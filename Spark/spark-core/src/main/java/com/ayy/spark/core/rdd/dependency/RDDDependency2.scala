package com.ayy.spark.core.rdd.dependency

import org.apache.spark.{SparkConf, SparkContext}

object RDDDependency2 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Dependency2")
    val sc: SparkContext = new SparkContext(sparkConf)

    val lines = sc.textFile("dataset/wordcount")
    println(lines.dependencies); println("----")
    val words = lines.flatMap(_.split(" "))
    println(words.dependencies); println("----")
    val map = words.map(w=>(w, 1))
    println(map.dependencies); println("----")
    val res = map.reduceByKey(_ + _)
    println(res.dependencies); println("----")
    res.collect().foreach(println)

    sc.stop()
  }
}
