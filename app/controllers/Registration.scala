package controllers

import play.api.mvc.{Action, Controller}

import scala.concurrent.Future

/**
  * Created by Rokas on 13/11/2016.
  */
object Registration extends Controller{

  def save = Action.async {
    Future.successful(Ok("???"))
  }
}
