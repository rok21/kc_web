package helpers

import models.City
import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write
import org.json4s.native.JsonMethods._
import play.api.mvc.{AnyContent, Request}

trait Json4sSupport {

  implicit val formats = Serialization.formats(NoTypeHints)
  def toJson(obj: AnyRef) = write(obj)

  def parseCity(request: Request[AnyContent]) : City = parse(request.body.asJson.get.toString).extract[City]
}
