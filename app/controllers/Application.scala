package controllers

import com.google.inject.Inject
import dal.CitiesRepo
import helpers.Json4sSupport
import play.api.mvc._
import play.api.libs.concurrent.Execution.Implicits._

class Application @Inject()(citiesRepo: CitiesRepo) extends Controller with Json4sSupport{

  def index = Action(Ok(views.html.main()))

  def angular(any: Any) = index

  def getAllCities = Action.async{
    citiesRepo.allCities map {
      case cities => Ok(toJson(cities))
    }
  }
}
