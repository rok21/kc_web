package dal

import com.google.inject.Inject
import models.{City, User}
import services.SqlDb
import slick.driver.H2Driver.api._
import slick.lifted.ForeignKeyQuery
import play.api.libs.concurrent.Execution.Implicits._

import scala.concurrent.Future

class UsersRepo @Inject()(sqlDb: SqlDb, citiesRepo: CitiesRepo){

  import UsersRepo._

  val table = TableQuery[Users]

  type UserRow = (String, String, String, Option[Int])
  class Users(tag : Tag) extends Table[UserRow](tag, "users"){

    def id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def nick: Rep[String] = column[String]("nick", O.PrimaryKey)
    def email: Rep[String] = column[String]("email", O.PrimaryKey)
    def password: Rep[String] = column[String]("password")
    def cityId: Rep[Int] = column[Int]("city_id")

    override def *  = (email, nick, password, cityId.?)
  }

  def findFullUser(nickOrEmail: String) = {

    val fullUserQuery = (nickOrEmail: String) => for{
      (u, c) <- table.filter(u => u.nick === nickOrEmail || u.email === nickOrEmail) joinLeft citiesRepo.table on (_.cityId === _.id)
    } yield (u, c)

    sqlDb.run(
      fullUserQuery(nickOrEmail).result.headOption
    ) map {
      case option => option.map {
        case ((email, nick, password, cityId), city) => User(email, nick, password, city)
      }
    }
  }

  def createUser(user: User) = sqlDb.run(table += user.toRow)

  def updateCity(nick: String, cityId: Int) = {

    val q = for{
      u <- table if u.nick === nick
    } yield u.cityId

    sqlDb.run(q.update(cityId))
  }
}

object UsersRepo{
  implicit class UserExtensions(user: User) {
    def toRow = (user.email, user.nick, user.password, user.city.map(_.id))
  }
}
