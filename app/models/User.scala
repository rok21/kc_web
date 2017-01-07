package models

case class User(email: String,
                nick: String,
                password: String,
                city: Option[City])
