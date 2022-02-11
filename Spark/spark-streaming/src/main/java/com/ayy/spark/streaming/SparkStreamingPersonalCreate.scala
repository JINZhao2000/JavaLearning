package com.ayy.spark.streaming

import org.apache.spark.SparkConf
import org.apache.spark.api.java.StorageLevels
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.receiver.Receiver
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.util.UUID
import scala.collection.mutable

object SparkStreamingPersonalCreate {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Streaming Personal Receiver")
    val ssc = new StreamingContext(sparkConf, Seconds(3))

    val msgDS = ssc.receiverStream(new PersonalReceiver())
    msgDS.print()

    ssc.start()

    ssc.awaitTermination()
  }

  class PersonalReceiver extends Receiver[String](StorageLevels.MEMORY_ONLY) {
    private var flag = true

    override def onStart(): Unit = {
      new Thread(() => {
        while (flag) {
          val msg = UUID.randomUUID().toString
          store(msg)
          Thread.sleep(1000)
        }
      }).start()
    }

    override def onStop(): Unit = this.synchronized {
      flag = false
    }
  }
}
