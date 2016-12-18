package controllers

import com.google.inject.Inject
import controllers.security.CookieSupport
import helpers.JsonHelper
import play.api.libs.concurrent.Execution.Implicits._
import play.api.mvc._
import services.UserService

import scala.concurrent.Future

/**
  * Created by Rokas on 13/11/2016.
  */
class Login @Inject()(userService: UserService) extends Controller with CookieSupport{

  import CookieSupport._

  def login = Action.async { request =>
    JsonHelper.getLoginCreds(request).map {
      case (login, pass) => {
        userService.login(login, pass).map {
          case user => Ok.addSessionCookie(user.nick)
        }
      }
    }.getOrElse(Future.failed(new Exception("Invalid json parameters")))
  }

  def logout = Action { Ok.destroySession }

  def currentUser = Action {
    request => getUser(request) match {
      case Some(user) => Ok(user)
      case None => Ok
    }
  }
}
