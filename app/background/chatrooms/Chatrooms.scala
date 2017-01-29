package background.chatrooms

import akka.pattern._
import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import background.chatrooms.CityChat.{NewMember, RemoveMember}
import com.google.inject.Inject
import dal.CitiesRepo
import models.User
import play.api.inject.ApplicationLifecycle

class Chatrooms @Inject() (applicationLifecycle: ApplicationLifecycle, actorSystem: ActorSystem, citiesRepo: CitiesRepo) {

  implicit val ec = actorSystem.dispatcher

  private val cityChats = citiesRepo.allCities map {
    case cities => cities map {
      case city => (city, actorSystem.actorOf(Props(new CityChat(city))))
    } toMap
  }

  applicationLifecycle.addStopHook {
    () => {
      cityChats.map(_.values.foreach(chat => chat ! PoisonPill))
    }
  }

  def join(user: User,  ref: ActorRef) = {
    cityChats map {
      case cities =>
        cities(user.city.get) ! NewMember(user, ref)
    }
  }

  def leave(user: User, ref: ActorRef) = cityChats map {
    case cities =>
      cities(user.city.get) ! RemoveMember(ref)
  }
}
