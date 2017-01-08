package helpers

import org.json4s._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.write

trait Json4sSupport {

  implicit val formats = Serialization.formats(NoTypeHints)
  def toJson(obj: AnyRef) = write(obj)
}
