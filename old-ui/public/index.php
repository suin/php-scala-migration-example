<?php

define('NEW_UI_HOST', $_SERVER['HTTP_HOST'].':9000');
?>

<h1>top page</h1>
<form id="login-form">
  <div>ログイン</div>
  <div>
    <label>ユーザ名: <input id="username-field" type="text" name="username" required></label>
  </div>
  <div>
    <label>パスワード: <input id="password-field" type="password" name="password" required></label>
  </div>
  <div>
    <button id="login-button">ログイン</button>
  </div>
</form>

<script src="//code.jquery.com/jquery-1.11.3.min.js"></script>
<script src="//<?= NEW_UI_HOST ?>/assets/javascripts/hello.js"></script>
