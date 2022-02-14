package com.ayy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object SparkStreamingState {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming State")
    val ssc = new StreamingContext(sparkConf, Seconds(3))
    ssc.checkpoint("tmp")

    val source = ssc.socketTextStream("localhost", 9999)
    val mapped = source.map((_, 1))
    mapped.updateStateByKey(
      (seq: Seq[Int], buf: Option[Int]) => {
        val newCnt = buf.getOrElse(0) + seq.sum
        Option(newCnt)
      }
    ).print()

    ssc.start()
    ssc.awaitTermination()
  }
}
