package services

import com.google.inject.Inject
import dal.CitiesRepo
import models.City

import scala.concurrent.Future

class CityService @Inject()(repo: CitiesRepo) {
  def getAllCities : Future[Seq[City]] = repo.allCities
}
