package controllers

import jp.t2v.lab.play2.auth.{AuthenticityToken, IdContainer}
import services.AccountService
import scala.reflect.ClassTag

class ProprietaryIdContainer[Id: ClassTag] extends IdContainer[Id] {
  override def startNewSession(userId: Id, timeoutInSeconds: Int): AuthenticityToken =
    AccountService.Session.startSession(userId.toString, timeoutInSeconds)

  override def get(token: AuthenticityToken): Option[Id] =
    AccountService.Session.userIdOf(token).map(_.asInstanceOf[Id])

  override def remove(token: AuthenticityToken): Unit =
    AccountService.Session.removeToken(token)

  override def prolongTimeout(token: AuthenticityToken, timeoutInSeconds: Int): Unit =
    AccountService.Session.changeTokenExpires(token, timeoutInSeconds)
}
