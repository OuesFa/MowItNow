name := "MowItNow"

version := "1.0"

scalaVersion := "2.12.1"


libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.5.0",
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.4",
  "org.scala-lang.modules" %% "scala-swing" % "2.0.0-M2")