"""Assemble the real vendor applications and native configuration privately.

No database statements, network requests, host file installation, or service
operations occur here. A complete staged payload is distinct from an activated,
qualified installation. Reference/customer backups are never inputs to this API.
"""
from __future__ import annotations

from dataclasses import dataclass
import hashlib
import json
import os
from pathlib import Path, PurePosixPath
import shutil
import tarfile

from .artifacts import file_hash, safe_extract_zip
from .certificates import CertificateArtifacts
from .config import Config, ConfigError
from .native_config import NativeLayout, native_endpoint, stage_native_config
from .vendor_cms import stage_smartcms
from .vendor_config import WAR_NAMES, VendorRenderConfig, _props, render_vendor_wars


TOMCAT_VERSION = '9.0.121'
TOMCAT_SHA512 = '16494dd4745f808d3c506807b5275521fd71044d976f441d18eeeab0f5a38bc1b5344ca395292f6f26eb7612cd8c8e746d01ccdfb29893d394052d9f4b1f4c11'
SQL_INITIALIZATION = {'smartinstall': (2, 3), 'cas': (2,), 'tpvision': tuple(range(2, 14)),
                      'smartcontrol': (2,), 'smartcms': (2, 3)}
VENDOR_INPUT_HASHES = {
    'cas.war': 'e5203c0ee083439d8182725ea1bff96a2a24501f49f1c353b9ecfc4c8c801066',
    'smartcms.war': 'f90e8472e0c42c19cdc1de3ea7471db1d9aee2b777e6002928e47236d7620478',
    'smartcontrol.war': '80219f615dca7971b4827754dd113850a30dac840b187d5a09ab5ebd32ead362',
    'SmartInstall.war': '55bbdbefc9c87ce2156ac4a69403b8171845c405727d9355d717f85c2695659d',
    'usermanagement.war': '4aa46c9bdb3a34e311ec2bec61bcc177939759248a184c2a3262445c526d8bb7',
    'Philips.zip': '93f95b144f609e49d92edb59715ad4e571b9cd8c2a5ed9b910200cddc12c60ac',
    'SmartCMS.zip': '16901ff9416e4a6a0cf6bed0b0fa208f39522e7f3cfb5fe4f90ec286af3d3a14',
    'Tomcat 9.0/lib/reload.jar': '2a301629647b10cc8e8ab7cb496d05333524372839c9a5ea4d5c4d9faf6786ab',
    'SQLScripts/smartinstall/sql_2.sql': 'aa48c174b614e3a9849fb69f94be6bd4349fba56219b341849df668087a56855',
    'SQLScripts/smartinstall/sql_3.sql': '8279e2570630057754e5c2503b83b93b4ff38e91b54db4ea59fc3106cc943a73',
    'SQLScripts/cas/sql_2.sql': 'c63414348cbce79eefd2ad813c559ff3db479b8281f37b54fca4de37f0b8cfb0',
    'SQLScripts/tpvision/sql_2.sql': '10b9d79fbe916acbdc04cc1001d9fece8128902c17938601d8028dbafb85bcaa',
    'SQLScripts/tpvision/sql_3.sql': 'dc882e63a129dde1c21a8890d7607cc3c4f5d7e2af8a28cc05f20bacdaa50d96',
    'SQLScripts/tpvision/sql_4.sql': 'd0bf08c7a60a4d9eccb31a384438887a3e7ac4a7bf4092112e3dcd78f1c9b67d',
    'SQLScripts/tpvision/sql_5.sql': '39fa8f0c4ea2ade639ceef3e863b781d619ec12a663a9f88b6e68ba626f6f5da',
    'SQLScripts/tpvision/sql_6.sql': '495bdc59ad05978b5aa3129181edc76e14cc732a5c3c4bed6c84f1164a86b643',
    'SQLScripts/tpvision/sql_7.sql': 'f80cd2e2220dec0830775411a223b7690845fc292f91a89445768b68feac293b',
    'SQLScripts/tpvision/sql_8.sql': '5c55015c6e5d18d2fc299dcbb1bb1a91fffb05ed503450a7484e0c7e06aee38b',
    'SQLScripts/tpvision/sql_9.sql': '0f99578ca551763cdb472e842832171d94f28570c335c192aec5e4a70d426820',
    'SQLScripts/tpvision/sql_10.sql': '010dd835fc51638c67d0ca8cb13dde1ca090f67534be9ca301e8f0ed403299e4',
    'SQLScripts/tpvision/sql_11.sql': 'f34b14fe30726c2c044e45848744d63364c10407057d333b823a0cd2408e38f1',
    'SQLScripts/tpvision/sql_12.sql': '85786fdc383f808c3e319a4c3b56003c470053b169fa9b7bcea4d2de0d6e2f7e',
    'SQLScripts/tpvision/sql_13.sql': '4a9d30271236f5025ac8142b67ebb02a0fbbc2fd9a6921cbad02a4cec2d32317',
    'SQLScripts/smartcontrol/sql_2.sql': '94665060ebdad73b838579dba858fbb6ad19f1cadb64cae339f5024db3156f3c',
    'SQLScripts/smartcms/sql_2.sql': '11f4a82f809719d1de0eee8b25d4579da7cff0dc8d2ac50cd5fb9dffee23287c',
    'SQLScripts/smartcms/sql_3.sql': 'a07b5790ad570eefa68f0f4a92d7201c5114b00990d64919cae7a93ee521eedd',
}


