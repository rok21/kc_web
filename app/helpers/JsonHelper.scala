package helpers

import models.User
import play.api.mvc.{AnyContent, Request}

import scala.util.Try

object JsonHelper {

  def getLoginCreds(request: Request[AnyContent]) : Option[(String, String)] =
    request.body.asJson.flatMap{
      case json => Try({
        val uname = (json \ "username").as[String]
        val pass = (json \ "password").as[String]
        (uname, pass)
      }).toOption
    }

  def getRegistration(request: Request[AnyContent]) : Option[User] =
    request.body.asJson.flatMap{
      case json => Try({
        val email = (json \ "email").as[String]
        val uname = (json \ "username").as[String]
        val pass = (json \ "password").as[String]
        User(email, uname, pass, None)
      }).toOption
    }
}
