name := """new-ui"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "jp.t2v" %% "play2-auth"      % "0.13.2",
  "jp.t2v" %% "play2-auth-test" % "0.13.2" % "test"
)
