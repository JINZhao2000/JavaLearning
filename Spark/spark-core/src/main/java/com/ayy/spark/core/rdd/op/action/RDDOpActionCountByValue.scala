package com.ayy.spark.core.rdd.op.action

import org.apache.spark.{SparkConf, SparkContext}

object RDDOpActionCountByValue {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpActionCountByValue")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1, 2, 2, 4),2)

    println(rdd.countByValue())

    sc.stop()
  }
}
