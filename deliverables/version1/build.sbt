import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "group3",
      scalaVersion := "2.12.4",
      scalacOptions := Seq("-Xlint", "-deprecation", "-feature"),
      version := "1.0.0"
    )),
    name := "version1",
    libraryDependencies += scalaTest % Test
  )
