"""Content-minimized diagnostic report for the disposable CMND VM."""
from pathlib import Path
from urllib.request import build_opener, ProxyHandler, HTTPRedirectHandler
from urllib.error import HTTPError, URLError
import collections
import json
import re
import subprocess
from urllib.parse import urlsplit


class NoRedirect(HTTPRedirectHandler):
    def redirect_request(self, *args):
        return None


def report():
    result = {'http': {}, 'logs': {}}
    opener = build_opener(ProxyHandler({}), NoRedirect())
    for context in ('cas/login', 'usermanagement/', 'smartcontrol/', 'SmartInstall/', 'smartcms/'):
        try:
            response = opener.open('http://127.0.0.1:8080/' + context, timeout=10)
        except HTTPError as exc:
            response = exc
        except URLError as exc:
            result['http'][context] = type(exc.reason).__name__
            continue
        except TimeoutError:
            result['http'][context] = 'TimeoutError'
            continue
        with response:
            body = response.read(1024 * 1024)
            result['http'][context] = {'status': response.code, 'bytes': len(body),
                'login_form': b'type="password"' in body or b"type='password'" in body}
            if response.headers.get('Location'):
                redirect = urlsplit(response.headers['Location'])
                result['http'][context]['redirect'] = redirect.scheme + '://' + redirect.netloc + redirect.path
    for path in Path('/usr/local/tomcat/logs').glob('*.log'):
        text = path.read_text(errors='replace')
        errors = collections.Counter(re.findall(r'\b(?:[A-Za-z_$]\w*\.)+[A-Za-z_$]\w*(?:Exception|Error)\b', text))
        result['logs'][path.name] = {'exception_classes': dict(errors),
            'severe_lines': sum('SEVERE' in line for line in text.splitlines())}
    client = ['docker', 'exec', '-i', 'cmnd-lab-mysql', 'mysql', '--defaults-extra-file=/run/secrets/client.cnf', '-N', '-B']
    query = b"SELECT table_schema,COUNT(*) FROM information_schema.tables WHERE table_schema IN ('cas','tpvision','smartcms','smartcontroldb','smartinstall') GROUP BY table_schema;"
    counts = subprocess.run(client, input=query, capture_output=True, check=True).stdout.decode()
    result['database_table_counts'] = {line.split('\t')[0]: int(line.split('\t')[1]) for line in counts.splitlines()}
    result['migrations'] = {}
    for schema, table in (('smartinstall', 'flyway_schema_history'), ('smartcontroldb', 'schema_version')):
        history = subprocess.run(client, input=f'SELECT version,success FROM {schema}.{table} ORDER BY installed_rank;'.encode(), capture_output=True)
        rows = history.stdout.decode().splitlines()
        result['migrations'][schema] = {'query_ok': history.returncode == 0, 'rows': len(rows),
            'last': rows[-1].split('\t') if rows else None, 'all_success': bool(rows) and all(row.endswith('\t1') for row in rows)}
    return result


if __name__ == '__main__':
    print(json.dumps(report(), indent=2))
