"""Render a self-contained Docker Compose candidate from the same CMND staging code.

This path is intentionally separate from the qualified native runtime. It builds
a root-owned bundle for review and never starts containers automatically because
Philips TV LAN/discovery behavior under Docker host networking is not yet qualified.
"""
from __future__ import annotations
import argparse, hashlib, json, os, secrets, shutil, socket
from pathlib import Path
from urllib.parse import urlsplit
from .application_stage import ApplicationInputs, VENDOR_INPUT_HASHES, _tomcat_members, stage_application
from .artifacts import file_hash
from .certificates import CertificateConfig, provision_certificates
from .config import ConfigError, load_config
from .native_deploy import SCHEMAS
from .vendor_config import REQUIRED_SECRETS

VENDOR_ROOT=Path('/usr/lib/cmnd/vendor/7.5.9')
TOMCAT_ARCHIVE=Path('/usr/lib/cmnd/tomcat/apache-tomcat-9.0.121.tar.gz')
DEFAULT_JAVA_IMAGE='eclipse-temurin:17-jre-jammy'
MYSQL_IMAGE='mysql@sha256:4bc6bc963e6d8443453676cae56536f4b8156d78bae03c0145cbe47c2aad73bb'

def _write(path,data,mode=0o600):
    path.parent.mkdir(parents=True,exist_ok=True); raw=data.encode() if isinstance(data,str) else data
    fd=os.open(path,os.O_CREAT|os.O_EXCL|os.O_WRONLY,mode)
    with os.fdopen(fd,'wb') as h:h.write(raw)
def _sql_quote(v):return "'"+v.replace('\\','\\\\').replace("'","''")+"'"
def _bootstrap_sql(vendor,values,admin_password):
    parts=[b'SET sql_mode="NO_ENGINE_SUBSTITUTION";\n']
    for schema,user,folder,numbers in SCHEMAS:
        pw=values[folder+'_db_password']; parts.append((f'CREATE DATABASE `{schema}` CHARACTER SET utf8 COLLATE utf8_general_ci;\nCREATE USER {_sql_quote(user)}@\'%\' IDENTIFIED BY {_sql_quote(pw)};\nGRANT ALL ON `{schema}`.* TO {_sql_quote(user)}@\'%\';\nUSE `{schema}`;\n').encode())
        for n in numbers:parts += [(vendor/'SQLScripts'/folder/f'sql_{n}.sql').read_bytes(),b'\n']
    digest=hashlib.md5(admin_password.encode()).hexdigest(); parts.append(("USE `cas`; DELETE FROM users; INSERT INTO users(username,password,role) VALUES"+f"('admin','{digest}','ADMIN');\n").encode()); return b''.join(parts)
def _compose(java_image):
    return f'''name: cmnd-linux\nservices:\n  db:\n    image: {MYSQL_IMAGE}\n    network_mode: host\n    restart: unless-stopped\n    command: ["--bind-address=127.0.0.1", "--port=${{CMND_DB_PORT}}", "--event-scheduler=ON", "--local-infile=0", "--sql-mode=NO_ENGINE_SUBSTITUTION", "--lower-case-table-names=1"]\n    environment:\n      MYSQL_ROOT_PASSWORD_FILE: /run/secrets/mysql_root\n    volumes:\n      - ./state/mysql:/var/lib/mysql\n      - ./secrets/mysql-root-password:/run/secrets/mysql_root:ro\n      - ./mysql-init:/docker-entrypoint-initdb.d:ro\n  php:\n    build: ./php-image\n    network_mode: host\n    restart: unless-stopped\n    read_only: true\n    security_opt: ["no-new-privileges:true"]\n    cap_drop: ["ALL"]\n    tmpfs: ["/tmp:rw,nosuid,noexec,size=256m"]\n    volumes:\n      - ./payload/SmartCMS:/opt/cmnd/SmartCMS:ro\n      - ./state/cms-files:/opt/cmnd/SmartCMS/sites/default/files\n      - ./state/php-uploads:/var/lib/cmnd/php-uploads\n      - ./state/cas-pgt:/var/lib/cmnd/cas-pgt\n      - ./config/php-fpm.conf:/usr/local/etc/php-fpm.conf:ro\n      - /etc/ssl/certs:/etc/ssl/certs:ro\n    depends_on: [db]\n  tomcat:\n    image: {java_image}\n    network_mode: host\n    restart: unless-stopped\n    working_dir: /opt/cmnd/tomcat\n    command: ["/opt/cmnd/tomcat/bin/catalina.sh", "run"]\n    environment:\n      JAVA_HOME: /opt/java/openjdk\n      CATALINA_OPTS: -Xms512m -Xmx4096m -Djava.awt.headless=true -Djavax.net.ssl.trustStore=/etc/cmnd/java-cacerts\n    volumes:\n      - ./payload/tomcat:/opt/cmnd/tomcat\n      - ./payload/Philips:/opt/Philips\n      - ./config/java-cacerts:/etc/cmnd/java-cacerts:ro\n      - /sys/class/dmi/id:/sys/class/dmi/id:ro\n    depends_on: [db]\n  apache:\n    build: ./apache-image\n    network_mode: host\n    restart: unless-stopped\n    volumes:\n      - ./payload/SmartCMS:/opt/cmnd/SmartCMS:ro\n      - ./state/cms-files:/opt/cmnd/SmartCMS/sites/default/files\n      - ./config/apache.conf:/etc/cmnd/apache.conf:ro\n      - ./payload/tls:/etc/cmnd/tls:ro\n      - ./state/log:/var/log/cmnd\n      - ./state/run:/run/cmnd\n    depends_on: [php]\n'''