@dataclass(frozen=True)
class ApplicationInputs:
    vendor: Path
    tomcat_archive: Path
    certificates: CertificateArtifacts


def certificate_stage_paths(stage: Path) -> CertificateArtifacts:
    """Locate the certificate provisioner's output; existence is checked on use."""
    stage = Path(stage)
    return CertificateArtifacts(stage, stage / 'Cert/ca.p12', stage / 'Cert/ca.pem',
        stage / 'tomcat/server.p12', stage / 'apache/servercert.pem',
        stage / 'apache/serverkey.pem', stage / 'apache/server-chain.pem', stage / 'computername.env')


def _require_regular(path: Path) -> None:
    if not path.is_file() or path.is_symlink():
        raise ConfigError(f'required regular input is missing: {path.name}')


def _tomcat_members(path: Path) -> list[tarfile.TarInfo]:
    digest = hashlib.sha512()
    with path.open('rb') as handle:
        for chunk in iter(lambda: handle.read(1024 * 1024), b''):
            digest.update(chunk)
    if digest.hexdigest() != TOMCAT_SHA512:
        raise ConfigError('Tomcat archive does not match the pinned SHA-512')
    with tarfile.open(path, 'r:gz') as archive:
        members = archive.getmembers()
    if len(members) > 20000 or sum(item.size for item in members) > 1024**3:
        raise ConfigError('Tomcat archive exceeds staging limits')
    prefix = 'apache-tomcat-' + TOMCAT_VERSION
    seen: set[str] = set()
    for member in members:
        name = PurePosixPath(member.name)
        if (name.is_absolute() or '..' in name.parts or not name.parts or name.parts[0] != prefix
                or '\\' in member.name or not (member.isfile() or member.isdir())
                or member.name in seen):
            raise ConfigError('unexpected or unsafe member in Tomcat archive')
        seen.add(member.name)
    return members


