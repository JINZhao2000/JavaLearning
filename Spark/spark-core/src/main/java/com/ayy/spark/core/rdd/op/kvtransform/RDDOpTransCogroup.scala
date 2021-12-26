package com.ayy.spark.core.rdd.op.kvtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransCogroup {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpCogroup")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2)
    ))

    val rdd2 = sc.makeRDD(List(
      ("a", 4), ("b", 5), ("c", 6)
    ))

    val coRDD: RDD[(String, (Iterable[Int], Iterable[Int]))] = rdd1.cogroup(rdd2)
    coRDD.collect().foreach(println)

    sc.stop()
  }
}
