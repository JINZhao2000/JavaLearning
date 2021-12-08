package com.ayy.spark.core.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCountV3 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val ctx = new SparkContext(conf)
    try {
      val data: RDD[String] = ctx.textFile("dataset/wordcount")
      val words: RDD[String] = data.flatMap(_.split(" "))
      val countedWords = words.map {
        word => (word, 1)
      }
      val result: RDD[(String, Int)] = countedWords.reduceByKey(_ + _)
      result.foreach(println)
    } finally {
      ctx.stop()
    }
  }
}
