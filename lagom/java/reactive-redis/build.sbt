
organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

lazy val `rx-redis` = (project in file("."))
  .aggregate(`rx-redis-api`, `rx-redis-impl`)

lazy val `rx-redis-api` = (project in file("rx-redis-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi)
  )

lazy val `rx-redis-impl` = (project in file("rx-redis-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslTestKit,
      "biz.paluch.redis" % "lettuce" %  "4.2.0.Final",
      "io.reactivex" % "rxjava-reactive-streams" % "1.2.1"
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`rx-redis-api`)

def common = Seq(
  javacOptions in compile += "-parameters"
)
