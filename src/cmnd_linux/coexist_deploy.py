"""0.8 coexistence-aware fresh deployment engine.

The v0.7.1-derived native_deploy module remains unchanged. This module reuses its
verified primitives while allowing a pre-qualified host MySQL service and/or a
CMND-owned fragment in an existing Debian/Ubuntu Apache service.
"""
from __future__ import annotations
from dataclasses import dataclass
import hashlib, json, os, platform, re, secrets, shutil, socket, ssl, subprocess, time
from pathlib import Path
from urllib.error import HTTPError, URLError
from urllib.parse import urlsplit
from urllib.request import HTTPSHandler, HTTPRedirectHandler, ProxyHandler, build_opener

from .apache_integration import apply as apache_apply, detach as apache_detach, supported as apache_supported
from .application_stage import ApplicationInputs, VENDOR_INPUT_HASHES, _tomcat_members, stage_application
from .artifacts import file_hash
from .certificates import CertificateConfig, provision_certificates
from .config import Config, ConfigError, load_config
from .egress import EgressPolicy, apply_policy
from .native_config import native_endpoint, render_native_files
from .native_deploy import (BASELINE, ETC, LAYOUT, MANAGEMENT, MYSQL_IMAGE, SCHEMAS, STATE,
    _mysql, _owned_tree, https_origin, mysql_server_options, run, validate_baseline,
    validate_image, wait_database, wait_php, write_new)
from .shared_database import (SharedDatabaseSession, migration_histories as shared_histories,
    wait_shared_database, write_health_files)
from .vendor_config import REQUIRED_SECRETS

@dataclass(frozen=True)
class CoexistInputs:
    config: Path; vendor: Path; tomcat_archive: Path; php_image: str; java_home: Path
    database_mode: str='isolated'; apache_mode: str='standalone'
    shared_database: SharedDatabaseSession|None=None; apt_managed: bool=True

def os_support(content,machine):
    values={}
    for line in content.splitlines():
        if '=' in line and not line.startswith('#'):
            k,v=line.split('=',1); values[k]=v.strip('"\'')
    selected=(values.get('ID',''),values.get('VERSION_ID',''))
    if selected not in {('ubuntu','22.04'),('ubuntu','24.04'),('ubuntu','26.04'),('debian','12'),('debian','13')}:
        raise ConfigError('0.8 supports Ubuntu 22.04/24.04/26.04 or Debian 12/13 candidates')
    if machine not in {'x86_64','amd64'}: raise ConfigError('CMND requires amd64')
    return selected