def stage_application(inputs: ApplicationInputs, config: Config, secrets: dict[str, str],
                      output: Path, *, execute: bool = False,
                      layout: NativeLayout = NativeLayout(), database_host: str = '127.0.0.1') -> dict:
    """Build a new, non-overwriting deployment candidate from operator inputs."""
    host, ports = native_endpoint(config)
    layout.validate()
    if ':' in database_host:
        raise ConfigError('native vendor JDBC IPv6 deployment is not yet qualified')
    render = VendorRenderConfig(host, ports, secrets, database_host)
    render.validate()
    if not secrets.get('tpvision_db_password'):
        raise ConfigError('tpvision_db_password is required for the PHP CMS schema')
    vendor = Path(inputs.vendor)
    required = [vendor / name for name in WAR_NAMES]
    required += [vendor / name for name in ('Philips.zip', 'SmartCMS.zip', 'Tomcat 9.0/lib/reload.jar')]
    required += [vendor / 'SQLScripts' / schema / f'sql_{number}.sql'
                 for schema, numbers in SQL_INITIALIZATION.items() for number in numbers]
    cert = inputs.certificates
    certificate_files = (cert.ca_p12, cert.ca_pem, cert.server_p12, cert.apache_certificate,
                         cert.apache_private_key, cert.apache_chain, cert.computername_environment)
    for path in (*required, inputs.tomcat_archive, *certificate_files):
        _require_regular(Path(path))
    input_hashes = {path.relative_to(vendor).as_posix(): file_hash(path) for path in required}
    if input_hashes != VENDOR_INPUT_HASHES:
        mismatches = sorted(name for name in set(input_hashes) | set(VENDOR_INPUT_HASHES)
                            if input_hashes.get(name) != VENDOR_INPUT_HASHES.get(name))
        raise ConfigError('vendor inputs differ from the inspected 7.5.9 payload: ' + ', '.join(mismatches))
    members = _tomcat_members(inputs.tomcat_archive)
    report = {'executed': execute, 'vendor_wars': list(WAR_NAMES), 'tomcat': TOMCAT_VERSION,
              'services_started': False, 'database_initialization_performed': False,
              'reference_backups_included': False, 'activation_qualified': False}
    if not execute:
        return report
    output = Path(output)
    if any(parent.is_symlink() for parent in (output, *output.parents)):
        raise ConfigError('application stage must not traverse a symbolic link')
    output.mkdir(mode=0o700, exist_ok=False)
    # Preserve a failed stage for private diagnosis. Do not activate or delete it.
    try:
        payload = output / 'payload'
        payload.mkdir(mode=0o700)
        tomcat = payload / 'tomcat'
        tomcat.mkdir(mode=0o700)
        with tarfile.open(inputs.tomcat_archive, 'r:gz') as archive:
            for member in members:
                relative = PurePosixPath(member.name).parts[1:]
                if not relative or relative[0] == 'webapps':
                    continue  # Do not deploy Tomcat manager/examples/default ROOT.
                target = tomcat.joinpath(*relative)
                if member.isdir():
                    target.mkdir(parents=True, exist_ok=True)
                else:
                    target.parent.mkdir(parents=True, exist_ok=True)
                    with archive.extractfile(member) as source, target.open('xb') as destination:
                        shutil.copyfileobj(source, destination)
                    target.chmod(0o700 if relative[0] == 'bin' and target.suffix == '.sh' else 0o600)
        rendered = render_vendor_wars(vendor, output / 'rendered-wars', render)
        webapps = tomcat / 'webapps'
        webapps.mkdir()
        for war in rendered:
            safe_extract_zip(war, webapps / war.stem)
        shutil.copyfile(vendor / 'Tomcat 9.0/lib/reload.jar', tomcat / 'lib/reload.jar')
        # The known vendor template archive includes zero-filled channel files.
        # This exception does not loosen customer backup extraction limits.
        safe_extract_zip(vendor / 'Philips.zip', payload / 'Philips', max_ratio=2000)
        cms = stage_smartcms(vendor / 'SmartCMS.zip', output / 'cms-stage',
                            db_host=database_host, db_port=config.database_port,
                            db_user='tpvision', db_password=secrets['tpvision_db_password'])
        cms_target = payload / 'SmartCMS'
        shutil.move(cms.root, cms_target)
        (output / 'cms-stage').rmdir()
        properties = cms_target / 'sites/default/config.properties'
        original = properties.read_bytes()
        existing = {line.split('=', 1)[0].strip() for line in original.decode().splitlines()
                    if '=' in line and not line.lstrip().startswith(('#', '!'))}
        replacements = {f'{name}.name': host for name in ('server', 'cms', 'cas', 'published')}
        replacements.update({'server.port': str(config.tomcat_http), 'cms.port': str(config.apache_http),
            'cas.port': str(config.tomcat_http), 'published.port': str(config.tomcat_http),
            'server.https.port': str(config.tomcat_https), 'cms.https.port': str(config.apache_https),
            'cas.https.port': str(config.tomcat_https), 'published.https.port': str(config.tomcat_https),
            'mysql.ip': database_host, 'mysql.port': str(config.database_port),
            'security.pwd': secrets['application_security_password'], 'cert.ca.password': secrets['cert_ca_password'],
            'survey.email.smtp.account': '', 'survey.email.smtp.pwd': '',
            'cloud.server.auth.clientid': '', 'cloud.server.auth.clientsecret': ''})
        properties.write_bytes(_props(original, {key: value for key, value in replacements.items() if key in existing}))
        for source, destination in ((cert.ca_p12, payload / 'Philips/Cert/ca.p12'),
                                    (cert.ca_p12, webapps / 'SmartInstall/Cert/ca.p12'),
                                    (cert.server_p12, tomcat / 'server.p12'),
                                    (cert.ca_pem, payload / 'tls/ca.crt'),
                                    (cert.apache_chain, payload / 'tls/server.crt'),
                                    (cert.apache_private_key, payload / 'tls/server.key')):
            destination.parent.mkdir(parents=True, exist_ok=True)
            shutil.copyfile(source, destination)
            destination.chmod(0o600)
        native = output / 'native-config'
        stage_native_config(config, secrets, native, execute=True, layout=layout)
        for name in ('server.xml', 'context.xml'):
            shutil.copyfile(native / name, tomcat / 'conf' / name)
        shutil.copytree(native / 'ROOT', webapps / 'ROOT')
        shutil.copyfile(cert.computername_environment, native / 'computername.env')
        sql_stage = output / 'fresh-schema'
        for schema, numbers in SQL_INITIALIZATION.items():
            for number in numbers:
                source = vendor / 'SQLScripts' / schema / f'sql_{number}.sql'
                destination = sql_stage / schema / source.name
                destination.parent.mkdir(parents=True, exist_ok=True)
                shutil.copyfile(source, destination)
        report['input_sha256'] = input_hashes
        report['fresh_schema_order'] = {schema: list(numbers) for schema, numbers in SQL_INITIALIZATION.items()}
        report['activation_requirements'] = [
            'Install payload/Philips at /opt/Philips, not under the Tomcat root.',
            'Install payload/tomcat and payload/SmartCMS at the configured native runtime paths.',
            'Install payload/tls at /etc/cmnd/tls and establish OS/Java CA trust before CAS.',
            'Create private PHP PGT storage and apply reviewed service-account ownership.',
            'Initialize isolated databases and enforce reviewed IPv4/IPv6 egress before vendor startup.',
            'Verify actual application, migration, browser, delivery, and restart/recovery behavior.',
        ]
        report['state'] = 'staged-not-activated'
        with (output / 'manifest.json').open('x', encoding='utf-8') as handle:
            json.dump(report, handle, indent=2)
            handle.write('\n')
        return report
    except Exception:
        with (output / 'FAILED').open('x', encoding='utf-8') as handle:
            handle.write('Staging failed. Never activate this directory; preserve it for private diagnosis.\n')
        raise
