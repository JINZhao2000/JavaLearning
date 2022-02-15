package com.ayy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingTransform {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming Transform")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val stream = ssc.socketTextStream("localhost", 9999)

    // code 1 : Driver
    // One Time Execution
    stream.transform(rdd => {
      // code 2 : Driver
      // Scheduled Execution
      rdd.map(
        str => {
          // code 3 : RDD (Executor)
          str
        }
      )
    })

    // code 1 : Driver
    stream.map(
      data => {
        // code 2 : RDD (Executor)
        data
      }
    )

    ssc.start()
    ssc.awaitTermination()
  }
}