def _units(java_home,db_mode,apache_mode,apt_managed=True):
    if db_mode not in {'isolated','shared'} or apache_mode not in {'standalone','host'}: raise ConfigError('invalid coexistence mode')
    dbreq='Requires=cmnd-mysql.service\nAfter=cmnd-mysql.service\n' if db_mode=='isolated' else 'After=network-online.target\n'
    dbwait='/usr/bin/cmndctl native-wait-database' if db_mode=='isolated' else '/usr/bin/cmndctl shared-wait-database'
    before='cmnd-php.service cmnd-tomcat.service'+(' cmnd-mysql.service' if db_mode=='isolated' else '')+(' cmnd-apache.service' if apache_mode=='standalone' else '')
    u={
    'cmnd-egress.service':f'''[Unit]\nDescription=CMND egress isolation\nAfter=local-fs.target\nBefore={before}\n[Service]\nType=oneshot\nRemainAfterExit=yes\nEnvironment=PATH=/usr/sbin:/usr/bin:/sbin:/bin\nExecStart=/usr/bin/python3 /etc/cmnd/egress.py --policy /etc/cmnd/egress.json --execute\nRuntimeDirectory=cmnd\nRuntimeDirectoryMode=0755\nNoNewPrivileges=true\nProtectHome=true\nProtectSystem=strict\nReadWritePaths=/run\nCapabilityBoundingSet=CAP_NET_ADMIN CAP_NET_RAW\n[Install]\nWantedBy=multi-user.target\n''',
    'cmnd-php.service':f'''[Unit]\nDescription=CMND legacy PHP runtime\nRequires=cmnd-egress.service docker.service\nAfter=cmnd-egress.service docker.service\n{dbreq}[Service]\nType=simple\nExecStartPre={dbwait}\nExecStart=/usr/bin/docker start -a cmnd-native-php\nExecStop=/usr/bin/docker stop -t 30 cmnd-native-php\nRestart=on-failure\nTimeoutStartSec=180\n[Install]\nWantedBy=multi-user.target\n''',
    'cmnd-tomcat.service':f'''[Unit]\nDescription=Philips CMND Java applications\nRequires=cmnd-egress.service\nAfter=cmnd-egress.service\n{dbreq}[Service]\nType=simple\nUser=cmnd\nGroup=cmnd\nEnvironment=JAVA_HOME={java_home}\nEnvironmentFile=/etc/cmnd/native-tomcat.env\nWorkingDirectory=/opt/cmnd/tomcat\nExecStartPre=+/usr/bin/cmndctl native-hardware-access --execute\nExecStartPre=+{dbwait}\nExecStart=/opt/cmnd/tomcat/bin/catalina.sh run\nSuccessExitStatus=143\nRestart=on-failure\nUMask=0027\nNoNewPrivileges=true\nPrivateTmp=true\nProtectHome=true\nProtectSystem=strict\nReadWritePaths=/opt/Philips /opt/cmnd/tomcat /var/lib/cmnd /var/log/cmnd\n[Install]\nWantedBy=multi-user.target\n''',
    'cmnd-admin.service':'''[Unit]\nDescription=Linux CMND administration GUI\nAfter=network-online.target\n[Service]\nType=simple\nUser=cmnd-admin\nGroup=cmnd-admin\nExecStart=/usr/bin/cmndctl update-gui --settings /etc/linux-cmnd-management/admin.json\nRestart=on-failure\nUMask=0077\nNoNewPrivileges=true\nProtectSystem=strict\nProtectHome=true\nReadWritePaths=/var/lib/cmnd-update-requests\n[Install]\nWantedBy=multi-user.target\n'''}
    if db_mode=='isolated': u['cmnd-mysql.service']='''[Unit]\nDescription=CMND isolated MySQL\nRequires=cmnd-egress.service docker.service\nAfter=cmnd-egress.service docker.service\n[Service]\nType=simple\nExecStart=/usr/bin/docker start -a cmnd-native-mysql\nExecStop=/usr/bin/docker stop -t 30 cmnd-native-mysql\nRestart=on-failure\n[Install]\nWantedBy=multi-user.target\n'''
    if apache_mode=='standalone': u['cmnd-apache.service']='''[Unit]\nDescription=CMND standalone Apache\nRequires=cmnd-php.service\nAfter=cmnd-php.service\n[Service]\nType=simple\nExecStart=/usr/sbin/apache2 -f /etc/cmnd/apache.conf -DFOREGROUND\nExecReload=/usr/sbin/apache2 -f /etc/cmnd/apache.conf -k graceful\nRestart=on-failure\nNoNewPrivileges=true\nProtectSystem=strict\nProtectHome=true\nReadWritePaths=/run/cmnd /var/log/cmnd /opt/cmnd/SmartCMS/sites/default/files\n[Install]\nWantedBy=multi-user.target\n'''
    return u

def _port_free(port):
    with socket.socket() as s:
        try:s.bind(('0.0.0.0',port)); return True
        except OSError:return False

