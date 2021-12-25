package com.ayy.spark.core.rdd.op.kvtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransAggregateByKey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpAggregateByKey")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    // rdd.aggregateByKey(init)(funcA, funcA) == rdd.foldByKey(init)(funcA)
    rdd.aggregateByKey(0)(
      (x, y) => math.max(x, y),
      (x, y) => x + y
    ).collect().foreach(println)

    // rdd.foldByKey(0)(_+_)

    sc.stop()
  }
}
