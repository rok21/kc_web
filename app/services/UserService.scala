package services

import com.google.inject.Inject
import dal.UsersRepo
import models.User
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

/**
  * Created by rokas on 12/18/16.
  */
class UserService @Inject()(repo: UsersRepo) {

  def findFullUser(nickOrEmail: String) = repo.findFullUser(nickOrEmail).map(_.map(_.copy(password = "X")))

  def login(nickOrEmail: String, password: String): Future[String] =
    repo.findFullUser(nickOrEmail) flatMap {
      case Some(u) if u.password == password => Future.successful(u.nick)
      case _ => Future.failed(new Exception("Login failed"))
    }

  def register(user: User) = repo.createUser(user)

  def updateCity(nick: String, cityId: Int) = repo.updateCity(nick, cityId)
}
