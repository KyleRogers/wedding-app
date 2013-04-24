import sbt._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "WeddingApp"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    anorm,
    "mysql" % "mysql-connector-java" % "5.1.24",

    "com.typesafe" %% "play-plugins-mailer" % "2.1.0",

    "org.slf4j" % "jul-to-slf4j" % "1.6.6",
    "ch.qos.logback" % "logback-classic" % "0.9.11",


    // TEST DEPENDENCIES
    "play" %% "play-test" % "2.1.1" % "test",
    "org.scalatest" %% "scalatest" % "1.9.1" % "test",

    "junit" % "junit-dep" % "4.11" % "test",
    "info.cukes" % "cucumber-scala" % "1.1.2" % "test",
    "info.cukes" % "cucumber-junit" % "1.1.2" % "test",
    "org.fluentlenium" % "fluentlenium-core" % "0.7.7" % "test",
    "org.seleniumhq.selenium" % "selenium-java" % "2.31.0" % "test",

    "com.icegreen" % "greenmail" % "1.3.1b" % "test"

  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
