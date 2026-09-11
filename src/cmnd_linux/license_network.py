"""Opt-in HTTPS reachability for the unchanged vendor license client, not activation."""
import ipaddress
import json
import os
from pathlib import Path
import socket
import tempfile

from .egress import EgressPolicy, apply_policy

HOST = 'license.cmnd.pro'
POLICY = Path('/etc/cmnd/egress.json')


def resolve_endpoints(resolver=socket.getaddrinfo):
    answers = resolver(HOST, 443, type=socket.SOCK_STREAM)
    addresses = {ipaddress.ip_address(answer[4][0]) for answer in answers}
    if not addresses or len(addresses) > 32 or any(
            not value.is_global or value.is_multicast or value.is_unspecified or value.is_reserved
            for value in addresses):
        raise ValueError('Vendor license DNS must resolve only to bounded public unicast addresses')
    return tuple((str(value), 443) for value in sorted(addresses, key=lambda value: (value.version, int(value))))


def enable(*, execute=False, resolver=socket.getaddrinfo):
    if not execute:
        return {'executed': False, 'allowed_hostname': HOST, 'port': 443,
                'license_requested': False, 'note': 'Use --execute to resolve and allow only vendor HTTPS; no TV access is granted'}
    if os.name != 'posix' or os.geteuid() != 0:
        raise PermissionError('License network configuration requires Linux root')
    if not POLICY.is_file() or any(p.is_symlink() for p in (POLICY, *POLICY.parents)):
        raise ValueError('Expected regular native egress policy')
    if POLICY.stat().st_uid != 0 or POLICY.stat().st_mode & 0o022:
        raise ValueError('Native egress policy must be root owned and not writable by others')
    # CMND's existing loopback allowance supports the local resolver without
    # opening general DNS or bypassing TV isolation. Refuse other resolver setups.
    servers = [line.split()[1] for line in Path('/etc/resolv.conf').read_text().splitlines()
               if line.split() and line.split()[0] == 'nameserver' and len(line.split()) > 1]
    if not servers or any(not ipaddress.ip_address(server).is_loopback for server in servers):
        raise ValueError('Use a local loopback DNS resolver before enabling license access')
    import fcntl
    with (POLICY.parent / 'license-network.lock').open('a') as lock:
        fcntl.flock(lock, fcntl.LOCK_EX)
        original = POLICY.read_bytes()
        raw = json.loads(original)
        prior = EgressPolicy(raw['chain'], tuple(raw['uids']), tuple(tuple(x) for x in raw['tcp_endpoints']))
        prior.validate()
        if prior.chain != 'CMND_NATIVE':
            raise ValueError('Only native CMND egress may be adjusted')
        endpoints = resolve_endpoints(resolver)
        old = raw.get('vendor_license', {})
        if old and old.get('hostname') != HOST:
            raise ValueError('Unexpected previous license hostname')
        previous = {tuple(x) for x in old.get('endpoints', [])}
        updated = (set(prior.tcp_endpoints) - previous) | set(endpoints)
        candidate = EgressPolicy(prior.chain, prior.uids, tuple(sorted(updated)))
        candidate.validate()
        raw['tcp_endpoints'] = candidate.tcp_endpoints
        raw['vendor_license'] = {'hostname': HOST, 'endpoints': endpoints}
        # Retain a private, unique policy backup; never overwrite earlier evidence.
        with tempfile.NamedTemporaryFile(prefix='egress-before-license-', suffix='.json', dir=POLICY.parent, delete=False) as backup:
            backup.write(original)
        with tempfile.NamedTemporaryFile(prefix='.egress-license-', dir=POLICY.parent, delete=False) as stream:
            stream.write((json.dumps(raw, indent=2) + '\n').encode())
            pending = Path(stream.name)
        try:
            apply_policy(candidate, execute=True)
            os.replace(pending, POLICY)
        except Exception:
            apply_policy(prior, execute=True)
            raise
    return {'executed': True, 'allowed_hostname': HOST, 'endpoints': endpoints,
            'backup': backup.name, 'license_requested': False,
            'note': 'Original Admin > License performs activation; rerun if vendor DNS addresses change'}
