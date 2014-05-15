name := "slick-multidb"

version := "1.0"

scalaVersion := "2.10.3"

mainClass in Compile := Some("MultiDBExample")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "2.0.2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.170",
  "org.xerial" % "sqlite-jdbc" % "3.7.2"
)
