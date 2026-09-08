"""Manage only a named CMND UID egress chain; never flush a host ruleset."""
from __future__ import annotations

import argparse
from dataclasses import dataclass
import ipaddress
import json
import os
from pathlib import Path
import re
import subprocess


MARKER = 'linux-cmnd-managed-egress'


@dataclass(frozen=True)
class EgressPolicy:
    chain: str
    uids: tuple[int, ...]
    tcp_endpoints: tuple[tuple[str, int], ...]

    def validate(self):
        if not re.fullmatch(r'CMND_[A-Z0-9_]{1,20}', self.chain):
            raise ValueError('egress chain must have a CMND_ prefix and safe identifier')
        if not self.uids or len(set(self.uids)) != len(self.uids):
            raise ValueError('egress requires distinct service UIDs')
        if any(type(uid) is not int or not 1 <= uid <= 2**31 - 1 for uid in self.uids):
            raise ValueError('egress must never target root or an invalid UID')
        for address, port in self.tcp_endpoints:
            value = ipaddress.ip_address(address)
            if value.is_multicast or value.is_unspecified:
                raise ValueError('egress endpoint must be a unicast literal IP')
            if type(port) is not int or not 1 <= port <= 65535:
                raise ValueError('egress endpoint port is invalid')


def render_rules(policy: EgressPolicy, version: int) -> str:
    policy.validate()
    if version not in (4, 6):
        raise ValueError('IP version must be 4 or 6')
    chain = policy.chain
    loopback = '127.0.0.0/8' if version == 4 else '::1/128'
    lines = ['*filter', f':{chain} - [0:0]', f'-F {chain}',
             f'-A {chain} -m conntrack --ctstate ESTABLISHED,RELATED -j ACCEPT',
             f'-A {chain} -d {loopback} -j ACCEPT']
    for address, port in sorted(set(policy.tcp_endpoints)):
        value = ipaddress.ip_address(address)
        if value.version == version:
            lines.append(f'-A {chain} -d {value}/{value.max_prefixlen} -p tcp --dport {port} -j ACCEPT')
    lines += [f'-A {chain} -m comment --comment {MARKER} -j REJECT', 'COMMIT', '']
    return '\n'.join(lines)


def apply_policy(policy: EgressPolicy, *, execute: bool = False, runner=subprocess.run) -> dict:
    policy.validate()
    rules = {version: render_rules(policy, version) for version in (4, 6)}
    report = {'executed': execute, 'chain': policy.chain, 'uids': list(policy.uids),
              'families': [4, 6], 'host_ruleset_flushed': False}
    if not execute:
        return report
    if os.name != 'posix' or os.geteuid() != 0:
        raise PermissionError('egress activation requires Linux root')
    # Inspect both families before mutation. Never adopt/flush an unknown chain.
    for binary in ('iptables', 'ip6tables'):
        probe = runner([binary, '-w', '5', '-S', policy.chain], capture_output=True)
        if probe.returncode == 0:
            if MARKER.encode() not in probe.stdout:
                raise ValueError('existing egress chain is not managed by this module')
        elif b'No chain/target/match' not in probe.stderr and b'does not exist' not in probe.stderr:
            raise RuntimeError('unable to inspect egress chain; no policy changes made')
    for binary, version in (('iptables', 4), ('ip6tables', 6)):
        restored = runner([binary + '-restore', '--wait', '5', '--noflush'],
                          input=rules[version].encode(), capture_output=True)
        if restored.returncode:
            raise RuntimeError('egress chain setup failed; application startup must remain blocked')
        for uid in policy.uids:
            match = ['OUTPUT', '-m', 'owner', '--uid-owner', str(uid), '-j', policy.chain]
            probe = runner([binary, '-w', '5', '-C', *match], capture_output=True)
            if probe.returncode:
                result = runner([binary, '-w', '5', '-I', *match], capture_output=True)
                if result.returncode:
                    raise RuntimeError('egress attachment failed; application startup must remain blocked')
        # Assert the final reject rule and every UID attachment before success.
        final = runner([binary, '-w', '5', '-S', policy.chain], capture_output=True)
        if final.returncode or MARKER.encode() not in final.stdout:
            raise RuntimeError('egress rule verification failed')
        for uid in policy.uids:
            check = runner([binary, '-w', '5', '-C', 'OUTPUT', '-m', 'owner',
                            '--uid-owner', str(uid), '-j', policy.chain], capture_output=True)
            if check.returncode:
                raise RuntimeError('egress UID verification failed')
    return report


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument('--policy', required=True, type=Path)
    parser.add_argument('--execute', action='store_true')
    args = parser.parse_args()
    if args.policy.is_symlink() or not args.policy.is_file():
        raise SystemExit('policy must be a regular file')
    if os.name == 'posix' and args.policy.stat().st_mode & 0o022:
        raise SystemExit('policy must not be writable by group or others')
    raw = json.loads(args.policy.read_text())
    policy = EgressPolicy(raw['chain'], tuple(raw['uids']), tuple(tuple(item) for item in raw['tcp_endpoints']))
    print(json.dumps(apply_policy(policy, execute=args.execute)))


if __name__ == '__main__':
    main()
