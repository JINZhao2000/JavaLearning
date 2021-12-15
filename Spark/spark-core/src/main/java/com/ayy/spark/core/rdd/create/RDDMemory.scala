package com.ayy.spark.core.rdd.create

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDMemory {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("RDD1")
    val sc = new SparkContext(conf)

    val seq = Seq[Int](1, 2, 3, 4)
    // val rdd: RDD[Int]= sc.parallelize(seq)

    val rdd: RDD[Int] = sc.makeRDD(seq)
    rdd.collect().foreach(println)

    sc.stop()
  }
}
