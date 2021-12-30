package com.ayy.spark.core.rdd.serialization

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Serialization1 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Serialization1")
    val sc: SparkContext = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello spark", "scala"))

    val search = new Search("h")
    search.getMatch1(rdd).collect().foreach(println)

    sc.stop()
  }

  class Search(query: String) extends Serializable{
    def isMatch(s: String): Boolean = {
      s.contains(query)
    }

    def getMatch1(rdd: RDD[String]): RDD[String] = {
      rdd.filter(isMatch)
    }

    def getMatch2(rdd: RDD[String]): RDD[String] = {
      val s = query
      rdd.filter(x=>x.contains(query))
    }
  }
}
