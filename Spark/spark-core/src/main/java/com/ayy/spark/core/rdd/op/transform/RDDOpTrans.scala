package com.ayy.spark.core.rdd.op.transform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTrans {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Op")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    // def mapFunc(num: Int): Int = {
    //   num * 2
    // }
    // val mapRDD: RDD[Int] = rdd.map(mapFunc)

    // val mapRDD: RDD[Int] = rdd.map((num:Int)=>{num * 2})
    val mapRDD: RDD[Int] = rdd.map(_*2)
    mapRDD.collect().foreach(println)

    sc.stop()
  }
}
