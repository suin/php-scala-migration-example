<?php
require("CookieSessionAuthenticator.php");

$serverName = preg_replace('/:.*$/', '', $_SERVER['HTTP_HOST']); // remove port number.
define('NEW_UI_HOST', $serverName . ':9000');
define('ENDPOINT', 'http://' . NEW_UI_HOST);

$auth = new CookieSessionAuthenticator();

$action = isset($_GET['action']) ? $_GET['action'] : '';

if ($action == 'login') {
  $username = isset($_POST['username']) ? $_POST['username'] : '';
  $password = isset($_POST['password']) ? $_POST['password'] : '';

  if ($auth->login($username, $password) === false) {
    echo 'Login fail. please go back to login form.';
    exit;
  } else {
    header('Location: /');
    exit;
  }
}

if ($action == 'logout') {
  $auth->logout();
  header('Location: /');
  exit;
}

?>
<!doctype html>
<meta charset="utf-8">
<title>This is an old UI built in PHP</title>

<h1>PHP</h1>

<div>This is <u>PHP side</u>.</div>

<?php if ($auth->loggedIn()): ?>
  <form action="/?action=logout" method="post" style="margin: 20px 0;">
    <button>Logout</button>
  </form>
<?php else: ?>
  <form action="/?action=login" method="post">
    <div>Login on PHP</div>
    <div>
      <label for="username">Username</label>
      <input type="text" name="username" required="required">
    </div>
    <div>
      <label for="password">Password</label>
      <input type="password" name="password" required="required">
    </div>
    <div>
      <button>Login</button>
    </div>
  </form>

  <div>Available accounts:</div>
  <ul>
    <li>admin:pass</li>
    <li>alice:pass</li>
  </ul>
<?php endif ?>

<div>Cookie:</div>
<pre><?php var_dump($_COOKIE); ?></pre>

<div>User:</div>
<pre><?php var_dump($auth->loggedIn()); ?></pre>
