
organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

lazy val `rx-rabbit` = (project in file("."))
  .aggregate(`rx-rabbit-api`, `rx-rabbit-impl`)

lazy val `rx-rabbit-api` = (project in file("rx-rabbit-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi)
  )

lazy val `rx-rabbit-impl` = (project in file("rx-rabbit-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslTestKit,
      "com.rabbitmq" % "amqp-client" % "5.0.0",
      "com.lightbend.akka" %% "akka-stream-alpakka-amqp" % "0.14"

    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`rx-rabbit-api`)

def common = Seq(
  javacOptions in compile += "-parameters"
)
