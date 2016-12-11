name := """kc_web"""

version := "1.0"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws
)

libraryDependencies ++= Seq(
  "com.typesafe.play" %% "play-slick" % "2.0.0"
)