package com.ayy.spark.core.rdd.op.kvtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransGroupByKey {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpGroupByKey")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("b", 4)
    ))

    val groupRDD: RDD[(String, Iterable[Int])] = rdd.groupByKey()
    groupRDD.collect().foreach(println)
    val groupRDD2: RDD[(String, Iterable[(String, Int)])] = rdd.groupBy(_._1)
    groupRDD2.collect().foreach(println)

    sc.stop()
  }
}
