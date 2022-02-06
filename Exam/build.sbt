name := "Kolokwium"
version := "1.0.0"

scalaVersion := "3.1.0"
scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.6.17"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV
  ).map(_.cross(CrossVersion.for3Use2_13))
}


