<?php
// Dedicated synthetic VM only; invoke through the private PHP container CLI.
if (PHP_SAPI !== 'cli' || !is_dir('/opt/cmnd-lab-cms/SmartCMS')) {
  exit(2);
}
chdir('/opt/cmnd-lab-cms/SmartCMS');
define('DRUPAL_ROOT', getcwd());
$_SERVER['HTTP_HOST'] = '127.0.0.1:8444';
$_SERVER['SERVER_NAME'] = '127.0.0.1';
$_SERVER['SERVER_PORT'] = '8444';
$_SERVER['REMOTE_ADDR'] = '127.0.0.1';
$_SERVER['HTTPS'] = 'on';
$_SERVER['REQUEST_METHOD'] = 'GET';
$_SERVER['REQUEST_URI'] = '/SmartCMS/';
$_SERVER['SCRIPT_NAME'] = '/SmartCMS/index.php';
require_once DRUPAL_ROOT . '/includes/bootstrap.inc';
drupal_bootstrap(DRUPAL_BOOTSTRAP_VARIABLES);
$names = array('cas_version', 'cas_uri', 'cas_cert', 'cas_debugfile', 'cas_proxy', 'cas_pgtpath');
foreach ($names as $name) {
  echo json_encode(array($name => variable_get($name, null))) . "\n";
}
if (getenv('CMND_LAB_EXECUTE') === '1') {
  // Native Drupal serialization/cache invalidation, never SQL string replacement.
  variable_set('cas_cert', '/etc/ssl/certs/ca-certificates.crt');
  variable_set('cas_debugfile', '');
  if (!is_dir('/tmp/cmnd-lab-pgt') && !mkdir('/tmp/cmnd-lab-pgt', 0700)) {
    exit(3);
  }
  variable_set('cas_pgtpath', '/tmp/cmnd-lab-pgt');
  echo "Guest trust store configured; CAS certificate validation remains enabled.\n";
}
try {
  require_once DRUPAL_ROOT . '/sites/all/libraries/CAS/CAS.php';
  phpCAS::setVerbose(true); // CLI-only diagnostic, never enabled in web requests.
  drupal_bootstrap(DRUPAL_BOOTSTRAP_FULL);
} catch (Exception $error) {
  echo json_encode(array('class' => get_class($error), 'message' => $error->getMessage())) . "\n";
}
