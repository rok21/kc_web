package controllers.security

import play.api.Logger
import play.api.mvc._

import scala.concurrent.Future
import play.api.libs.concurrent.Execution.Implicits._

trait Secured extends CookieSupport {

  import CookieSupport._
  import Secured._

  def onUnauthorized(request: RequestHeader) = {
    logUnauthorized(request)
    Results.Unauthorized
  }

  def withAuth(f: => String => Request[AnyContent] => Result) = {
    Security.Authenticated(getUser, onUnauthorized) { user =>
      Action(request => {
        logIdentified(user, request)
        f(user)(request).updateSessionCookie(request)
      })
    }
  }

  def withAuthAsync(f: => String => Request[AnyContent] => Future[Result]) = {
    Security.Authenticated(getUser, onUnauthorized) { user =>
      Action.async(request => {
        logIdentified(user, request)
        f(user)(request).map(_.updateSessionCookie(request))
      })
    }
  }

      /**
      * This method shows how you could wrap the withAuth method to also fetch your user
      * You will need to implement UserDAO.findOneByUsername
      */
//    def withUser(f: User => Request[AnyContent] => Result) = withAuth { username => implicit request =>
//      val user = User("aaaa@bbbb.cc", username, "123")
//      f(user)(request)
//    }
  }

object Secured{
  private def logIdentified(user: String, request: RequestHeader) =
    Logger.info(s"Identified request by: $user, path: ${request.uri}")

  private def logUnauthorized(request: RequestHeader) =
    Logger.info(s"Unauthorized: ${request}, session: ${request.session}")
}
