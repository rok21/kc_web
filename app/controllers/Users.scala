package controllers
import controllers.Login.Ok
import controllers.security.Secured
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.{JsError, JsSuccess, Json}
import play.api.mvc.{Action, BodyParsers, Controller}

import scala.concurrent.{Future, Promise}

object Users extends Controller with Secured{
  def getUserInfo = withAuthAsync { username => implicit request =>
    {
      val f = Future {
        Thread.sleep(5000)
        420
      }
      val df = f.map(data => Ok(Json.toJson(data)).withSession())
      df
    }
  }
}
