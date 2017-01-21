package background.chatrooms

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, PoisonPill}
import background.chatrooms.CityChat.NewMember
import models.{City, User}
import play.api.Logger

/**
  * Created by rokas on 1/21/17.
  */
class CityChat(val city: City) extends Actor {
  private var users : Set[User] = Set.empty
  private var members: Set[ActorRef] = Set.empty
  override def receive: Receive = {
    case NewMember(user, ref) => {
      users += user
      members += ref
      Logger.info(s"new member joined the ${city.name} usr: $user")
      members.foreach(m => m ! users)
    }
  }
}

object CityChat {
  case class NewMember(user: User, ref: ActorRef)
  case class RemoveMember(ref: ActorRef)
}
