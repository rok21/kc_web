package controllers

import models.User
import play.api._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.Messages
import play.api.mvc._

object Registration extends Controller{
  def index = Action { implicit request =>
    Ok(views.html.registration(form))
  }

  def save = Action{ implicit request =>
    val newRegForm: Form[User] = form.bindFromRequest()
    newRegForm.fold(
      hasErrors = form =>
        Redirect(routes.Registration.index())
          .flashing(Flash(form.data + ("error" -> getErrorStr(form)))),
      success = _ => Ok("thanks!")
    )
  }
  private val form : Form[User] = Form(
    mapping(
      "email" -> email,
      "nick" -> text.verifying("error.nick", User.validNick(_)),
      "password" -> text.verifying("error.password", User.validPassword(_))
    )(User.apply)(User.unapply)
  )

  //case class Signup()

  private def getErrorStr(form: Form[User]) = {
    form.errors.foldLeft(Messages("validation.errors"))((str, error) => s"$str\n${Messages(error.message)}")
  }
}
