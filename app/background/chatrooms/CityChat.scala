package background.chatrooms

import akka.actor.Actor.Receive
import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, PoisonPill}
import background.chatrooms.CityChat.{NewMember, RemoveMember}
import models.{City, User}
import play.api.Logger

/**
  * Created by rokas on 1/21/17.
  */
class CityChat(val city: City) extends Actor {
  private var members : Map[ActorRef, User] = Map.empty
  override def receive: Receive = {
    case NewMember(user, ref) => {
      members = members.updated(ref, user)
      Logger.info(s"new member joined the ${city.name} usr: $user")
      onMembersChanged
    }
    case RemoveMember(ref) => {
      members -= ref
      onMembersChanged
    }
  }

  private def onMembersChanged = members.keys.foreach(m => m ! listOfMembersMsg)

  private def listOfMembersMsg : String = {
    val nicks = members.values.toSet[User].map(_.nick)
    s"${city.name}: ${nicks.mkString(", ")}"
  }
}

object CityChat {
  case class NewMember(user: User, ref: ActorRef)
  case class RemoveMember(ref: ActorRef)
}
