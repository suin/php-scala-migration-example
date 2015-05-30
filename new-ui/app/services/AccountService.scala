package services

import models.account.User

// 本当は認証サーバにRESTで問い合わせる
object AccountService {
  private val users = List[User](
    User("6E7D8ACD-C36E-4ACC-87B7-5EC35CBA1423", "admin", models.account.Role.Administrator),
    User("D4EAF402-54CC-469A-AACB-66EF0A0CB0AF", "alice", models.account.Role.Administrator)
  )

  private val tokenUser = List[(String, String)](
    ("6E7D8ACD-C36E-4ACC-87B7-5EC35CBA1423", "470qpgo0yk1jf5x68thgv3x3nes3gqxt"),
    ("D4EAF402-54CC-469A-AACB-66EF0A0CB0AF", "ij9wfyfn3w5zfam5k3helwboyh392sje")
  )

  def userOfId(userId: String): Option[User] = {
    users.find(_.id == userId)
  }

  def authenticate(username: String, password: String): Option[User] = {
    users.find(_.username == username)
  }

  object Session {
    def startSession(userId: String, timeoutInSeconds: Int): String = {
      tokenUser.find(_._1 == userId).map(_._2).get
    }

    def userIdOf(token: String): Option[String] = {
      tokenUser.find(_._2 == token).map(_._1)
    }

    def removeToken(token: String): Unit = {
      // do nothing
    }

    def changeTokenExpires(token: String, timeoutInSeconds: Int): Unit = {
      // do noting
    }
  }
}
