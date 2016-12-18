package services

import com.google.inject.{Inject, Singleton}
import play.api.db.slick.DatabaseConfigProvider
import slick.dbio.{DBIOAction, NoStream}

/**
  * Created by rokas on 12/11/16.
  */


@Singleton
class SqlDb @Inject()(dbConfigProvider: DatabaseConfigProvider){
  private lazy val dbConn = dbConfigProvider.get.db
  def run[R](a: DBIOAction[R, NoStream, Nothing]) = dbConn.run(a)
}