package models

case class User(email: String, nick: String, password: String)

object User {
  def validNick(nick: String) : Boolean = nick.length > 3 && nick.length < 13
  def validPassword(password: String) : Boolean = password.length > 6
}
