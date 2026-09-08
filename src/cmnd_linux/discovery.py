"""Bounded unicast discovery and independently owned inventory.

No scan adds write permissions or modifies vendor database tables.
"""
from __future__ import annotations

from concurrent.futures import ThreadPoolExecutor, wait, FIRST_COMPLETED
from contextlib import closing
from datetime import datetime, timezone
from ipaddress import ip_address, ip_network
from pathlib import Path
import math
import re
import sqlite3
import threading
import time

from .config import Config, ConfigError
from .protocol import WIXPClient, ProtocolError, discovery_request


def scan_targets(config: Config, requested: list[str], max_targets: int = 512) -> list[str]:
    if not 1 <= max_targets <= 4096:
        raise ConfigError('max_targets must be between 1 and 4096')
    allowed = [ip_network(net, strict=False) for net in config.permitted_ranges]
    result = set()
    for target in requested:
        network = ip_network(target, strict=False)
        if network.num_addresses > max_targets + 2:
            raise ConfigError('requested range exceeds the target cap')
        addresses = list(network.hosts()) if network.num_addresses > 1 else [network.network_address]
        for address in addresses:
            if address.is_multicast or address.is_unspecified or not any(
                address.version == net.version and address in net for net in allowed
            ):
                raise ConfigError(f'target outside permitted unicast ranges: {address}')
            if config.mode == 'isolated' and not address.is_loopback:
                raise ConfigError('isolated mode permits only loopback TV targets')
            result.add(address)
        if len(result) > max_targets:
            raise ConfigError('combined target count exceeds cap')
    if not result:
        raise ConfigError('at least one target is required')
    return [str(addr) for addr in sorted(result, key=lambda addr: (addr.version, int(addr)))]


def identify(target: str, response: dict) -> dict:
    details = response.get('CommandDetails', {})
    if not isinstance(details, dict):
        raise ProtocolError('invalid discovery details')
    discovery = details.get('TVDiscoveryParameters', {})
    listening = details.get('WebListeningServiceParameters', {})
    if not isinstance(discovery, dict) or not isinstance(listening, dict):
        raise ProtocolError('invalid discovery parameter objects')
    unique_id = listening.get('TVUniqueID', '')
    serial = discovery.get('TVSerialNumber', '')
    mac = discovery.get('TVMACAddress', '')
    if not all(isinstance(value, str) for value in (unique_id, serial, mac)):
        raise ProtocolError('invalid identity field types')
    if not unique_id or not serial or not re.fullmatch(r'[A-Za-z0-9._:-]{1,128}', unique_id):
        raise ProtocolError('discovery response lacks stable identity')
    claimed = str(discovery.get('TVIPAddress', target))
    if ip_address(claimed) != ip_address(target):
        raise ProtocolError('TV advertised an IP different from the queried target')
    room = discovery.get('TVRoomID', '')
    if not isinstance(room, str):
        raise ProtocolError('room ID must be returned as a string')
    return {'ip': str(ip_address(target)), 'identity': unique_id, 'serial': serial,
            'mac': mac, 'model': discovery.get('TVModelNumber', ''), 'room_id': room,
            'power': discovery.get('PowerStatus', ''), 'service_version': response.get('SvcVer'),
            'observed_at': datetime.now(timezone.utc).isoformat()}


def scan(config: Config, requested: list[str], *, port: int = 9079, concurrency: int = 8,
         max_targets: int = 512, rate: float = 10, cancel: threading.Event | None = None) -> dict:
    targets = scan_targets(config, requested, max_targets)  # Must precede sockets/threads.
    if not 1 <= concurrency <= 32 or not math.isfinite(rate) or not 0 < rate <= 50:
        raise ConfigError('concurrency must be 1..32 and rate must be >0..50 requests/s')
    if not 1 <= port <= 65535:
        raise ConfigError('port must be 1..65535')
    cancel = cancel or threading.Event()
    devices, failures = [], []
    next_send = 0.0

    def probe(target):
        return identify(target, WIXPClient(config.timeout_seconds).send(target, discovery_request(), port))

    with ThreadPoolExecutor(max_workers=concurrency) as pool:
        pending = {}
        iterator = iter(targets)
        exhausted = False
        while pending or not exhausted:
            if cancel.is_set():
                exhausted = True
            while not exhausted and len(pending) < concurrency:
                target = next(iterator, None)
                if target is None:
                    exhausted = True
                    break
                delay = max(0, next_send - time.monotonic())
                if cancel.wait(delay):
                    exhausted = True
                    break
                pending[pool.submit(probe, target)] = target
                next_send = time.monotonic() + 1 / rate
            if not pending:
                break
            done, _ = wait(pending, return_when=FIRST_COMPLETED)
            for future in done:
                target = pending.pop(future)
                try:
                    devices.append(future.result())
                except (ProtocolError, ValueError, OSError) as exc:
                    failures.append({'ip': target, 'error': str(exc)})
    return {'devices': sorted(devices, key=lambda item: item['ip']), 'failures': failures,
            'requested': len(targets), 'completed': len(devices) + len(failures),
            'cancelled': cancel.is_set(), 'write_permissions_added': False}


def verify_identity(config: Config, target: str, expected_identity: str, *, port: int = 9079) -> dict:
    scan_targets(config, [target], 1)
    if not 1 <= port <= 65535:
        raise ConfigError('port must be 1..65535')
    tv = identify(target, WIXPClient(config.timeout_seconds).send(target, discovery_request(), port))
    if expected_identity not in {tv['identity'], tv['serial'], tv['mac']}:
        raise ProtocolError('identity changed or differs from the selected scan result')
    return tv


def add_tv(config: Config, target: str, expected_identity: str, database: Path, *, port: int = 9079) -> dict:
    # Re-read identity before even opening/creating the independent inventory.
    tv = verify_identity(config, target, expected_identity, port=port)
    database.parent.mkdir(parents=True, exist_ok=True)
    with closing(sqlite3.connect(database)) as conn, conn:
        conn.execute('CREATE TABLE IF NOT EXISTS devices (identity TEXT PRIMARY KEY, ip TEXT UNIQUE NOT NULL, serial TEXT NOT NULL, mac TEXT NOT NULL, model TEXT, room_id TEXT, observed_at TEXT)')
        conflict = conn.execute('SELECT identity FROM devices WHERE ip=?', (tv['ip'],)).fetchone()
        if conflict and conflict[0] != tv['identity']:
            raise ProtocolError('IP belongs to another saved device; review inventory conflict')
        conn.execute('INSERT INTO devices VALUES (?,?,?,?,?,?,?) ON CONFLICT(identity) DO UPDATE SET ip=excluded.ip, serial=excluded.serial, mac=excluded.mac, model=excluded.model, room_id=excluded.room_id, observed_at=excluded.observed_at',
                     tuple(tv[key] for key in ('identity','ip','serial','mac','model','room_id','observed_at')))
    return {'device': tv, 'added': True, 'write_permissions_added': False,
            'inventory': str(database), 'vendor_inventory_updated': False}
