package controllers

import models.User
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.Messages
import play.api.mvc._

import scala.concurrent.Future

object Registration extends Controller{
  def index = ???

  def save = Action.async {
    Future.successful(Ok("kk"))
  }
}
