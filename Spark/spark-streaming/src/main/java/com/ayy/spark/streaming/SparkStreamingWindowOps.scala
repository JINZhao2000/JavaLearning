package com.ayy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.DStream
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingWindowOps {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming Window Operations")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val stream = ssc.socketTextStream("localhost", 9999)
    val mappedStream = stream.map((_, 1))

    val windowDS = mappedStream.window(Seconds(6), Seconds(6))
    windowDS.reduceByKey(_+_).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
