package controllers

import jp.t2v.lab.play2.auth.AuthElement
import models.account.Role.NormalUser
import play.api.mvc._
import play.api.libs.json._

object UserApi extends Controller with AuthElement with ProprietaryAuthConfig {
  def user = StackAction(AuthorityKey -> NormalUser) { implicit request =>
    val user = loggedIn
    Ok(Json.obj("id" -> user.id, "username" -> user.username))
  }
}
