package services

import models.account.User

// 本当は認証サーバにRESTで問い合わせる
object AccountService {
  private val users = List[User](
    User("6E7D8ACD-C36E-4ACC-87B7-5EC35CBA1423", "admin", models.account.Role.Administrator),
    User("D4EAF402-54CC-469A-AACB-66EF0A0CB0AF", "alice", models.account.Role.Administrator)
  )

  def userOfId(id: String): Option[User] = {
    users.find(_.id == id)
  }

  def authenticate(username: String, password: String): Option[User] = {
    users.find(_.username == username)
  }
}
