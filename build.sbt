name := "spark-lp"

version := "2.0"

scalaVersion := "2.11.12"

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.4"

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.4"


licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")

libraryDependencies += "com.joptimizer" % "joptimizer" % "3.4.0"
libraryDependencies += "org.scalatest" %% "scalatest-funsuite" % "3.2.0" % Test


parallelExecution in Test := false

assemblyMergeStrategy in assembly := {
  case PathList("META-INF", xs @_*) => MergeStrategy.discard
  case x => MergeStrategy.last
}
