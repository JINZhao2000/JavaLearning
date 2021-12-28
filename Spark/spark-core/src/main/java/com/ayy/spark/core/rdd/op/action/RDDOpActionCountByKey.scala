package com.ayy.spark.core.rdd.op.action

import org.apache.spark.{SparkConf, SparkContext}

object RDDOpActionCountByKey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpActionCountByKey")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(
      List(("a",1), ("b",2), ("b",3), ("d",4))
    )

    println(rdd.countByKey())

    sc.stop()
  }
}
