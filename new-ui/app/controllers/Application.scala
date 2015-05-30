package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import jp.t2v.lab.play2.auth.{Logout, OptionalAuthElement}
import play.api.mvc._

object Application extends Controller with OptionalAuthElement with Logout with ProprietaryAuthConfig  {
  def index = StackAction { implicit request =>

    val cookies = request.cookies.map { cookie => cookie.name + ": " + cookie.value }.mkString("\n")

    val userOpt: Option[models.account.User] = loggedIn

    Ok(views.html.index(cookies, userOpt))
  }

  def logout = Action.async { implicit request =>
    gotoLogoutSucceeded
  }
}
