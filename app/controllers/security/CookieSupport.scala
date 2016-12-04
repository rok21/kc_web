package controllers.security

import play.api._
import play.api.libs.Crypto
import play.api.mvc.{Cookie, RequestHeader, Result}

trait CookieSupport {

  import CookieSupport._

  def getUser(request: RequestHeader): Option[String] = {
    request.cookies.get(SessionKey).map {
      case cookie =>
        val user = Crypto.decryptAES(cookie.value)
        user
    }
  }
}

object CookieSupport{
  private lazy val CookieMaxAge = Play.maybeApplication.flatMap(_.configuration.getInt("session.maxAgeSecs")) getOrElse 3600
  private lazy val SessionKey = Play.maybeApplication.flatMap(_.configuration.getString("session.cookieName")) getOrElse "KC_SESSION"

  implicit class ResponseWithSessionCookies(val response: Result){

    def addSessionCookie(user: String) = {
      //seems to be ignoring httpOnly flag for some reason
      val cookie = Cookie(name = SessionKey, value = Crypto.encryptAES(user), maxAge = Some(CookieMaxAge), httpOnly = true)
      response.withCookies(cookie)
    }

    def updateSessionCookie(request: RequestHeader) = {
        val oldCookie = request.cookies(SessionKey)
        val newCookie = oldCookie.copy(maxAge = Some(CookieMaxAge))
        response.withCookies(newCookie)
    }
  }
}
