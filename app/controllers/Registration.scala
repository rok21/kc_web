package controllers

import play.api._
import play.api.mvc._

object Registration extends Controller{
  def index = Action {
    Ok(views.html.registration())
  }
}
