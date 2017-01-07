package controllers

import com.google.inject.Inject
import controllers.security.Secured
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc.Controller
import services.{CityService, UserService}

import scala.concurrent.Future

class Users @Inject()(val userService: UserService, cityService: CityService) extends Controller with Secured{

  def getUserInfo = withUserAsync { user => implicit request =>
    {
      println(user)
      Future.successful(Ok("132"))
    }
  }
}