package com.ayy.spark.core.rdd.op.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransPartition {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpPartition")
    val sc: SparkContext = new SparkContext(conf)

    val numPartition: Int = 2
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numPartition)

    val mapRDD: RDD[Int] = rdd.mapPartitions(
      iter => {
        println("Called")
        iter.map(_*2)
      }
    )

    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
