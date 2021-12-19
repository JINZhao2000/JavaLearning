package com.ayy.spark.core.rdd.op.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransPartitionWithIdx {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpPartitionWitHIdx")
    val sc: SparkContext = new SparkContext(conf)

    val numPartition: Int = 2
    val rdd = sc.makeRDD(List(1, 2, 3, 4), numPartition)

    val mapRDD: RDD[Int] = rdd.mapPartitionsWithIndex(
      (idx, iter) => {
        if (idx == 1){
          iter
        } else {
          Nil.iterator
        }
      }
    )

    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
