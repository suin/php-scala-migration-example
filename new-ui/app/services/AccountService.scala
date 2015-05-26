package services

import models.account.User

// 本当はアカウントサーバにRESTで問い合わせる
object AccountService {
  def userOfId(id: String): Option[User] = {
    id match {
      case "admin" => Some(User("admin", "admin", models.account.Role.Administrator))
      case "alice" => Some(User("alice", "alice", models.account.Role.Administrator))
      case _ => None
    }
  }

  def authenticate(username: String, password: String): Option[User] = {
    username match {
      case "admin" => Some(User("admin", "admin", models.account.Role.Administrator))
      case "alice" => Some(User("alice", "alice", models.account.Role.Administrator))
      case _ => None
    }
  }
}