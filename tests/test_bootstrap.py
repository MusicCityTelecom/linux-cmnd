import hashlib
import importlib.util
import io
from pathlib import Path
import tempfile
import unittest
from unittest.mock import patch
import zipfile


SCRIPTS = Path(__file__).resolve().parents[1] / 'scripts'


def load(name, filename):
    spec = importlib.util.spec_from_file_location(name, SCRIPTS / filename)
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


bootstrap = load('cmnd_bootstrap', 'bootstrap.py')
vendor = load('cmnd_prepare_vendor', 'prepare-vendor.py')


def release(version='0.6.0', **changes):
    result = {'tag_name': 'v' + version, 'prerelease': True, 'draft': False,
              'assets': [{'name': name, 'size': 4, 'digest': 'sha256:' + hashlib.sha256(b'data').hexdigest()}
                         for name in (f'linux-cmnd_{version}_amd64.deb', 'install.sh')]}
    return dict(result, **changes)


class BootstrapTests(unittest.TestCase):
    def test_newest_preview_numeric_not_api_order(self):
        result = bootstrap.choose_release([release('0.6.0'), release('0.10.0'), release('0.9.0')])
        self.assertEqual(result['tag_name'], 'v0.10.0')

    def test_stable_pin_draft_and_major_boundaries(self):
        items = [release('0.6.0'), release('0.5.0', prerelease=False), release('0.9.0', draft=True), release('1.0.0')]
        self.assertEqual(bootstrap.choose_release(items, channel='stable')['tag_name'], 'v0.5.0')
        self.assertEqual(bootstrap.choose_release(items, requested='0.6.0')['tag_name'], 'v0.6.0')
        for values in ([release('1.0.0')], [release('0.4.0')], [release(draft=True)]):
            with self.assertRaises(ValueError):
                bootstrap.choose_release(values)

    def test_bad_versions_rejected(self):
        for value in ('../../evil', '0.06.0', 'v0.6.0/x', '0.6.0;sh', None):
            with self.assertRaises(ValueError):
                bootstrap.version_number(value)

    def test_https_host_redirect_boundary(self):
        for value in ('http://github.com/a', 'https://evil.example/a', 'https://github.com.evil/a',
                      'https://user:pass@github.com/a', 'https://github.com:444/a', 'file:///etc/passwd'):
            with self.assertRaises(ValueError):
                bootstrap.trusted_url(value)
        self.assertEqual(bootstrap.trusted_url('https://release-assets.githubusercontent.com/a'),
                         'https://release-assets.githubusercontent.com/a')
        with self.assertRaises(ValueError):
            bootstrap.GitHubRedirect().redirect_request(None, None, 302, '', {}, 'http://github.com/a')

    def test_download_checks_bytes_and_builds_fixed_url(self):
        selected = release()
        selected['assets'][0]['browser_download_url'] = 'https://evil.example/root.sh'
        def fetch(url, size, destination):
            self.assertTrue(url.startswith('https://github.com/MusicCityTelecom/linux-cmnd/releases/download/v0.6.0/'))
            destination.write(b'data')
        with tempfile.TemporaryDirectory() as temp, patch.object(bootstrap, 'fetch', side_effect=fetch):
            self.assertEqual(len(bootstrap.download_assets(selected, Path(temp))), 2)

    def test_missing_duplicate_unbounded_and_corrupt_assets_fail(self):
        for mode in ('missing', 'duplicate', 'size', 'digest', 'corrupt'):
            selected = release()
            if mode == 'missing':
                selected['assets'] = []
            elif mode == 'duplicate':
                selected['assets'].append(dict(selected['assets'][0]))
            elif mode == 'size':
                selected['assets'][0]['size'] = 1024**3
            elif mode == 'digest':
                selected['assets'][0]['digest'] = None
            with tempfile.TemporaryDirectory() as temp, \
                    patch.object(bootstrap, 'fetch', side_effect=lambda url, size, destination: destination.write(b'evil')):
                with self.assertRaises(ValueError, msg=mode):
                    bootstrap.download_assets(selected, Path(temp))

    def test_download_does_not_overwrite(self):
        with tempfile.TemporaryDirectory() as temp, patch.object(bootstrap, 'fetch') as fetch:
            target = Path(temp) / 'linux-cmnd_0.6.0_amd64.deb'
            target.write_text('preserve')
            with self.assertRaises(FileExistsError):
                bootstrap.download_assets(release(), Path(temp))
            self.assertEqual(target.read_text(), 'preserve')
            fetch.assert_not_called()

    def test_configuration_empty_permissions_default_ports(self):
        for address, mode in (('127.0.0.1', 'isolated'), ('192.0.2.5', 'lab')):
            config = bootstrap.tomllib.loads(bootstrap.configuration(address))
            self.assertEqual(config['tv']['allowlist'], [])
            self.assertEqual(config['safety']['mode'], mode)
            self.assertEqual(list(config['ports'].values()), [8080, 8443, 8082, 8444, 3306])

    def test_address_injection_wildcards_and_multicast_fail(self):
        for value in ('0.0.0.0', '224.0.0.1', '169.254.0.1', '::1', 'example.com', '127.0.0.1"\n[tv]'):
            with self.assertRaises(ValueError):
                bootstrap.configuration(value)

    def test_config_validation_before_fetch(self):
        with patch.object(bootstrap.socket, 'socket'):
            content = bootstrap.configuration('127.0.0.1')
            self.assertEqual(bootstrap.check_configuration(content), ('127.0.0.1', 8444))
            for invalid in (content.replace('allowlist = []', 'allowlist = [{}]'),
                            content.replace('database = 3306', 'database = 9078'),
                            content.replace('http://127.0.0.1:8080', 'http://evil.example:8080')):
                with self.assertRaises(ValueError):
                    bootstrap.check_configuration(invalid)

    def test_dry_run_does_not_read_payload_or_contact_network(self):
        for arguments in ([], ['--execute', '--dry-run']):
            with patch.object(bootstrap, 'fetch') as fetch, patch.object(bootstrap, 'check_host') as host, \
                    patch('sys.stdout', new_callable=io.StringIO) as output:
                bootstrap.main(arguments)
                self.assertIn('No downloads or changes', output.getvalue())
                fetch.assert_not_called()
                host.assert_not_called()

    def test_missing_input_fails_before_network_or_package_operations(self):
        with patch.object(bootstrap.os, 'geteuid', return_value=0, create=True), \
                patch.object(bootstrap, 'check_host'), patch('sys.stdin.isatty', return_value=False), \
                patch.object(bootstrap, 'fetch') as fetch, patch.object(bootstrap.subprocess, 'run') as run:
            with self.assertRaisesRegex(ValueError, 'Supply --server-ip'):
                bootstrap.main(['--execute'])
            fetch.assert_not_called()
            run.assert_not_called()

    def test_bundle_download_uses_fixed_release_url_and_streaming_hash(self):
        selected = release()
        selected['assets'].append({'name': bootstrap.VENDOR_ASSET, 'size': 4,
                                  'digest': 'sha256:' + hashlib.sha256(b'data').hexdigest()})
        urls = []
        def fetch(url, size, destination):
            urls.append(url)
            destination.write(b'data')
        with tempfile.TemporaryDirectory() as temp, patch.object(bootstrap, 'fetch', side_effect=fetch):
            result = bootstrap.download_assets(selected, Path(temp), include_vendor=True)
            self.assertEqual(set(result), {'linux-cmnd_0.6.0_amd64.deb', 'install.sh', bootstrap.VENDOR_ASSET})
            self.assertEqual(urls[-1], 'https://github.com/MusicCityTelecom/linux-cmnd/releases/download/v0.6.0/' + bootstrap.VENDOR_ASSET)

    def test_bundle_missing_duplicate_oversize_and_corrupt_fail(self):
        for mode in ('missing', 'duplicate', 'size', 'corrupt'):
            selected = release()
            asset = {'name': bootstrap.VENDOR_ASSET, 'size': 4,
                     'digest': 'sha256:' + hashlib.sha256(b'data').hexdigest()}
            if mode != 'missing':
                selected['assets'].append(asset)
            if mode == 'duplicate':
                selected['assets'].append(dict(asset))
            if mode == 'size':
                asset['size'] = bootstrap.MAX_VENDOR_ASSET + 1
            def fetch(url, size, destination):
                destination.write(b'evil' if mode == 'corrupt' and url.endswith('.zip') else b'data')
            with tempfile.TemporaryDirectory() as temp, patch.object(bootstrap, 'fetch', side_effect=fetch):
                with self.assertRaises(ValueError, msg=mode):
                    bootstrap.download_assets(selected, Path(temp), include_vendor=True)

    def test_explicit_missing_payload_never_falls_back_to_download(self):
        with tempfile.TemporaryDirectory() as temp, \
                patch.object(bootstrap.os, 'geteuid', return_value=0, create=True), \
                patch.object(bootstrap, 'check_host'), patch.object(bootstrap, 'fetch') as fetch:
            with self.assertRaisesRegex(ValueError, 'Explicit --payload'):
                bootstrap.main(['--execute', '--payload', str(Path(temp) / 'missing.zip')])
            fetch.assert_not_called()


