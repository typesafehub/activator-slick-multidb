name := "slick-multidb"

version := "1.0"

scalaVersion := "2.11.6"

mainClass in Compile := Some("SimpleExample")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.1.0-RC2",
  "org.slf4j" % "slf4j-nop" % "1.7.10",
  "com.h2database" % "h2" % "1.4.187",
  "org.xerial" % "sqlite-jdbc" % "3.8.7"
)

fork in run := true
