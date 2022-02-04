name := "labAdd2"
version := "1.0.0"

scalaVersion := "2.12.15"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.6.18"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "uk.gov.hmrc" % "stub-data-generator_2.12" % "0.5.3"
  )
}

// parallelExecution in Test := false
