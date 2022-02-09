package com.ayy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingWordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming Word Count")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val lines = ssc.socketTextStream("localhost", 9999)
    val words = lines.flatMap(_.split(" "))
    val oneWord = words.map((_, 1))
    val cnt: DStream[(String, Int)] = oneWord.reduceByKey(_ + _)
    cnt.foreachRDD(r=>r.foreach(println))

    ssc.start()
    ssc.awaitTermination()
  }
}
