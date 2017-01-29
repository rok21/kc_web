package controllers

import akka.actor.{Actor, ActorRef, ActorSystem, Props}
import akka.stream.Materializer
import akka.stream.scaladsl.{Flow, Source}
import background.chatrooms.Chatrooms
import com.google.inject.Inject
import controllers.security.Secured
import dal.CitiesRepo
import helpers.Json4sSupport
import models.User
import org.joda.time.DateTime
import play.api.libs.concurrent.Execution.Implicits._
import play.api.libs.streams.ActorFlow
import play.api.mvc._
import play.api.Logger
import services.UserService

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.util.Random

class Application @Inject()(implicit system: ActorSystem,
                            materializer: Materializer,
                            citiesRepo: CitiesRepo,
                            val userService: UserService,
                            chatrooms: Chatrooms) extends Controller with Secured with Json4sSupport{

  def index = Action{
    implicit request => Ok(views.html.main())
  }

  def angular(any: Any) = index

  def getAllCities = Action.async{
    citiesRepo.allCities map {
      case cities => Ok(toJson(cities))
    }
  }

  def homews = WebSocket.acceptOrResult[String, String] { request =>
    getUser(request).map {
      case None => Left(Forbidden)
      case Some(user) => Right(ActorFlow.actorRef(out => Props(new MyWebSocketActor(user, chatrooms, out))))
    }
  }
}

class MyWebSocketActor(user :User, chatrooms: Chatrooms, out: ActorRef) extends Actor {
  override def preStart() = {
    chatrooms.join(user, self)
    context.system.scheduler.schedule(5 seconds, 50 milliseconds, self, "push")
  }

  override def postStop() = {
    Logger.info(s"Socket connection with $out has been closed")
    chatrooms.leave(user, self)
  }

  override def receive: Receive = {
    case "push" => out ! s"time: ${new DateTime().toString}"
    case msg: String => {
      out ! msg
    }
    case x => Logger.info(s"Socket actor received $x")
  }
}
