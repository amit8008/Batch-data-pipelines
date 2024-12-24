ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "batch_data_piplines",
    libraryDependencies ++= Seq(
      "org.apache.spark" %% "spark-core" % "3.5.1" % "provided",
      "org.apache.spark" %% "spark-sql" % "3.5.1" % "provided",
      "org.postgresql" % "postgresql" % "42.7.4" % "provided",
      "mysql" % "mysql-connector-java" % "8.0.33" % "provided",
      "com.typesafe" % "config" % "1.4.2"
    ),
    //    assembly / assemblyJarName := "spark-batch_data-pipeline-1.0.jar",

  )