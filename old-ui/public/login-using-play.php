<?php

$serverName = preg_replace('/:.*$/', '', $_SERVER['HTTP_HOST']); // remove port number.
define('NEW_UI_HOST', $serverName . ':9000');
define('ENDPOINT', 'http://' . NEW_UI_HOST);

?>
<script src="<?= ENDPOINT ?>/assets/javascripts/bundle.login.js"></script>

<div id="mount-login-form" data-endpoint="<?= ENDPOINT ?>"></div>
