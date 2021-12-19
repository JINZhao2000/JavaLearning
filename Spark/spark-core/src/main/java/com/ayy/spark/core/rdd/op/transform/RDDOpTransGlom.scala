package com.ayy.spark.core.rdd.op.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransGlom {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpGlom")
    val sc: SparkContext = new SparkContext(conf)

    val numPartition: Int = 2
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numPartition)

    val mapRDD: RDD[Array[Int]] = rdd.glom()

    mapRDD.collect().foreach(d=> println(d.mkString(",")))

    sc.stop()
  }
}
