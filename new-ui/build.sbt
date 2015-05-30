import play.PlayImport.PlayKeys.playRunHooks

name := """new-ui"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "jp.t2v" %% "play2-auth" % "0.13.2",
  "jp.t2v" %% "play2-auth-test" % "0.13.2" % "test",
  "com.mohiva" %% "play-html-compressor" % "0.3.1"
)

// Asset packing
playRunHooks ++= Seq(
  RunSubProcess("make watch"),
  RunSubProcess("make old-server")
)

// Scalastyle settings
lazy val compileScalastyle = taskKey[Unit]("compileScalastyle")

compileScalastyle := org.scalastyle.sbt.ScalastylePlugin.scalastyle.in(Compile).toTask("").value

(compile in Compile) <<= (compile in Compile) dependsOn compileScalastyle