def preflight(i:CoexistInputs,c:Config):
    if i.database_mode not in {'isolated','shared'} or i.apache_mode not in {'standalone','host'}: raise ConfigError('invalid coexistence mode')
    native_endpoint(c); render_native_files(c,{k:'preflight-placeholder' for k in REQUIRED_SECRETS}); validate_image(i.php_image)
    distro=os_support(Path('/etc/os-release').read_text(),platform.machine()); validate_baseline(); _tomcat_members(i.tomcat_archive)
    for name,expected in VENDOR_INPUT_HASHES.items():
        p=i.vendor/name
        if not p.is_file() or p.is_symlink() or file_hash(p)!=expected: raise ConfigError('unrecognized vendor input: '+name)
    for cmd in ('docker','apache2','openssl','systemctl','systemd-analyze','iptables','ip6tables','ip','update-ca-certificates','useradd'):
        if not shutil.which(cmd): raise ConfigError('missing dependency: '+cmd)
    j=subprocess.run([str(i.java_home/'bin/java'),'-version'],capture_output=True)
    if j.returncode or not re.search(rb'version "17\.',j.stdout+j.stderr): raise ConfigError('CMND requires Java 17')
    if not os.access(i.java_home/'bin/keytool',os.X_OK): raise ConfigError('Java 17 keytool required')
    if not os.access('/usr/bin/cmnd-7zip',os.X_OK) or not any(os.access(p,os.X_OK) for p in ('/usr/bin/7zz','/usr/bin/7z')): raise ConfigError('7zip helpers required')
    for p in (STATE,MANAGEMENT,Path('/var/lib/cmnd-updates'),Path('/var/lib/cmnd-update-requests'),Path(LAYOUT.tomcat),Path(LAYOUT.cms),Path('/opt/Philips'),ETC/'deployment.json',ETC/'tls',ETC/'apache.conf',ETC/'php-fpm.conf',ETC/'native-tomcat.env',ETC/'java-cacerts',ETC/'egress.json'):
        if p.exists() or p.is_symlink(): raise ConfigError('fresh deployment refuses existing state: '+str(p))
    for name in ('cmnd-native-mysql','cmnd-native-php'):
        if subprocess.run(['docker','inspect',name],capture_output=True).returncode==0: raise ConfigError('existing CMND container: '+name)
    ports={c.tomcat_http,c.tomcat_https,c.apache_http,c.apache_https,LAYOUT.fpm_port,9078}
    if len(ports|{c.database_port})!=7: raise ConfigError('CMND ports collide')
    for port in ports:
        if not _port_free(port): raise ConfigError('planned CMND port occupied: '+str(port))
    db={'mode':i.database_mode}
    if i.database_mode=='isolated':
        if not _port_free(c.database_port): raise ConfigError('isolated DB port occupied')
        run('docker','image','inspect',MYSQL_IMAGE)
    else:
        if not i.shared_database or i.shared_database.port!=c.database_port: raise ConfigError('shared DB session/port mismatch')
        db.update(i.shared_database.validate_for_fresh_cmnd())
    if i.apache_mode=='host' and not apache_supported(): raise ConfigError('host Apache layout unsupported')
    run('docker','image','inspect',i.php_image)
    return {'os':list(distro),'database':db,'apache_mode':i.apache_mode,'vendor_inputs_verified':len(VENDOR_INPUT_HASHES)}

def _isolated_db(i,c,values):
    root=secrets.token_hex(32); write_new(STATE/'mysql-root-password',root); write_new(STATE/'mysql-client.cnf',f'[client]\nuser=root\npassword={root}\nhost=127.0.0.1\nport={c.database_port}\nprotocol=tcp\n'); (STATE/'mysql').mkdir(mode=0o700)
    run('docker','create','--name','cmnd-native-mysql','--network','host','--mount',f'type=bind,src={STATE}/mysql,dst=/var/lib/mysql','--mount',f'type=bind,src={STATE}/mysql-root-password,dst=/run/secrets/root-password,readonly','--mount',f'type=bind,src={STATE}/mysql-client.cnf,dst=/run/secrets/client.cnf,readonly','-e','MYSQL_ROOT_PASSWORD_FILE=/run/secrets/root-password',MYSQL_IMAGE,*mysql_server_options(c.database_port)); run('systemctl','start','cmnd-mysql'); wait_database()
    for schema,user,folder,numbers in SCHEMAS:
        _mysql((f'CREATE DATABASE `{schema}` CHARACTER SET utf8 COLLATE utf8_general_ci;CREATE USER \'{user}\'@\'%\' IDENTIFIED BY \'{values[folder+"_db_password"]}\';GRANT ALL ON `{schema}`.* TO \'{user}\'@\'%\';').encode())
        for n in numbers:_mysql((i.vendor/'SQLScripts'/folder/f'sql_{n}.sql').read_bytes(),schema)

def _admin(i):
    password=secrets.token_hex(24); sql="DELETE FROM cas.users; INSERT INTO cas.users(username,password,role) VALUES('admin','%s','ADMIN');"%hashlib.md5(password.encode()).hexdigest()
    _mysql(sql.encode()) if i.database_mode=='isolated' else i.shared_database.execute(sql,'cas'); return password

