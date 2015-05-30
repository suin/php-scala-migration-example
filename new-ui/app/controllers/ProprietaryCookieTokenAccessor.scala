package controllers

import jp.t2v.lab.play2.auth.TokenAccessor
import jp.t2v.lab.play2.auth._
import play.api.mvc.{DiscardingCookie, Cookie, Result, RequestHeader}

class ProprietaryCookieTokenAccessor(
                                      cookieName: String = "SESSION_TOKEN",
                                      cookieSecureOption: Boolean = false,
                                      cookieHttpOnlyOption: Boolean = true,
                                      cookieDomainOption: Option[String] = None,
                                      cookiePathOption: String = "/",
                                      cookieMaxAge: Option[Int] = None
                                      ) extends TokenAccessor {
  def put(token: AuthenticityToken)(result: Result)(implicit request: RequestHeader): Result = {
    val c = Cookie(cookieName, token, cookieMaxAge, cookiePathOption, cookieDomainOption, cookieSecureOption, cookieHttpOnlyOption)
    result.withCookies(c)
  }

  def extract(request: RequestHeader): Option[AuthenticityToken] = {
    request.cookies.get(cookieName).map(_.value.asInstanceOf[AuthenticityToken])
  }

  def delete(result: Result)(implicit request: RequestHeader): Result = {
    result.discardingCookies(DiscardingCookie(cookieName))
  }
}
