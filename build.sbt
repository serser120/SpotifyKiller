name := """SpotifyKiller"""
organization := "com.example"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.10"
libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.sangria-graphql" %% "sangria" % "3.5.3"
libraryDependencies += "org.sangria-graphql" %% "sangria-play-json" % "2.0.2"
libraryDependencies += "org.sangria-graphql" %% "sangria-circe" % "1.3.2"
libraryDependencies += "com.typesafe.play" %% "play" % "2.8.19"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.4.1",
  "org.postgresql" % "postgresql"%"42.5.4",
  "com.typesafe.play" %% "play-slick" % "5.1.0",
  "com.typesafe.play" %% "play-slick-evolutions" % "5.1.0"
)
libraryDependencies += "com.github.tminglei" %% "slick-pg" % "0.21.1"