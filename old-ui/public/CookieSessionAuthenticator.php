<?php

// 本当は認証サーバにRESTで問い合わせる
class CookieSessionAuthenticator
{
  const SESSION_NAME = "SESSION_TOKEN";
  const SESSION_TIMEOUT_IN_SECONDS = 3600;

  /**
   * @var User[]
   */
  private $users = [];

  /**
   * @var string[string]
   */
  private $tokens = [
    '470qpgo0yk1jf5x68thgv3x3nes3gqxt' => '6E7D8ACD-C36E-4ACC-87B7-5EC35CBA1423',
    'ij9wfyfn3w5zfam5k3helwboyh392sje' => 'D4EAF402-54CC-469A-AACB-66EF0A0CB0AF',
  ];

  public function __construct()
  {
    $this->users = [
      "6E7D8ACD-C36E-4ACC-87B7-5EC35CBA1423" => new User("6E7D8ACD-C36E-4ACC-87B7-5EC35CBA1423", "admin"),
      "D4EAF402-54CC-469A-AACB-66EF0A0CB0AF" => new User("D4EAF402-54CC-469A-AACB-66EF0A0CB0AF", "alice"),
    ];
  }

  /**
   * return logged in user
   * @return User|null
   */
  public function loggedIn()
  {
    if (isset($_COOKIE[self::SESSION_NAME]) === false) {
      return null;
    }

    $token = $_COOKIE[self::SESSION_NAME];
    $userId = $this->getUserIdByToken($token);
    if ($userId === null) {
      // todo: destory cookie
      return null;
    }

    $user = $this->getUserById($userId);
    if ($user === null) {
      return null;
    }

    return $user;
  }

  /**
   * @param $username string
   * @param $password string
   * @return bool
   */
  public function login($username, $password)
  {
    foreach ($this->users as $user) {
      // パスワードは気にしない
      if ($user->username() == $username) {
        $this->startSession($user->id(), self::SESSION_TIMEOUT_IN_SECONDS);
        return true;
      }
    }

    return false;
  }

  public function logout()
  {
    if (isset($_COOKIE[self::SESSION_NAME]) === false) {
      return;
    }

    setcookie(self::SESSION_NAME, '', 1, '/', null, false, true); // destory cookie

    return;
  }

  private function startSession($userId, $timeoutInSeconds)
  {
    setcookie(self::SESSION_NAME, $this->generateSessionId($userId), time() + $timeoutInSeconds, '/', null, false, true);
  }

  private function generateSessionId($userId)
  {
    foreach ($this->tokens as $token => $id) {
      if ($userId === $id) {
        return $token;
      }
    }

    throw new Exception("Unable to generate token.");
  }

  /**
   * @param $token string
   * @return string|null
   */
  private function getUserIdByToken($token)
  {
    if (isset($this->tokens[$token]) === false) {
      return null;
    }

    return $this->tokens[$token];
  }

  /**
   * @param $userId string
   * @return User|null
   */
  private function getUserById($userId)
  {
    if (isset($this->users[$userId]) === false) {
      return null;
    }

    return $this->users[$userId];
  }
}


class User
{
  private $id;
  private $username;

  public function __construct($id, $username)
  {
    $this->id = $id;
    $this->username = $username;
  }

  public function id()
  {
    return $this->id;
  }

  public function username()
  {
    return $this->username;
  }
}
