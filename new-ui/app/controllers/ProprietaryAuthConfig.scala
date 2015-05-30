package controllers

import jp.t2v.lab.play2.auth._
import play.api.mvc.{Result, RequestHeader}
import play.api.mvc.Results.{Redirect, Ok, Forbidden, Unauthorized}
import scala.concurrent.{Future, ExecutionContext}
import scala.reflect.{ClassTag,classTag}
import models.account.Role._
import services.AccountService

trait ProprietaryAuthConfig extends AuthConfig {

  /**
   * ユーザを識別するための型。
   * `String`, `Int`, `Long` など。
   */
  override type Id = String

  /**
   * ユーザを表現する型。modelなどに定義したclassの型を指定する。
   */
  override type User = models.account.User

  /**
   * 認可のために使う権限を表現した型。
   */
  override type Authority = models.account.Role

  /**
   * A `ClassTag` is used to retrieve an id from the Cache API.
   * Use something like this:
   */
  override val idTag: ClassTag[Id] = classTag[Id]

  /**
   * セッションがタイムアウトするまでの時間(秒)
   */
  override val sessionTimeoutInSeconds: Int = 3600 // 1時間

  /**
   * `Id`からユーザを探す方法。
   */
  override def resolveUser(id: Id)(implicit context: ExecutionContext): Future[Option[User]] = {
    Future.successful(AccountService.userOfId(id))
  }

  /**
   * ログインできたらどうするか？
   */
  override def loginSucceeded(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] = {
    Future.successful(Ok)
  }

  /**
   * ログアウトしたらどうするか？
   */
  override def logoutSucceeded(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] = {
    Future.successful(Redirect(routes.Application.index))
  }

  /**
   * 認証に失敗したらどうするか？
   */
  override def authenticationFailed(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] = {
    Future.successful(Unauthorized("Bad credentials"))
  }

  /**
   * 権限がないアクセスはどうするか？
   */
  override def authorizationFailed(request: RequestHeader, user: User, authority: Option[Authority])(implicit context: ExecutionContext): Future[Result] = {
    Future.successful(Forbidden("No permission"))
  }

  /**
   * ユーザが権限を持っているかを判定する関数。
   */
  override def authorize(user: User, authority: Authority)(implicit context: ExecutionContext): Future[Boolean] = Future.successful {
    (user.role, authority) match {
      case (Administrator, _)       => true // AdminならどんなActionでも全権限を開放
      case (NormalUser, NormalUser) => true // ユーザがNormalUserで、ActionがNormalUserなら権限あり。もしActionがAdminだけなら権限なしになる。
      case _                        => false
    }
  }

  override def authorizationFailed(request: RequestHeader)(implicit context: ExecutionContext): Future[Result] = throw new AssertionError("don't use")

  override lazy val idContainer: AsyncIdContainer[Id] = AsyncIdContainer(new ProprietaryIdContainer[Id])

  override lazy val tokenAccessor: TokenAccessor = new ProprietaryCookieTokenAccessor(
    cookieMaxAge = Some(sessionTimeoutInSeconds)
  )
}
