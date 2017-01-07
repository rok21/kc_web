package services

import com.google.inject.Inject
import dal.UsersRepo
import models.User
import slick.dbio.Effect.Read
import slick.dbio.{DBIOAction, NoStream}
import slick.profile.SqlAction
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

/**
  * Created by rokas on 12/18/16.
  */
class UserService @Inject()(repo: UsersRepo) {

  def findFullUser(nickOrEmail: String) = repo.findFullUser(nickOrEmail)

  def login(nickOrEmail: String, password: String): Future[User] =
    findFullUser(nickOrEmail) flatMap {
      case Some(u) if u.password == password => Future.successful(u)
      case _ => Future.failed(new Exception("Login failed"))
    }

  def register(user: User) = {
    repo.createUser(user)
  }
}
