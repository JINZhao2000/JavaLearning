package com.ayy.spark.core.rdd.op.action

import org.apache.spark.{SparkConf, SparkContext}

object RDDOpActionForeach {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpActionForeach")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // Driver : foreach of array
    rdd.collect().foreach(println)
    println("***")
    // Executor : foreach of node
    rdd.foreach(println)

    sc.stop()
  }
}
