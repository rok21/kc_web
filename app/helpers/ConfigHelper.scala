package helpers

import play.api.{Application, Configuration, Play}

/**
  * Created by rokas on 12/11/16.
  */
object ConfigHelper {

  private def getOption[A](f: Configuration => Option[A]) = Play.maybeApplication.flatMap(a => f(a.configuration))

  def getCookieMaxAge = getOption(_.getInt("play.http.session.maxAgeSecs")) getOrElse 3600

  def getSessionCookieName = getOption(_.getString("play.http.session.cookieName")) getOrElse "KC_SESSION"
}
