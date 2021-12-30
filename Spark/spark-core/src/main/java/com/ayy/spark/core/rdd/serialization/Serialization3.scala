package com.ayy.spark.core.rdd.serialization

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object Serialization3 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().
      setMaster("local[*]").
      setAppName("Serialization3").
      // set("spark.serializer", "com.twitter.chill.KryoSerializer").
      set("spark.serializer", "org.apache.spark.serializer.KryoSerializer").
      registerKryoClasses(Array(classOf[Search]))
    val sc: SparkContext = new SparkContext(sparkConf)

    val rdd: RDD[String] = sc.makeRDD(Array("hello world", "hello spark", "scala"))

    val search = new Search("h")
    val res: RDD[String] = search.getMatch1(rdd)
    res.collect().foreach(println)

    sc.stop()
  }

  case class Search(query: String) {
    def isMatch(s: String): Boolean = {
      s.contains(query)
    }

    def getMatch1(rdd: RDD[String]): RDD[String] = {
      rdd.filter(isMatch)
    }

    def getMatch2(rdd: RDD[String]): RDD[String] = {
      val s = query
      rdd.filter(x=>x.contains(s))
    }
  }
}
