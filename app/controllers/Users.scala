package controllers

import com.google.inject.Inject
import controllers.security.Secured
import helpers.Json4sSupport
import play.api.mvc.Controller
import services.UserService

import scala.concurrent.Future

class Users @Inject()(val userService: UserService) extends Controller with Secured with Json4sSupport{

  def getUserData = withUserAsync { user =>implicit request =>
    {
      Future.successful(Ok(toJson(user)))
    }
  }
}