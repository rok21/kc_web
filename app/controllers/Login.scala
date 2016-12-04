package controllers

import controllers.security.CookieSupport
import play.api.libs.json.JsValue
import play.api.mvc._

import scala.util.Try

/**
  * Created by Rokas on 13/11/2016.
  */
object Login extends Controller with CookieSupport{

  import CookieSupport._

  def login = Action { request =>
    credsFrom(request).flatMap{
      case (uname, pass) if credsValid(uname, pass) =>
        Some(
          Ok.addSessionCookie(uname)
        )
      case _ => None
    }.getOrElse(Ok("Invalid credentials"))
  }

  def logout = Action { Ok.destroySession }

  def currentUser = Action {
    request => getUser(request) match {
      case Some(user) => Ok(user)
      case None => Ok
    }
  }

  private def credsValid(uname: String, pass: String) = {
    (uname == "a" && pass == "a") || (uname == "b" && pass == "b")
  }

  private def credsFrom(request: Request[AnyContent]) : Option[(String, String)] =
    request.body.asJson.flatMap{
      case json => Try({
        val uname = (json \ "username").as[String]
        val pass = (json \ "password").as[String]
        (uname, pass)
      }).toOption
    }
}
