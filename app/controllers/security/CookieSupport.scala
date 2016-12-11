package controllers.security

import helpers.ConfigHelper
import play.api._
import play.api.libs.Crypto
import play.api.mvc.{Cookie, DiscardingCookie, RequestHeader, Result}

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
  private lazy val CookieMaxAge = ConfigHelper.getCookieMaxAge
  private lazy val SessionKey = ConfigHelper.getSessionCookieName

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

    def destroySession = {
      response.discardingCookies(DiscardingCookie(SessionKey))
    }
  }
}
