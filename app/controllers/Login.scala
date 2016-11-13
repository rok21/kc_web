package controllers

import play.api.mvc.{Action, Controller}

/**
  * Created by Rokas on 13/11/2016.
  */
object Login extends Controller {

  def index = {
    println("login index")
    Action(Ok(views.html.main()))
  }

//  val loginForm = Form(
//    mapping (
//      "email" -> text,
//      "password" -> text
//    ) verifying ("Invalid email or password", result => result match {
//      case (email, password) => check(email, password)
//    })
//  )
//
//  def check(username: String, password: String) = {
//    (username == "admin" && password == "1234")
//  }
//
//  def login = Action { implicit request =>
//    Ok(html.login(loginForm))
//  }
//
//  def authenticate = Action { implicit request =>
//    loginForm.bindFromRequest.fold(
//      formWithErrors => BadRequest(html.login(formWithErrors)),
//      user => Redirect(routes.Application.index).withSession(Security.username -> user._1)
//    )
//  }
//
//  def logout = Action {
//    Redirect(routes.Auth.login).withNewSession.flashing(
//      "success" -> "You are now logged out."
//    )
//  }
}
