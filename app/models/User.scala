package models

case class User(email: String,
                nick: String,
                password: String,
                city: Option[City])

case class FieldsForSSVal(email: Option[String],  nick: Option[String])
