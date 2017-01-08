package controllers.security

import models.User
import play.api.Logger
import play.api.mvc._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._
import services.UserService

trait Secured extends CookieSupport {

  val userService: UserService

  import CookieSupport._
  import Secured._

  def onUnauthorized(request: RequestHeader) = {
    logUnauthorized(request)
    Results.Unauthorized
  }

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(getUserNick, onUnauthorized) { user =>
      Action(request => {
        logIdentified(user, request)
        f(user)(request).updateSessionCookie(request)
      })
    }
  }

  def withAuthAsync(f: => String => Request[AnyContent] => Future[Result]) = {
    Security.Authenticated(getUserNick, onUnauthorized) { user =>
      Action.async(request => {
        logIdentified(user, request)
        f(user)(request).map(_.updateSessionCookie(request))
      })
    }
  }

  def withUserAsync(f: User => Request[AnyContent] => Future[Result]) = withAuthAsync { username => implicit request =>
      userService.findFullUser(username) flatMap {
        case user => f(user.get)(request)
      }
    }
  }

object Secured{
  private def logIdentified(user: String, request: RequestHeader) =
    Logger.info(s"Identified request by: $user, path: ${request.uri}")

  private def logUnauthorized(request: RequestHeader) =
    Logger.info(s"Unauthorized: ${request}, session: ${request.session}")
}
