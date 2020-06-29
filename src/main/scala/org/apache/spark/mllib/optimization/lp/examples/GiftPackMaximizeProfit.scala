package org.apache.spark.mllib.optimization.lp.examples

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.linalg.{DenseVector, Vector, Vectors}
import org.apache.spark.mllib.optimization.lp.VectorSpace._
import org.apache.spark.mllib.optimization.lp.vs.dvector.DVectorSpace
import org.apache.spark.mllib.optimization.lp.vs.vector.DenseVectorSpace
import org.apache.spark.mllib.optimization.lp.LP


object GiftPackMaximizeProfit {
  def main(args: Array[String]) {
    """
      The XYZ company during the festival season combines two factors A and B to form a gift pack
      which must weigh 5 kg. At least 2 kg of A and not more than 4 kg of B should be used.
      The net profit contribution to the company is Rs. 5 per kg for A and Rs. 6 per kg for B.
      Formulate LP Model to find the optimal factor mix.

      The correct answer: A = 2, B = 3, cost = 28
      """

    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("TestLPSolver")
    val sc = new SparkContext(sparkConf)

//    val spark = SparkSession.builder().appName("TestLPSolver").master("local[2]").getOrCreate()
//    val sc = spark.sparkContext
//    import spark.implicits._


    val numPartitions = 2
    // to convert min problem to max, multiply cost of each product by - 1,
    // the final cost will be negative so have to multiply it by -1 as well
    val cArray = Array(-5.0, -6.0, 0, 0) // goal is to minimize => positive sign for coefficients
    val BArray = Array(
      Array(1.0, 1, 0),
      Array(1.0, 0, 1),
      Array(0.0, -1, 0), // surplus variable
      Array(0.0, 0, 1)  // slack variable
    )
    val bArray = Array(5.0, 2, 4)

//    val experiment = sc.parallelize(cArray, numPartitions).toDF("cost")

    val c: DVector = sc.parallelize(cArray, numPartitions).glom.map(new DenseVector(_))
    val rows: DMatrix = sc.parallelize(BArray, numPartitions).map(Vectors.dense(_))
    val b: DenseVector = new DenseVector(bArray)
    try {
      val (v, x): (Double, DVector) = LP.solve(c, rows, b, sc = sc)
      val xx = Vectors.dense(x.flatMap(_.toArray).collect())
      println(s"optimial vector is $xx")
      println("minimum cost: " + - v)
    }
    catch {
      case e: Exception => println(e.getCause)
    }

//    println(experiment)
    sc.stop()
  }
}
