
name := """equipment-allocation"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.14"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test

libraryDependencies ++= Seq(
  "org.playframework" %% "play-slick"            % "6.1.0",    // Enables to work with database
  "org.playframework" %% "play-slick-evolutions" % "6.1.0",    // Support for database migrations, similar to Flyway
  "mysql" % "mysql-connector-java" % "8.0.26"
)

libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.2"

libraryDependencies += "org.apache.kafka" % "kafka-clients" % "3.4.0" // Add the correct version of Kafka client

libraryDependencies += filters

libraryDependencies += "com.auth0" % "java-jwt" % "4.3.0"