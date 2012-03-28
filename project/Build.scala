import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

    val appName         = "AddressBook"
    val appVersion      = "1.0-SNAPSHOT"

    val appDependencies = Seq(
      "org.mybatis.scala" % "mybatis-scala-core" % "1.0-beta1"
    )

    val main = PlayProject(appName, appVersion, appDependencies, mainLang = SCALA).settings(
      // Add your own project settings here      
    )

}
