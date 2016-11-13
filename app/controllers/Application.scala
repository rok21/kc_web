package controllers

import play.api._
import play.api.mvc._

object Application extends Controller with Secured {

  def index = withAuth { username => implicit request =>
    Ok(views.html.main(username))
  }

  def angular(any: Any) = index
}