class PrepareVendorTests(unittest.TestCase):
    def test_only_pinned_files_in_private_zip_no_overwrite(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            source = root / 'original'
            source.mkdir()
            (source / 'original.war').write_bytes(b'original')
            (source / 'customer-passwords.txt').write_text('never package this')
            hashes = {'original.war': hashlib.sha256(b'original').hexdigest()}
            output = root / 'private.zip'
            with patch.object(vendor, 'VENDOR_INPUT_HASHES', hashes):
                self.assertFalse(vendor.prepare(source, output)['written'])
                self.assertFalse(output.exists())
                self.assertTrue(vendor.prepare(source, output, True)['written'])
                with zipfile.ZipFile(output) as archive:
                    self.assertEqual(archive.namelist(), ['original.war'])
                with self.assertRaises(ValueError):
                    vendor.prepare(source, output, True)

    def test_modified_input_never_packaged(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            (root / 'original.war').write_bytes(b'modified')
            output = root / 'private.zip'
            with patch.object(vendor, 'VENDOR_INPUT_HASHES', {'original.war': '0' * 64}):
                with self.assertRaisesRegex(ValueError, 'changed original'):
                    vendor.prepare(root, output, True)
                self.assertFalse(output.exists())


if __name__ == '__main__':
    unittest.main()
