package com.ayy.spark.core.rdd.create

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDFile {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("RDD2")
    val sc = new SparkContext(conf)

    // val rdd: RDD[String] = sc.textFile("./dataset/wordcount/1.txt")
    val rdd: RDD[String] = sc.textFile("./dataset/wordcount")
    // sc.wholeTextFiles("dataset")
    rdd.collect().foreach(println)

    sc.stop()
  }
}
