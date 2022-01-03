package com.ayy.spark.core.rdd.dependency

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDDependency {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Dependency1")
    val sc: SparkContext = new SparkContext(sparkConf)

    val lines = sc.textFile("dataset/wordcount")
    println(lines.toDebugString); println("----")
    val words = lines.flatMap(_.split(" "))
    println(words.toDebugString); println("----")
    val map = words.map(w=>(w, 1))
    println(map.toDebugString); println("----")
    val res = map.reduceByKey(_ + _)
    println(res.toDebugString); println("----")
    res.collect().foreach(println)

    sc.stop()
  }
}
