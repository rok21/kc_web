package controllers

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.Materializer
import com.google.inject.Inject
import dal.CitiesRepo
import helpers.Json4sSupport
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import play.api.Logger

import scala.concurrent.duration._
import scala.util.Random

class Application @Inject()(implicit system: ActorSystem,
                            materializer: Materializer,
                            citiesRepo: CitiesRepo) extends Controller with Json4sSupport{

  def index = Action{
    implicit request => Ok(views.html.main())
  }

  def angular(any: Any) = index

  def getAllCities = Action.async{
    Logger.info("Getting cities from db")
    citiesRepo.allCities map {
      case cities => {
        Logger.info("Returning cities via http")
        Ok(toJson(cities))
      }
    }
  }


  def homews = WebSocket.accept[String, String] { request =>
    ActorFlow.actorRef(out => Props(new MyWebSocketActor(out)))
  }
}

class MyWebSocketActor(out: ActorRef) extends Actor {
  override def preStart() = {
    context.system.scheduler.schedule(5 seconds, 2 seconds, self, "push")
  }

  override def postStop() = {
    Logger.info(s"Socket connection with $out has been closed")
  }
  override def receive: Receive = {
    case "push" => {
      val rand = new Random(System.nanoTime())
      out ! s"millis: ${System.currentTimeMillis()} rand: ${rand.nextBoolean()}"
    }
  }
}
