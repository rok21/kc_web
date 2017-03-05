package controllers

import com.google.inject.Inject
import controllers.security.CookieSupport
import helpers.JsonHelper
import play.api.mvc.{Action, Controller}
import services.UserService
import play.api.libs.concurrent.Execution.Implicits._

/**
  * Created by Rokas on 13/11/2016.
  */
class Registration @Inject()(userService: UserService) extends Controller with CookieSupport{

  def save = Action.async { request =>
    val user = JsonHelper.getRegistration(request)
    userService.register(user).map(_ => Ok.addSessionCookie(user.nick))
  }

  def isUnique = Action.async{ request =>

    println(request.body.asJson)
    val fields = JsonHelper.getFieldsForSsVal(request)

    userService.checkUniqueness(fields).map(_ => Ok).recover({
      case t: Throwable => Ok(t.getMessage)
    })
  }
}
