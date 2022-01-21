package com.ayy.spark.core.bc

import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object BC {
  def main(args: Array[String]): Unit = {
    val sc: SparkContext = new SparkContext(new SparkConf().setMaster("local").setAppName("BC"))

    val rdd1 = sc.makeRDD(List(
      ("a", 1), ("b", 2), ("c", 3),
    ))

    val map = mutable.Map(
      ("a", 4), ("b", 5), ("c", 6),
    )

    val bc = sc.broadcast(map)

    rdd1.map{
      case (w, c) => {
        val l: Int = bc.value.getOrElse(w, 0)
        (w, (c, l))
      }
    }.collect().foreach(println)

    sc.stop()
  }
}
