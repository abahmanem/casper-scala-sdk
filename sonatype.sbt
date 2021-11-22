
organization := "io.caspercommunity"
sonatypeProfileName := "io.caspercommunity"

//sync with Maven central
publishMavenStyle := true

// Open-source license of your choice
licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))

// source code hosted: GitHub
import sbt.Developer
import xerial.sbt.Sonatype._

homepage := Some(url("https://caspercommunity.io"))
scmInfo := Some(
  ScmInfo(
    url("https://github.com/caspercommunity/casper-scala-sdk"),
    "scm:git@github.com:caspercommunity/casper-scala-sdk"
  )
)
developers := List(
  Developer(id="mabahma", name="abahmane", email="elmabahma@gmail.com", url=url("https://caspercommunity.io")),
    Developer(id="mikael", name="grouwet", email="elmabahma@gmail.com", url=url("https://caspercommunity.io"))
)


ThisBuild / pomIncludeRepository := { _ => false }
ThisBuild / publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
  else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true