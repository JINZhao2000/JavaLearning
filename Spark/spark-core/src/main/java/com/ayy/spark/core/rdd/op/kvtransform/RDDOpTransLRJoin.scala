package com.ayy.spark.core.rdd.op.kvtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransLRJoin {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpLeftRightOuterJoin")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5)
    ))


    val lJoinRDD: RDD[(String, (Int, Option[Int]))] = rdd1.leftOuterJoin(rdd2)
    val rJoinRDD: RDD[(String, (Option[Int], Int))] = rdd1.rightOuterJoin(rdd2)

    lJoinRDD.collect().foreach(println)
    println()
    rJoinRDD.collect().foreach(println)

    sc.stop()
  }
}
