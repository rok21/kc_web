package controllers

import controllers.security.Secured
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc.Controller

import scala.concurrent.Future

class Users extends Controller with Secured{
  def getUserInfo = withAuthAsync { username => implicit request =>
    {
      val zz = Future.successful("shits")

      zz.map{
        case shits =>
          val data = s"420 ${shits.head}"
          Ok(Json.toJson(data))
      }
    }
  }
}