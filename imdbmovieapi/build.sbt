import sbt.{Resolver, enablePlugins}
import org.scalastyle.sbt.ScalastylePlugin
name := "swagger-ui-akka-http"

version := "1.0"

scalaVersion := "2.12.6"
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  "Scalaz Bintray"    at "http://dl.bintray.com/scalaz/releases"
)

val CatsVersion = "1.6.0"
val CirceVersion = "0.11.1"
val CirceConfigVersion = "0.6.1"
val EnumeratumCirceVersion = "1.5.21"
val LogbackVersion = "1.2.3"
val ScalaCheckVersion = "1.14.0"
val ScalaTestVersion = "3.0.7"
val FlywayVersion = "5.2.4"
val akkaVersion = "2.5.12"
val slickV = "3.2.3"

libraryDependencies ++=
  Seq(
    "com.github.swagger-akka-http" % "swagger-akka-http_2.12" % "0.14.0",
    "com.typesafe.akka" %% "akka-http-testkit" % "10.0.11",
    "com.typesafe.akka" %% "akka-http" % "10.0.11",
    "org.typelevel" %% "cats-core" % CatsVersion,
    "org.scalatest" %% "scalatest" % ScalaTestVersion,
    "io.circe" %% "circe-generic" % CirceVersion,
    "io.circe" %% "circe-literal" % CirceVersion,
    "io.circe" %% "circe-generic-extras" % CirceVersion,
    "io.circe" %% "circe-parser" % CirceVersion,
    "io.circe" %% "circe-java8" % CirceVersion,
    "io.circe" %% "circe-config" % CirceConfigVersion,
    "ch.qos.logback" % "logback-classic" % "1.1.3",
    "com.typesafe.akka" %% "akka-stream" % akkaVersion,
    "org.scalacheck" %% "scalacheck" % ScalaCheckVersion % Test,
    "de.heikoseeberger" %% "akka-http-circe" % "1.25.2",
    "com.softwaremill.macwire" %% "macros"  % "2.3.2",
    "com.typesafe.play" %% "play-json" % "2.6.13"
  )
enablePlugins(ScalafmtPlugin)

lazy val updateScalaStyle = taskKey[Unit]("updateScalaStyle")
(scalastyle in Compile) := (scalastyle in Compile) dependsOn updateScalaStyle
