package com.ayy.spark.core.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val ctx = new SparkContext(conf)
    try {
      val data: RDD[String] = ctx.textFile("dataset/wordcount")
      val words: RDD[String] = data.flatMap(_.split(" "))
      val wordGroup: RDD[(String, Iterable[String])]= words.groupBy(word=>word)
      val count = wordGroup.map {
        case (word, list) => {
          (word, list.size)
        }
      }
      val result: Array[(String, Int)] = count.collect()
      result.foreach(println)
    } finally {
      ctx.stop()
    }
  }
}
