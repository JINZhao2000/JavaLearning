package com.ayy.spark.core.rdd.op.action

import org.apache.spark.{SparkConf, SparkContext}

object RDDOpActionCountFirstTake {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpActionCountFirstTake")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(3, 5, 1, 2, 4))

    println(rdd.count())
    println(rdd.first())
    println(rdd.take(2).mkString(","))
    println(rdd.takeOrdered(3).mkString(","))

    sc.stop()
  }
}
