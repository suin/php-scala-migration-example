package controllers

import play.api.mvc._
import play.api.libs.json._
import jp.t2v.lab.play2.auth.LoginLogout
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import services.AccountService

object AuthApi extends Controller with LoginLogout with ProprietaryAuthConfig {

  // POSTで渡ってくるデータの形式
  case class AuthData(username: String, password: String)

  // JSONをパースできるようにするための宣言
  implicit val authDataReads = Json.reads[AuthData]

  def auth = Action.async(BodyParsers.parse.json) { implicit request =>
    request.body.validate[AuthData].fold(
      errors => {
        Future.successful(BadRequest(Json.obj("message" -> JsError.toFlatJson(errors))))
      },
      data => {
        AccountService.authenticate(data.username, data.password) match {
          case None => Future.successful(Unauthorized(Json.obj("message" -> "authentication failed")))
          case Some(user) => gotoLoginSucceeded(user.id, Future.successful(
            Ok(Json.obj("username" -> user.username))
          ))
        }
      }
    )
  }
}

