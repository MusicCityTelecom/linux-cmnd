'use strict';
const element = id => document.getElementById(id);
let csrf = '', release = null, running = false;
async function api(path, body) {
  const options = {credentials:'same-origin', cache:'no-store'};
  if (body) Object.assign(options, {method:'POST', headers:{'Content-Type':'application/json','X-CSRF-Token':csrf}, body:JSON.stringify(body)});
  const response = await fetch('api/' + path, options);
  const value = await response.json();
  if (!response.ok) { const error = new Error(value.error || 'Request failed'); error.status = response.status; throw error; }
  return value;
}
async function refresh() {
  if (running) return;
  running = true;
  try {
    const value = await api('status');
    csrf = value.csrf;
    element('login').hidden = true; element('dashboard').hidden = false;
    element('error').textContent = '';
    const links = element('applications'); links.replaceChildren();
    for (const [label, url] of Object.entries(value.applications)) {
      const link = document.createElement('a'); link.textContent = label; link.href = url; link.className = 'application'; links.append(link);
    }
    release = value.update.release;
    element('version').textContent = 'Installed tooling version: ' + value.update.current_version;
    element('update-status').textContent = value.update.error || (value.update.checking ? 'Checking GitHub…' :
      value.update.enabled === false ? 'Automatic checks are disabled by the administrator.' :
      release ? 'Version ' + release.version + ' is available' + (release.prerelease ? ' (evaluation release).' : '.') : 'No newer release is available in the configured channel.');
    const busy = ['verifying','backing-up','installing'].includes(value.job.state);
    element('offer').hidden = !release || busy;
    if (release) element('release-notes').href = release.url;
    element('job-status').textContent = 'Installer status: ' + value.job.state +
      (value.job.state === 'failed' ? '. Administrator review required. ' +
        (value.job.previous_runtime_recovered ? 'Previous runtime recovered and verified. ' : 'Runtime recovery has not been verified. ') +
        (value.job.backup_retained ? 'A complete database backup is retained privately.' : 'No complete database backup was recorded; inspect private diagnostics.') :
       value.job.state === 'complete' ? '. Update completed and application readiness verified.' : '');
  } catch (error) {
    if (error.status === 401) { element('login').hidden = false; element('dashboard').hidden = true; }
    else element('error').textContent = error.message;
  } finally { running = false; }
}
element('login-form').addEventListener('submit', async event => {
  event.preventDefault(); const target = event.currentTarget; const form = new FormData(target);
  try { await api('login', {username:form.get('username'), password:form.get('password')}); target.reset(); await refresh(); }
  catch (error) { element('error').textContent = error.message; }
});
element('check').addEventListener('click', async () => { try { await api('check', {}); await refresh(); } catch(error) { element('error').textContent = error.message; } });
element('install').addEventListener('click', async () => {
  if (!release || !confirm('Install Linux CMND ' + release.version + '? CMND will stop and restart; Java startup can take several minutes. This updates tooling only. Review and pause pending TV jobs first: vendor services can resume them after restart.')) return;
  element('install').disabled = true;
  try { await api('install', {version:release.version, release_id:release.release_id, execute:true}); await refresh(); }
  catch(error) { element('error').textContent = error.message; }
  finally { element('install').disabled = false; }
});
refresh(); setInterval(refresh, 5000);
