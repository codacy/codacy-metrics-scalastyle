import com.typesafe.sbt.packager.docker.{Cmd, DockerAlias}

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
organization := "com.codacy"
scalaVersion := "2.12.10"
name := "codacy-metrics-scalastyle"
// App Dependencies
libraryDependencies ++= Seq(
  "com.codacy" %% "codacy-metrics-scala-seed" % "0.2.2",
  "com.github.pathikrit" %% "better-files" % "3.8.0",
  "org.specs2" %% "specs2-core" % "4.8.0" % Test)

mappings in Universal ++= {
  (resourceDirectory in Compile).map { resourceDir: File =>
    val src = resourceDir / "docs"
    val dest = "/docs"

    for {
      path <- src.allPaths.get if !path.isDirectory
    } yield path -> path.toString.replaceFirst(src.toString, dest)
  }
}.value

Docker / packageName := packageName.value
dockerBaseImage := "amazoncorretto:8-alpine3.17-jre"
Docker / daemonUser := "docker"
Docker / daemonGroup := "docker"
dockerEntrypoint := Seq(s"/opt/docker/bin/${name.value}")
dockerCommands := dockerCommands.value.flatMap {
  case cmd @ Cmd("ADD", _) =>
    List(
      Cmd("RUN", "adduser -u 2004 -D docker"),
      cmd,
      Cmd("RUN", s"apk update && apk --no-cache add bash"),
      Cmd("RUN", "mv /opt/docker/docs /docs"))

  case other => List(other)
}
