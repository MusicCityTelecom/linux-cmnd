"""Add synthetic TLS and vendor ROOT callback mappings to the lab VM only."""
from pathlib import Path
import json
import os
import shutil
import socket
import xml.etree.ElementTree as ET
from lab_vendor_runtime import BASE, run, write


def main():
    if os.geteuid() or socket.gethostname() != 'cmnd-qualification':
        raise SystemExit('Requires the dedicated lab VM')
    tomcat = Path('/usr/local/tomcat')
    saved = BASE / 'before-tls'
    saved.mkdir(mode=0o700)  # Refuse reapplication over unknown state.
    run('systemctl', 'stop', 'cmnd-lab-tomcat')
    for name in ('server.xml', 'context.xml'):
        shutil.copy2(tomcat / 'conf' / name, saved / name)
    values = json.loads((BASE / 'secrets.json').read_text())
    write(BASE / 'tls-password', values['cert_ca_password'])
    run('openssl', 'req', '-x509', '-newkey', 'rsa:2048', '-nodes', '-days', '30',
        '-keyout', str(BASE / 'lab.key'), '-out', str(BASE / 'lab.crt'),
        '-subj', '/CN=cmnd-qualification', '-addext', 'subjectAltName=DNS:cmnd-qualification,IP:127.0.0.1')
    run('openssl', 'pkcs12', '-export', '-name', 'tomcat', '-inkey', str(BASE / 'lab.key'),
        '-in', str(BASE / 'lab.crt'), '-out', str(tomcat / 'server.p12'), '-passout', f'file:{BASE}/tls-password')
    run('chown', 'cmnd:cmnd', str(tomcat / 'server.p12'))
    # Trust only this synthetic lab certificate inside this disposable guest.
    shutil.copy2(BASE / 'lab.crt', '/usr/local/share/ca-certificates/cmnd-qualification.crt')
    run('chmod', '0644', '/usr/local/share/ca-certificates/cmnd-qualification.crt')
    run('update-ca-certificates')
    root = ET.Element('Server', port='-1')
    service = ET.SubElement(root, 'Service', name='Catalina')
    ET.SubElement(service, 'Connector', port='8080', protocol='HTTP/1.1', connectionTimeout='20000', redirectPort='8443')
    connector = ET.SubElement(service, 'Connector', port='8443', protocol='com.tpv.smartinstall.util.ReloadProtocol', SSLEnabled='true', maxThreads='150')
    sslhost = ET.SubElement(connector, 'SSLHostConfig')
    ET.SubElement(sslhost, 'Certificate', certificateKeystoreFile=str(tomcat / 'server.p12'),
                  certificateKeystorePassword=values['cert_ca_password'], certificateKeystoreType='PKCS12', certificateKeyAlias='tomcat')
    engine = ET.SubElement(service, 'Engine', name='Catalina', defaultHost='localhost')
    ET.SubElement(engine, 'Host', name='localhost', appBase='webapps', unpackWARs='true', autoDeploy='false')
    ET.ElementTree(root).write(tomcat / 'conf/server.xml', encoding='utf-8', xml_declaration=True)
    os.chmod(tomcat / 'conf/server.xml', 0o640)
    run('chown', 'root:cmnd', str(tomcat / 'conf/server.xml'))
    context = ET.Element('Context', swallowOutput='true')
    ET.SubElement(context, 'Valve', className='org.apache.catalina.valves.rewrite.RewriteValve')
    ET.ElementTree(context).write(tomcat / 'conf/context.xml', encoding='utf-8', xml_declaration=True)
    # Preserve stock examples/admin directories outside the served webapp tree.
    for name in ('docs', 'examples', 'manager', 'host-manager', 'ROOT'):
        source = tomcat / 'webapps' / name
        if source.exists():
            shutil.move(source, saved / name)
    webinf = tomcat / 'webapps/ROOT/WEB-INF'
    webinf.mkdir(parents=True)
    write(webinf / 'rewrite.config', 'RewriteCond %{REQUEST_URI} ^/$\nRewriteRule ^(.*)$ /SmartInstall/$1 [L]\nRewriteRule ^/webservices.jsp /SmartInstall/webservices.jsp [L]\nRewriteRule ^/(.*).jsp /SmartInstall/$1.jsp [L]\n', 0o640)
    run('chown', '-R', 'cmnd:cmnd', str(tomcat / 'webapps/ROOT'))
    run('systemctl', 'start', 'cmnd-lab-tomcat')
    print('Synthetic TLS and ROOT mappings staged; startup verification pending.')


if __name__ == '__main__':
    main()
