
organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

lazy val `weather` = (project in file("."))
  .aggregate(`hi-weather-api`, `hi-weather-impl`)

lazy val `hi-weather-api` = (project in file("hi-weather-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok)
  )

lazy val `hi-weather-impl` = (project in file("hi-weather-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslPersistenceCassandra,
      lagomJavadslKafkaBroker,
      lagomJavadslTestKit,
      javaWs,
      lombok
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`hi-weather-api`)

val lombok = "org.projectlombok" % "lombok" % "1.16.10"

def common = Seq(
  javacOptions in compile += "-parameters"
)



