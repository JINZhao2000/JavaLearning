package com.ayy.spark.core.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object WordCountV2 {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("WordCount")
    val ctx = new SparkContext(conf)
    try {
      val data: RDD[String] = ctx.textFile("dataset/wordcount")
      val words: RDD[String] = data.flatMap(_.split(" "))
      val countedWords = words.map {
        word => (word, 1)
      }
      val wordGroup: RDD[(String, Iterable[(String, Int)])]= countedWords.groupBy(t=>t._1)
      val result = wordGroup.map {
        case (word, count) => {
          val countPair: (String, Int) = count.reduce(
            (t1, t2) => {
              (t1._1, t1._2 + t2._2)
            }
          )
        }
      }
      result.foreach(println)
    } finally {
      ctx.stop()
    }
  }
}
