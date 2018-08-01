import scalariform.formatter.preferences._

name := """evaluator"""
organization := "group3"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

scalacOptions := Seq("-Xlint:_", "-deprecation", "-feature", "-unchecked")

libraryDependencies +=
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test

/* Configure Scalariform to follow Scala style guide.  */
scalariformPreferences := scalariformPreferences.value
  .setPreference(DoubleIndentConstructorArguments, true)
  .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, true)
  .setPreference(DanglingCloseParenthesis, Preserve)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "group3.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "group3.binders._"
