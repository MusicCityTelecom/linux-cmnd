"""Assemble a complete private application candidate in the disposable VM only."""
import json
import os
from pathlib import Path
import socket

from cmnd_linux.application_stage import ApplicationInputs, stage_application
from cmnd_linux.certificates import CertificateArtifacts
from cmnd_linux.config import Config


def main():
    if os.geteuid() or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires root in the dedicated qualification VM')
    os.umask(0o077)
    base = Path('/var/lib/cmnd-lab')
    certificates = base / 'native-certificates-001'
    cert = CertificateArtifacts(certificates, certificates / 'Cert/ca.p12', certificates / 'Cert/ca.pem',
        certificates / 'tomcat/server.p12', certificates / 'apache/servercert.pem',
        certificates / 'apache/serverkey.pem', certificates / 'apache/server-chain.pem',
        certificates / 'computername.env')
    inputs = ApplicationInputs(Path('/home/cmndlab/input/vendor'), base / 'tomcat.tar.gz', cert)
    config = Config('isolated', '127.0.0.1', 'http://127.0.0.1:8080', ('127.0.0.0/8',), ())
    secrets = json.loads((base / 'secrets.json').read_text())
    report = stage_application(inputs, config, secrets, base / 'application-stage-001', execute=True)
    print(json.dumps(report, indent=2))


if __name__ == '__main__':
    main()
