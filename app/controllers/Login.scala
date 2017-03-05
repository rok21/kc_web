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
    val (login, pass) = JsonHelper.getLoginCreds(request)
    userService.login(login, pass).map {
      case userNick => Ok.addSessionCookie(userNick)
    } recover {
      case ex => Ok(ex.getMessage)
    }
  }

  def logout = Action { Ok.destroySession }

  def currentUser = Action {
    request => getUserNick(request) match {
      case Some(user) => Ok(user)
      case None => Ok
    }
  }
}
