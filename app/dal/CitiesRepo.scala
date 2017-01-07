package dal

import com.google.inject.Inject
import models.City
import services.SqlDb
import slick.driver.H2Driver.api._
import slick.lifted.ProvenShape

class CitiesRepo @Inject()(sqlDb: SqlDb){

  val table = TableQuery[Cities]

  class Cities(tag: Tag) extends Table[City](tag, "cities"){
    def id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    def name: Rep[String] = column[String]("city")
    def country: Rep[String] = column[String]("country")

    override def * : ProvenShape[City] = (id, name, country) <> (City.tupled, City.unapply)
  }

  def byId(id: Int) = sqlDb.run(table.filter(_.id === id).result.head)
  def allCities = sqlDb.run(table.result)
}
