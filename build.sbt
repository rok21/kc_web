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
  "com.typesafe.play" %% "play-slick" % "2.0.0",
  "com.h2database" % "h2" % "1.3.175",
  "org.postgresql" % "postgresql" % "9.4-1206-jdbc41",
  "org.json4s" %% "json4s-native" % "3.5.0"
)

fork in run := true