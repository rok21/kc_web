package helpers

import models.{FieldsForSSVal, User}
import play.api.libs.json.JsLookupResult
import play.api.mvc.{AnyContent, Request}

import scala.util.Try

object JsonHelper {

  def getLoginCreds(request: Request[AnyContent]) : (String, String) =
    request.body.asJson.get match{
      case json => {
        val uname = (json \ "username").as[String]
        val pass = (json \ "password").as[String]
        (uname, pass)
      }
    }

  def getRegistration(request: Request[AnyContent]) : User =
    request.body.asJson.get match{
      case json => {
        val email = (json \ "email").as[String]
        val uname = (json \ "username").as[String]
        val pass = (json \ "password").as[String]
        User(email, uname, pass, None)
      }
    }

  def getFieldsForSsVal(request: Request[AnyContent]) : FieldsForSSVal =
    request.body.asJson.map{
      case json => {
        val email = (json \ "email").asOptionString
        val nick = (json \ "username").asOptionString
        FieldsForSSVal(email, nick)
      }
    }.getOrElse(FieldsForSSVal(None, None))

  implicit class OptionWrapper(lookup: JsLookupResult){
    def asOptionString = Try(lookup.as[String]).toOption
  }
}