class _NoRedirect(HTTPRedirectHandler):
    def redirect_request(self,*args):return None

def _ready(c,db_mode,seconds=900):
    ctx=ssl.create_default_context(cafile=str(ETC/'tls/ca.crt')); opener=build_opener(ProxyHandler({}),HTTPSHandler(context=ctx),_NoRedirect()); host=urlsplit(c.callback_base_url).hostname; deadline=time.monotonic()+seconds; statuses={}; histories={}
    while time.monotonic()<deadline:
        for port,path in [(c.tomcat_https,p) for p in ('cas/login','SmartInstall/','smartcontrol/','usermanagement/','smartcms/')]+[(c.apache_https,'SmartCMS/'),(c.apache_https,'linux-cmnd/')]:
            try:
                with opener.open(f'https://{host}:{port}/{path}',timeout=5) as r:statuses[path]=r.status
            except HTTPError as e:statuses[path]=e.code
            except (URLError,OSError):statuses[path]=0
        if db_mode=='shared':histories=shared_histories()
        else:
            histories={}
            for schema,table,count,version in (('smartinstall','flyway_schema_history',122,'9.7'),('smartcontroldb','schema_version',27,'1.5.1')):
                try:
                    lines=_mysql(f'SELECT version,success FROM {schema}.{table} ORDER BY installed_rank;'.encode()).decode().splitlines(); histories[schema]=bool(len(lines)==count and lines[-1].split('\t')[0]==version and all(x.endswith('\t1') for x in lines))
                except RuntimeError:histories[schema]=False
        if all(v in (200,301,302,303) for v in statuses.values()) and all(histories.values()):return {'readiness_verified':True,'https_contexts':statuses,'migration_histories':histories}
        time.sleep(3)
    return {'readiness_verified':False,'https_contexts':statuses,'migration_histories':histories}

