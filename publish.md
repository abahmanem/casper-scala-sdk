/*
ThisBuild / organization := "io.caspercommunity"
ThisBuild / organizationName := "caspercommunity"
ThisBuild / organizationHomepage := Some(url("https://caspercommunity.io/"))

ThisBuild / scmInfo := Some(
ScmInfo(
url("https://github.com/abahmanem/casper-scala-sdk"),
"scm:git@github.com:caspercommunityio/casper-scala-sdk"
)
)
ThisBuild / developers := List(
Developer(
id    = "mabahma",
name  = "mabahma",
email = "elmabahma@gmail.com",
url   = url("https://caspercommunity.io")
)
)

ThisBuild / description := "casper scala sdk"
ThisBuild / licenses := Seq("MIT" -> url("http://opensource.org/licenses/MIT"))
ThisBuild / homepage := Some(url("https://caspercommunity.io"))

// Remove all additional repository other than Maven Central from POM
ThisBuild / pomIncludeRepository := { _ => false }

sonatypeCredentialHost := "s01.oss.sonatype.org"
sonatypeRepository := "https://s01.oss.sonatype.org/service/local"

ThisBuild / publishTo := {
val nexus = "https://s01.oss.sonatype.org/"
if (isSnapshot.value) Some("snapshots" at nexus + "content/repositories/snapshots")
else Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
ThisBuild / publishMavenStyle := true
*/