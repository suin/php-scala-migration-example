package models

package object account {

  case class User(
                   id: String,
                   username: String,
                   role: Role)


  sealed trait Role

  object Role {
    case object Administrator extends Role
    case object NormalUser extends Role
  }
}
