name := "spark-lp"

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.12"

sparkVersion := "2.4.4"

sparkComponents += "mllib"

licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0")

libraryDependencies ++= Seq(
  "com.joptimizer" % "joptimizer" % "3.4.0",
  "org.scalatest" %% "scalatest" % "2.1.5" % Test
)

// https://mvnrepository.com/artifact/org.apache.spark/spark-core
libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.4"

// https://mvnrepository.com/artifact/org.apache.spark/spark-mllib
libraryDependencies += "org.apache.spark" %% "spark-mllib" % "2.4.4" % "runtime"


parallelExecution in Test := false

// META-INF discarding
mergeStrategy in assembly <<= (mergeStrategy in assembly) { (old) =>
   {
    case PathList("META-INF", xs @ _*) => MergeStrategy.discard
    case x => MergeStrategy.last
   }
}
