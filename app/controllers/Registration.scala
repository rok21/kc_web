package controllers

import com.google.inject.Inject
import controllers.security.CookieSupport
import helpers.JsonHelper
import play.api.mvc.{Action, Controller}
import services.UserService
import play.api.libs.concurrent.Execution.Implicits._
import scala.concurrent.Future

/**
  * Created by Rokas on 13/11/2016.
  */
class Registration @Inject()(userService: UserService) extends Controller with CookieSupport{

  def save = Action.async { request =>
    JsonHelper.getRegistration(request).map{
      case user =>
        userService.register(user)
          .map(_ => Ok.addSessionCookie(user.nick))
    }.getOrElse(Future.failed(new Exception("Invalid json parameters")))
  }
}
