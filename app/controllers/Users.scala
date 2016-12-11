package controllers
import controllers.security.Secured
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.json.Json
import play.api.mvc.Controller

import scala.concurrent.Future

class Users extends Controller with Secured{
  def getUserInfo = withAuthAsync { username => implicit request =>
    {

//      val db = Database.forConfig("db")
//      val idk = TableQuery[Tests]
//      val x: QueryBase[Seq[String]] = idk.map(_.name)
//      val y : DBIO[Seq[String]] = x.result
//      val zz: Future[Seq[String]] = db.run(y)

      val zz = Future.successful("shits")

      zz.map{
        case shits =>
          val data = s"420 ${shits.head}"
          Ok(Json.toJson(data))
      }
    }
  }
}


//class Tests(tag : Tag) extends Table[(Int, String)](tag, "test"){
//
//  def id: Rep[Int] = column[Int]("id")
//  def name: Rep[String] = column[String]("name")
//
//  override def * : ProvenShape[(Int, String)] = (id, name)
//}
//
//
