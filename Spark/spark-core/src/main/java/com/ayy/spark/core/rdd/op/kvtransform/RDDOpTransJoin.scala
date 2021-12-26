package com.ayy.spark.core.rdd.op.kvtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransJoin {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpJoin")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))

    val rdd3 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("d", 6)
    ))

    val joinRDD: RDD[(String, (Int, Int))] = rdd1.join(rdd2)
    val joinRDD2: RDD[(String, (Int, Int))] = rdd1.join(rdd3)

    joinRDD.collect().foreach(println)
    println()
    joinRDD2.collect().foreach(println)

    sc.stop()
  }
}
