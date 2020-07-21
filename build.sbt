name := "spark-lp"
version := "2.0"
scalaVersion := "2.11.12"
val sparkVersion = "2.4.4"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-mllib" % sparkVersion,

  "com.joptimizer" % "joptimizer" % "3.4.0",
  "org.scalatest" %% "scalatest-funsuite" % "3.2.0" % Test
)

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs@_*) => MergeStrategy.discard
  case x => MergeStrategy.last
}

assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
assemblyJarName in assembly := "spark-lp.jar"

test in assembly := {}

assemblyExcludedJars in assembly := {
  val cp = (fullClasspath in assembly).value
  cp filter {
    _.data.getPath().contains("org")
  }
}

fullClasspath in Runtime := (fullClasspath in (Compile, run)).value

//licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")
//parallelExecution in Test := false





