package controllers

import play.api.libs.json.JsValue
import play.api.mvc._

import scala.util.Try

/**
  * Created by Rokas on 13/11/2016.
  */
object Login extends Controller {

  def checkCreds = Action { request =>
    credsFrom(request).flatMap{
      case (uname, pass) if credsValid(uname, pass) =>{
        Some(
          Ok.withSession(Security.username -> uname)
        )
      }
      case _ => None
    }.getOrElse({
      println("Ok(\"Invalid credentials\")")
      Ok("Invalid credentials")
    })
  }

  private def credsValid(uname: String, pass: String) = {
    uname == "rok" && pass == "123"
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
