import socket
import unittest
from unittest.mock import Mock

from cmnd_linux.license_network import HOST, enable, resolve_endpoints


class LicenseNetworkTests(unittest.TestCase):
    def test_preview_never_resolves_or_requests_a_license(self):
        resolver = Mock(side_effect=AssertionError('Unexpected network request'))
        result = enable(resolver=resolver)
        self.assertFalse(result['executed'])
        self.assertFalse(result['license_requested'])
        resolver.assert_not_called()

    def test_resolves_only_fixed_vendor_https(self):
        resolver = Mock(return_value=[(socket.AF_INET, socket.SOCK_STREAM, 6, '', ('8.8.8.8', 443))])
        self.assertEqual(resolve_endpoints(resolver), (('8.8.8.8', 443),))
        resolver.assert_called_once_with(HOST, 443, type=socket.SOCK_STREAM)

    def test_empty_private_loopback_multicast_and_mixed_answers_rejected(self):
        for ips in ([], ['127.0.0.1'], ['192.168.255.97'], ['224.0.0.1'], ['::1'], ['8.8.8.8', '10.1.10.14']):
            answers = [(socket.AF_INET, socket.SOCK_STREAM, 6, '', (ip, 443)) for ip in ips]
            with self.subTest(ips=ips), self.assertRaises(ValueError):
                resolve_endpoints(Mock(return_value=answers))


if __name__ == '__main__':
    unittest.main()
