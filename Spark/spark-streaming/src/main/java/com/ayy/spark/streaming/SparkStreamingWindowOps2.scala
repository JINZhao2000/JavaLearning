package com.ayy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingWindowOps2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming Window Operations 2")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.checkpoint("ckp")

    val stream = ssc.socketTextStream("localhost", 9999)
    val mappedStream = stream.map((_, 1))

    val windowDS = mappedStream.reduceByKeyAndWindow(
      (x:Int, y:Int) => {x+y},
      (x:Int, y:Int) => {x-y},
      Seconds(9), Seconds(3))

    windowDS.print()

    ssc.start()
    ssc.awaitTermination()
  }
}