def deploy(i:CoexistInputs,*,execute=False,accept_legacy=False):
    c=load_config(i.config); report=preflight(i,c)|{'executed':execute,'database_mode':i.database_mode,'apache_mode':i.apache_mode,'apt_managed':i.apt_managed,'readiness_verified':False,'scope':'0.8 coexistence candidate'}
    if not execute:return report
    if os.name!='posix' or not hasattr(os,'geteuid') or os.geteuid()!=0 or not accept_legacy:
        raise ConfigError('root Linux execution and legacy-runtime acceptance required')
    import pwd
    os.umask(0o077); STATE.mkdir(mode=0o700); host_apache=False
    try:
        values={k:secrets.token_hex(24) for k in (*REQUIRED_SECRETS,'tpvision_db_password')}; write_new(STATE/'secrets.json',json.dumps(values)); write_new(STATE/'certificate-password',values['cert_ca_password'])
        addresses=json.loads(run('ip','-j','-4','address','show')); ips=sorted({a['local'] for x in addresses if 'UP' in x.get('flags',[]) for a in x.get('addr_info',[]) if a.get('family')=='inet'}); public,_=native_endpoint(c)
        for user,home in (('cmnd','/var/lib/cmnd'),('cmnd-cms','/var/lib/cmnd/cms'),('cmnd-admin','/var/lib/cmnd-update-requests')):
            try: account=pwd.getpwnam(user)
            except KeyError: run('useradd','--system','--user-group','--home-dir',home,'--shell','/usr/sbin/nologin',user); account=pwd.getpwnam(user)
            if account.pw_uid==0:raise ConfigError('unsafe existing CMND service account')
        java,cms=pwd.getpwnam('cmnd'),pwd.getpwnam('cmnd-cms'); webgid=pwd.getpwnam('www-data').pw_gid if i.apache_mode=='host' else cms.pw_gid
        certs=provision_certificates(CertificateConfig(STATE/'certificates',public,ips,socket.gethostname(),STATE/'certificate-password',STATE/'certificate-password')); stage_application(ApplicationInputs(i.vendor,i.tomcat_archive,certs),c,values,STATE/'candidate',execute=True,database_host='127.0.0.1'); payload,native=STATE/'candidate/payload',STATE/'candidate/native-config'; Path('/opt/cmnd').mkdir(mode=0o755,exist_ok=True)
        for name,target in (('tomcat',Path(LAYOUT.tomcat)),('SmartCMS',Path(LAYOUT.cms)),('Philips',Path('/opt/Philips'))):shutil.copytree(payload/name,target)
        (Path(LAYOUT.tomcat)/'conf/Catalina/localhost').mkdir(parents=True,exist_ok=True); _owned_tree(Path(LAYOUT.tomcat),java.pw_uid,java.pw_gid); _owned_tree(Path('/opt/Philips'),java.pw_uid,java.pw_gid); _owned_tree(Path(LAYOUT.cms),0,webgid); _owned_tree(Path(LAYOUT.cms)/'sites/default/files',cms.pw_uid,webgid)
        for d,uid,gid in ((Path('/var/lib/cmnd'),java.pw_uid,java.pw_gid),(Path('/var/lib/cmnd/smartcontrol'),java.pw_uid,java.pw_gid),(Path('/var/log/cmnd'),java.pw_uid,java.pw_gid),(Path(LAYOUT.php_uploads),cms.pw_uid,cms.pw_gid),(Path(LAYOUT.pgt),cms.pw_uid,cms.pw_gid)):d.mkdir(parents=True,exist_ok=True); os.chown(d,uid,gid); d.chmod(0o750)
        ETC.mkdir(parents=True,exist_ok=True); shutil.copytree(payload/'tls',ETC/'tls')
        for name in ('apache.conf','php-fpm.conf','configure-cms.php'):write_new(ETC/name,(native/name).read_bytes(),0o640); os.chown(ETC/name,0,webgid)
        for p in (ETC/'tls').iterdir():p.chmod(0o600)
        write_new(ETC/'native-tomcat.env',(native/'computername.env').read_text()+'\nCATALINA_OPTS="-Xms512m -Xmx4096m -Djava.awt.headless=true -Djavax.net.ssl.trustStore=/etc/cmnd/java-cacerts"\n',0o640)
        os.chown(ETC/'native-tomcat.env',0,java.pw_gid)
        shutil.copyfile(i.java_home/'lib/security/cacerts',ETC/'java-cacerts')
        write_new(STATE/'truststore-password','changeit')
        run(i.java_home/'bin/keytool','-importcert','-noprompt','-alias','linux-cmnd','-file',certs.ca_pem,'-keystore',ETC/'java-cacerts','-storepass:file',STATE/'truststore-password')
        os.chown(ETC/'java-cacerts',0,java.pw_gid)
        (ETC/'java-cacerts').chmod(0o640)
        write_new(Path('/usr/local/share/ca-certificates/linux-cmnd.crt'),certs.ca_pem.read_bytes(),0o644)
        run('update-ca-certificates')
        endpoints={('127.0.0.1',c.database_port)}|{(a,p) for a in ips for p in (c.tomcat_http,c.tomcat_https,c.apache_http,c.apache_https)}; policy=EgressPolicy('CMND_NATIVE',(java.pw_uid,cms.pw_uid),tuple(sorted(endpoints))); write_new(ETC/'egress.json',json.dumps({'chain':policy.chain,'uids':policy.uids,'tcp_endpoints':policy.tcp_endpoints})); write_new(ETC/'egress.py',Path(__file__).with_name('egress.py').read_bytes(),0o644)
        units=_units(i.java_home,i.database_mode,i.apache_mode,i.apt_managed)
        for name,text in units.items():write_new(Path('/etc/systemd/system')/name,text,0o644)
        run('systemctl','daemon-reload'); run('systemctl','enable','--now','cmnd-egress'); apply_policy(policy,execute=True)
        if i.database_mode=='isolated':_isolated_db(i,c,values)
        else: report['shared_database_initialization']=i.shared_database.initialize(i.vendor,values); write_health_files(values,c.database_port); wait_shared_database()
        adminpass=_admin(i)
        write_new(STATE/'initial-admin.json',json.dumps({'username':'admin','password':adminpass}))
        from .update_gui import password_record
        admin=pwd.getpwnam('cmnd-admin')
        MANAGEMENT.mkdir(mode=0o750)
        os.chown(MANAGEMENT,0,admin.pw_gid)
        MANAGEMENT.chmod(0o750)
        updates_dir=Path('/var/lib/cmnd-updates')
        updates_dir.mkdir(mode=0o750)
        updates_dir.chmod(0o750)
        queue=Path('/var/lib/cmnd-update-requests')
        queue.mkdir(mode=0o700)
        os.chown(queue,admin.pw_uid,admin.pw_gid)
        queue.chmod(0o700)
        write_new(MANAGEMENT/'admin.json',json.dumps({'password':password_record(adminpass),'origin':https_origin(public,c.apache_https),'check_on_startup':not i.apt_managed,'applications':{'TV management':f'https://{public}:{c.tomcat_https}/SmartInstall/','Site and content editor':f'https://{public}:{c.apache_https}/SmartCMS/'}}),0o640)
        write_new(MANAGEMENT/'updates.json',json.dumps({'enabled':not i.apt_managed,'channel':'apt' if i.apt_managed else 'preview','token_file':None}),0o640)
        for management_file in MANAGEMENT.iterdir():
            os.chown(management_file,0,admin.pw_gid)
            management_file.chmod(0o640)
        validate_baseline()
        shutil.copyfile(BASELINE,'/var/lib/cmnd-updates/current.deb')
        Path('/var/lib/cmnd-updates/current.deb').chmod(0o600)
        create=['docker','create','--name','cmnd-native-php','--network','host','--user',f'{cms.pw_uid}:{cms.pw_gid}','--read-only','--cap-drop','ALL','--pids-limit','128','--memory','1g','--security-opt','no-new-privileges','--tmpfs','/tmp:rw,nosuid,noexec,size=256m']; create += (['--group-add',str(webgid)] if i.apache_mode=='host' else []); create += ['--mount',f'type=bind,src={LAYOUT.cms},dst={LAYOUT.cms},readonly','--mount',f'type=bind,src={LAYOUT.cms}/sites/default/files,dst={LAYOUT.cms}/sites/default/files','--mount',f'type=bind,src={LAYOUT.pgt},dst={LAYOUT.pgt}','--mount',f'type=bind,src={LAYOUT.php_uploads},dst={LAYOUT.php_uploads}','--mount','type=bind,src=/etc/cmnd/php-fpm.conf,dst=/usr/local/etc/php-fpm.conf,readonly','--mount','type=bind,src=/etc/ssl/certs,dst=/etc/ssl/certs,readonly',i.php_image]; run(*create); run('systemctl','start','cmnd-php'); wait_php(); run('docker','exec','-i','-e','CMND_CMS_EXECUTE=1','cmnd-native-php','php',data=(ETC/'configure-cms.php').read_bytes())
        if i.apache_mode=='host': report['host_apache']=apache_apply(c,execute=True); host_apache=True
        else: run('apache2','-t','-f',ETC/'apache.conf'); run('systemctl','start','cmnd-apache')
        run('systemctl','start','cmnd-admin','cmnd-tomcat'); report.update(_ready(c,i.database_mode))
        if not report['readiness_verified']:raise RuntimeError('coexistence readiness failed')
        enabled=['cmnd-php','cmnd-tomcat','cmnd-admin']+(['cmnd-mysql'] if i.database_mode=='isolated' else [])+(['cmnd-apache'] if i.apache_mode=='standalone' else []); run('systemctl','enable',*enabled); write_new(ETC/'deployment.json',json.dumps({'state':'active','managed_by':'linux-cmnd-apt' if i.apt_managed else 'linux-cmnd-coexist','database_mode':i.database_mode,'apache_mode':i.apache_mode,'vendor':'7.5.9','config_sha256':file_hash(i.config)})); write_new(STATE/'qualification.json',json.dumps(report,indent=2)); return report
    except Exception:
        subprocess.run(['systemctl','stop','cmnd-tomcat','cmnd-apache','cmnd-php','cmnd-admin'],capture_output=True,timeout=120)
        if host_apache:
            try:apache_detach(execute=True)
            except Exception:pass
        if not (STATE/'FAILED').exists():write_new(STATE/'FAILED','Incomplete 0.8 coexistence deployment; preserve state and inspect private logs.\n')
        raise
