package services

import com.google.inject.Inject
import dal.UsersRepo
import models.{FieldsForSSVal, User}
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

/**
  * Created by rokas on 12/18/16.
  */
class UserService @Inject()(repo: UsersRepo) {

  import UserService._

  def checkUniqueness(fields: FieldsForSSVal) : Future[Unit] = {

    def checkField[A](opt: Option[A], repoCheck: A => Future[Boolean]): Future[Boolean] =
      opt.map(repoCheck).getOrElse(Future.successful(true))

    val fUnq = for{
      nickUnq <- checkField(fields.nick, repo.uniqueNickOrEmail)
      emailUnq <- checkField(fields.email, repo.uniqueNickOrEmail)
    } yield (nickUnq, emailUnq)

    fUnq.map(uniquenessErrors).map(_ => println("checkUniqueness"))
  }

  def findFullUser(nickOrEmail: String) = repo.findFullUser(nickOrEmail).map(_.map(_.copy(password = "X")))

  def login(nickOrEmail: String, password: String): Future[String] =
    repo.findFullUser(nickOrEmail) map {
      case Some(u) if u.password == password => u.nick
      case _ => throw new Exception("Login failed")
    }

  def register(user: User) = {
    val fieldsForSSVal = FieldsForSSVal(Some(user.email), Some(user.nick))
    for{
      _ <- checkUniqueness(fieldsForSSVal)
      _ <- repo.createUser(user)
    } yield ()
  }

  def updateCity(nick: String, cityId: Int) = repo.updateCity(nick, cityId)
}

object UserService{
  def uniquenessErrors(nick_email: (Boolean, Boolean)): Unit = nick_email match {
    case (false, _) => throw new Exception("Username is already taken")
    case (_, false) => throw new Exception("Email is already taken")
    case _ => ()
  }
}
