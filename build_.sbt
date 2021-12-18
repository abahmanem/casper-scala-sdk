name := "casper-scala-sdk"

version := "1.0.0"

scalaVersion := "3.0.2"


//ScalaTest framework
libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.10"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.10" % "test"

//FasteXml library
libraryDependencies += "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.13.0"

//bouncycastle crypto library
libraryDependencies += "org.bouncycastle" % "bcprov-jdk15on" % "1.66"

//scala cats library
libraryDependencies += "org.typelevel" %% "cats-core" % "2.6.1"
libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.13.6"

//OkHttp3 library
libraryDependencies += "com.squareup.okhttp3" % "okhttp" % "4.9.2"

libraryDependencies += "org.scodec" %% "scodec-bits" % "1.1.29"


