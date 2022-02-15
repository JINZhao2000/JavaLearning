package com.ayy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingJoin {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming Join")
    val ssc = new StreamingContext(sparkConf, Seconds(5))

    val src1 = ssc.socketTextStream("localhost", 9999)
    val src2 = ssc.socketTextStream("localhost", 10000)

    val map1 = src1.map((_, 1))
    val map2 = src2.map((_, 1))

    val jointDS: DStream[(String, (Int, Int))] = map1.join(map2)
    jointDS.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
