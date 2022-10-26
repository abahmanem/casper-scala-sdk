name := "casper-scala-sdk"

version := "1.2.3"

scalaVersion := "3.1.3"

//scalaTest framework
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-funspec" % "3.2.10" % "test"
libraryDependencies += "org.scalatest" %% "scalatest-flatspec" % "3.2.10" % "test"

//bouncycastle crypto library
libraryDependencies += "org.bouncycastle" % "bcprov-jdk15on" % "1.69"
libraryDependencies += "org.bouncycastle" % "bcpkix-jdk15on" % "1.69"
libraryDependencies += "org.bouncycastle" % "bcprov-ext-jdk15on" % "1.69"

//scala cats library
libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.9"

//OkHttp3 library
libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "4.9.2"

//apache codec library
libraryDependencies += "commons-codec" % "commons-codec" % "1.15"

libraryDependencies += "com.rfksystems" % "blake2b" % "1.0.0"

//circe json libs
val circeVersion = "0.14.3"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

scalacOptions ++= Seq("-Xmax-inlines", "10000")



}



