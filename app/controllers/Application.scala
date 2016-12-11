package controllers

import play.api._
import play.api.mvc._
import security._

class Application extends Controller with Secured {

  def index = Action(Ok(views.html.main()))

  def angular(any: Any) = index
}