def render(config_path,output,*,java_image=DEFAULT_JAVA_IMAGE,execute=False):
    config=load_config(config_path)
    if not java_image or any(c.isspace() for c in java_image):raise ConfigError('invalid Java container image reference')
    for p in (VENDOR_ROOT,TOMCAT_ARCHIVE):
        if not p.exists() or p.is_symlink():raise ConfigError('required packaged Docker input missing: '+str(p))
    for name,expected in VENDOR_INPUT_HASHES.items():
        p=VENDOR_ROOT/name
        if not p.is_file() or file_hash(p)!=expected:raise ConfigError('vendor package does not match pinned input: '+name)
    _tomcat_members(TOMCAT_ARCHIVE)
    report={'executed':execute,'output':str(output),'java_image':java_image,'mysql_image':MYSQL_IMAGE,'network_mode':'host','qualified':False,'warning':'Docker host-network TV discovery/control is not yet physical-TV qualified; review before starting containers.'}
    if not execute:return report
    if os.geteuid()!=0:raise ConfigError('docker bundle rendering requires root')
    if output.exists() or output.is_symlink():raise ConfigError('Docker bundle output must not already exist')
    output.mkdir(parents=True,mode=0o700); os.umask(0o077)
    try:
        values={k:secrets.token_hex(24) for k in (*REQUIRED_SECRETS,'tpvision_db_password')}; admin=secrets.token_hex(24); root=secrets.token_hex(32); certpass=output/'secrets/certificate-password'; _write(certpass,values['cert_ca_password'])
        addresses=[config.bind] if config.bind!='0.0.0.0' else []; public=urlsplit(config.callback_base_url).hostname or config.bind
        certs=provision_certificates(CertificateConfig(output/'certificates',public,addresses,socket.gethostname(),certpass,certpass)); stage=output/'stage'; stage_application(ApplicationInputs(VENDOR_ROOT,TOMCAT_ARCHIVE,certs),config,values,stage,execute=True,database_host='127.0.0.1'); shutil.move(str(stage/'payload'),str(output/'payload')); shutil.move(str(stage/'native-config'),str(output/'config')); shutil.rmtree(stage)
        _write(output/'secrets/mysql-root-password',root); _write(output/'mysql-init/00-cmnd.sql',_bootstrap_sql(VENDOR_ROOT,values,admin)); _write(output/'initial-admin.json',json.dumps({'username':'admin','password':admin},indent=2)+'\n'); _write(output/'.env',f'CMND_DB_PORT={config.database_port}\n'); _write(output/'compose.yaml',_compose(java_image))
        shutil.copytree(Path('/opt/linux-cmnd/current/deploy/php'),output/'php-image'); _write(output/'apache-image/Dockerfile','FROM ubuntu:24.04\nENV DEBIAN_FRONTEND=noninteractive\nRUN apt-get update && apt-get install -y --no-install-recommends apache2-bin media-types ca-certificates && rm -rf /var/lib/apt/lists/*\nCMD ["/usr/sbin/apache2","-f","/etc/cmnd/apache.conf","-DFOREGROUND"]\n')
        for d in ('state/mysql','state/cms-files','state/php-uploads','state/cas-pgt','state/log','state/run'):(output/d).mkdir(parents=True,exist_ok=True)
        _write(output/'README.txt','CMND Linux 0.8 Docker candidate\n\nReview compose.yaml and manifest.json before starting.\nStart: docker compose --env-file .env -f compose.yaml up -d --build\nStop: docker compose --env-file .env -f compose.yaml down\n\nHost-network discovery/control is not yet physical-TV qualified. Do not expose legacy services to the Internet.\n'); report['initial_admin_file']='initial-admin.json'; report['state']='rendered-not-started'; _write(output/'manifest.json',json.dumps(report,indent=2)+'\n'); return report
    except Exception:
        try:_write(output/'FAILED','Docker bundle rendering failed; preserve for diagnosis.\n')
        except Exception:pass
        raise
def main(argv=None):
    p=argparse.ArgumentParser(prog='cmndctl docker-render'); p.add_argument('--config',type=Path,default=Path('/etc/cmnd/cmnd.toml')); p.add_argument('--output',type=Path,required=True); p.add_argument('--java-image',default=DEFAULT_JAVA_IMAGE); p.add_argument('--execute',action='store_true'); a=p.parse_args(argv)
    try:r=render(a.config,a.output,java_image=a.java_image,execute=a.execute)
    except (ConfigError,RuntimeError,OSError) as e:print('cmndctl docker-render: '+str(e),file=os.sys.stderr); return 2
    print(json.dumps(r,indent=2,sort_keys=True)); return 0
