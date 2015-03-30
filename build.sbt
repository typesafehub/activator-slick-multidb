name := "slick-multidb"

version := "1.0"

scalaVersion := "2.11.6"

mainClass in Compile := Some("SimpleExample")

libraryDependencies ++= List(
  "com.typesafe.slick" %% "slick" % "3.0.0-RC2",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.h2database" % "h2" % "1.3.170",
  "org.xerial" % "sqlite-jdbc" % "3.7.2"
)


fork in run := true