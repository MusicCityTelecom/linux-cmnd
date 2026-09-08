import os
from pathlib import Path
import shutil
import stat
import subprocess
import tempfile
import unittest

from cmnd_linux.certificates import CertificateConfig, provision_certificates


def openssl_binary() -> str | None:
    found = shutil.which("openssl")
    if found:
        return found
    candidate = Path(r"C:\Program Files\Git\usr\bin\openssl.exe")
    return str(candidate) if candidate.is_file() else None


@unittest.skipUnless(openssl_binary(), "OpenSSL is required for certificate integration tests")
class CertificateTests(unittest.TestCase):
    def setUp(self):
        self.temp = tempfile.TemporaryDirectory()
        self.base = Path(self.temp.name)
        self.ca_password = self.base / "ca-password"
        self.server_password = self.base / "server-password"
        self.ca_password.write_text("ca-test-secret", encoding="ascii")
        self.server_password.write_text("server-test-secret", encoding="ascii")
        os.chmod(self.ca_password, 0o600)
        os.chmod(self.server_password, 0o600)

    def tearDown(self):
        self.temp.cleanup()

    def config(self, output: str, *, existing_ca=None):
        return CertificateConfig(
            output_dir=self.base / output,
            public_host="cmnd.example.test",
            active_ipv4=("10.20.30.40", "192.168.50.2"),
            computer_name="cmnd-server4",
            ca_password_file=self.ca_password,
            server_password_file=self.server_password,
            existing_ca_p12=existing_ca,
            openssl_binary=openssl_binary(),
            validity_days=30,
        )

    def run_openssl(self, *args):
        return subprocess.run([openssl_binary(), *map(str, args)], check=True,
                              stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)

    def pkcs12_info(self, package, password_file):
        for legacy in ((), ("-legacy",)):
            result = subprocess.run([openssl_binary(), "pkcs12", *legacy, "-in", str(package),
                                     "-passin", f"file:{password_file}", "-nokeys"],
                                    stdout=subprocess.PIPE, stderr=subprocess.PIPE, text=True)
            if result.returncode == 0:
                return result.stdout + result.stderr
        self.fail("OpenSSL could not inspect generated PKCS12")

    def test_chain_sans_aliases_and_environment(self):
        artifacts = provision_certificates(self.config("stage"))
        self.run_openssl("verify", "-CAfile", artifacts.ca_pem, artifacts.apache_certificate)
        for hostname in ("cmnd.example.test", "cmnd-server4", "localhost"):
            self.run_openssl("x509", "-in", artifacts.apache_certificate, "-noout", "-checkhost", hostname)
        for address in ("10.20.30.40", "192.168.50.2", "127.0.0.1"):
            self.run_openssl("x509", "-in", artifacts.apache_certificate, "-noout", "-checkip", address)
        self.assertIn("tpvision", self.pkcs12_info(artifacts.ca_p12, self.ca_password))
        self.assertIn("tomcat", self.pkcs12_info(artifacts.server_p12, self.server_password))
        self.assertGreaterEqual(artifacts.apache_chain.read_text().count("BEGIN CERTIFICATE"), 2)
        self.assertEqual(artifacts.computername_environment.read_text(), "COMPUTERNAME=cmnd-server4\n")
        if os.name == "posix":
            self.assertEqual(stat.S_IMODE(artifacts.server_p12.stat().st_mode), 0o600)
            self.assertEqual(stat.S_IMODE(artifacts.apache_private_key.stat().st_mode), 0o600)

    def test_existing_ca_is_preserved_while_leaf_is_regenerated(self):
        first = provision_certificates(self.config("first"))
        original_ca = first.ca_p12.read_bytes()
        original_fingerprint = self.run_openssl("x509", "-in", first.ca_pem, "-noout", "-fingerprint").stdout
        second = provision_certificates(self.config("second", existing_ca=first.ca_p12))
        self.assertEqual(second.ca_p12.read_bytes(), original_ca)
        retained_fingerprint = self.run_openssl("x509", "-in", second.ca_pem, "-noout", "-fingerprint").stdout
        self.assertEqual(retained_fingerprint, original_fingerprint)
        self.run_openssl("verify", "-CAfile", second.ca_pem, second.apache_certificate)

    def test_existing_non_ca_package_is_rejected(self):
        key = self.base / "not-ca.key"
        cert = self.base / "not-ca.pem"
        package = self.base / "not-ca.p12"
        self.run_openssl("req", "-x509", "-newkey", "rsa:2048", "-nodes", "-days", "2",
                         "-keyout", key, "-out", cert, "-subj", "/CN=not-a-ca",
                         "-addext", "basicConstraints=critical,CA:FALSE",
                         "-addext", "keyUsage=critical,digitalSignature")
        self.run_openssl("pkcs12", "-export", "-name", "tpvision", "-inkey", key,
                         "-in", cert, "-out", package,
                         "-passout", f"file:{self.ca_password}")
        with self.assertRaises(ValueError):
            provision_certificates(self.config("reject-not-ca", existing_ca=package))
        self.assertFalse((self.base / "reject-not-ca").exists())

    def test_output_stage_is_never_overwritten(self):
        output = self.base / "existing"
        output.mkdir()
        marker = output / "keep"
        marker.write_text("unchanged")
        with self.assertRaises(FileExistsError):
            provision_certificates(self.config("existing"))
        self.assertEqual(marker.read_text(), "unchanged")

    def test_output_symlink_and_symlink_ancestor_are_rejected(self):
        real_parent = self.base / "real-parent"
        real_parent.mkdir()
        linked_parent = self.base / "linked-parent"
        dangling = self.base / "dangling-output"
        try:
            linked_parent.symlink_to(real_parent, target_is_directory=True)
            dangling.symlink_to(self.base / "missing-target", target_is_directory=True)
        except (OSError, NotImplementedError) as error:
            self.skipTest(f"symlinks are unavailable: {error}")
        for output in (linked_parent / "stage", dangling):
            config = self.config("unused")
            config = CertificateConfig(**{**config.__dict__, "output_dir": output})
            with self.assertRaises(ValueError):
                provision_certificates(config)
            self.assertFalse((real_parent / "stage").exists())

    def test_password_values_are_not_returned_or_written_to_environment(self):
        artifacts = provision_certificates(self.config("private"))
        public_text = artifacts.computername_environment.read_text()
        self.assertNotIn("ca-test-secret", public_text)
        self.assertNotIn("server-test-secret", public_text)

    @unittest.skipUnless(os.name == "posix", "POSIX permission bits are required")
    def test_linux_rejects_world_readable_password_files(self):
        os.chmod(self.ca_password, 0o644)
        with self.assertRaises(PermissionError):
            provision_certificates(self.config("unsafe-password"))

    def test_rejects_unusable_public_ip(self):
        config = self.config("unusable-public-ip")
        config = CertificateConfig(**{**config.__dict__, "public_host": "0.0.0.0"})
        with self.assertRaises(ValueError):
            provision_certificates(config)


if __name__ == "__main__":
    unittest.main()
