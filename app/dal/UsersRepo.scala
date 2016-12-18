package dal

import com.google.inject.Inject
import models.User
import services.SqlDb
import slick.driver.H2Driver.api._

class UsersRepo @Inject()(sqlDb: SqlDb){

  val table = TableQuery[Users]

  class Users(tag : Tag) extends Table[User](tag, "users"){

    def id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def nick: Rep[String] = column[String]("nick", O.PrimaryKey)
    def email: Rep[String] = column[String]("email", O.PrimaryKey)
    def password: Rep[String] = column[String]("password")

    override def *  = (email, nick, password) <> (User.tupled, User.unapply)
  }

  def findByNickOrEmail(noe: String) = sqlDb.run(
      table.filter(u => u.nick === noe || u.email === noe).result.head
    )

  def createUser(user: User) = sqlDb.run(table += user)
}
