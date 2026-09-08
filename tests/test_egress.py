from dataclasses import replace
from types import SimpleNamespace
import unittest
from unittest.mock import patch

from cmnd_linux.egress import EgressPolicy, MARKER, apply_policy, render_rules


class EgressTests(unittest.TestCase):
    def setUp(self):
        self.policy = EgressPolicy('CMND_TEST', (997, 33), (('172.30.44.2', 3306), ('172.30.44.4', 9079)))

    def test_rules_only_flush_own_chain_and_end_in_reject(self):
        rules = render_rules(self.policy, 4)
        self.assertIn('-F CMND_TEST\n', rules)
        self.assertNotIn('-F OUTPUT', rules)
        self.assertIn('-d 172.30.44.2/32 -p tcp --dport 3306', rules)
        self.assertIn('-d 172.30.44.4/32 -p tcp --dport 9079', rules)
        self.assertIn(MARKER + ' -j REJECT\nCOMMIT', rules)
        v6 = render_rules(self.policy, 6)
        self.assertIn('::1/128', v6)
        self.assertNotIn('172.30.44', v6)

    def test_invalid_policy_never_executes(self):
        invalid = (replace(self.policy, chain='OUTPUT'), replace(self.policy, uids=(0,)),
                   replace(self.policy, uids=(33, 33)), replace(self.policy, tcp_endpoints=(('0.0.0.0', 80),)),
                   replace(self.policy, tcp_endpoints=(('192.0.2.1', True),)))
        for policy in invalid:
            with self.subTest(policy=policy), self.assertRaises(ValueError):
                apply_policy(policy, execute=True, runner=lambda *args, **kwargs: self.fail('executed invalid policy'))

    def test_preview_has_no_host_calls(self):
        result = apply_policy(self.policy, runner=lambda *args, **kwargs: self.fail('preview mutated host'))
        self.assertFalse(result['executed'])

    def test_unknown_chain_is_preserved(self):
        calls = []
        def runner(args, **kwargs):
            calls.append(args)
            return SimpleNamespace(returncode=0, stdout=b'-N CMND_TEST\n', stderr=b'')
        with patch('cmnd_linux.egress.os.name', 'posix'), patch('cmnd_linux.egress.os.geteuid', return_value=0, create=True):
            with self.assertRaises(ValueError):
                apply_policy(self.policy, execute=True, runner=runner)
        self.assertTrue(all('-S' in call for call in calls))

    def test_apply_uses_noflush_and_verifies_both_families(self):
        calls = []
        def runner(args, **kwargs):
            calls.append((args, kwargs))
            return SimpleNamespace(returncode=0, stdout=MARKER.encode(), stderr=b'')
        with patch('cmnd_linux.egress.os.name', 'posix'), patch('cmnd_linux.egress.os.geteuid', return_value=0, create=True):
            result = apply_policy(self.policy, execute=True, runner=runner)
        restore_calls = [args for args, _ in calls if args[0].endswith('-restore')]
        self.assertEqual(len(restore_calls), 2)
        self.assertTrue(all('--noflush' in args for args in restore_calls))
        self.assertTrue(result['executed'])


if __name__ == '__main__':
    unittest.main()
