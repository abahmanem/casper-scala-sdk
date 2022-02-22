name := "casper-scala-sdk"

version := "1.2.1"

scalaVersion := "3.0.2"

//scalaTest framework
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-funspec" % "3.2.10" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.10" % "test"

//fasterXml library
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.0"

//bouncycastle crypto library
libraryDependencies += "org.bouncycastle" % "bcprov-jdk15on" % "1.69"
libraryDependencies += "org.bouncycastle" % "bcpkix-jdk15on" % "1.69"
libraryDependencies += "org.bouncycastle" % "bcprov-ext-jdk15on" % "1.69"

//scala cats library
libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.6"

//OkHttp3 library
libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "4.9.2"

//apache codec library
libraryDependencies += "commons-codec" % "commons-codec" % "1.15"

libraryDependencies += "com.rfksystems" % "blake2b" % "1.0.0"



//publish
/*

ThisBuild / organization := "io.caspercommunity"
ThisBuild / organizationName := "caspercommunity"
ThisBuild / organizationHomepage := Some(url("https://caspercommunity.io/"))

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/caspercommunity/casper-scala-sdk"),
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