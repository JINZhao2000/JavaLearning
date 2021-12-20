package com.ayy.spark.core.rdd.op.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransGroupBy {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpGroupBy")
    val sc: SparkContext = new SparkContext(conf)

    val numPartition: Int = 2
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numPartition)

    def groupFunc(num: Int): Int = {
      num % 2
    }

    val mapRDD: RDD[(Int, Iterable[Int])] = rdd.groupBy(groupFunc)

    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
