package com.ayy.spark.core.rdd.op.kvtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransAggregateByKey3 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpAggregateByKey3")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("b", 3), ("b", 4), ("b", 5), ("a", 6)
    ), 2)

    val aRDD: RDD[(String, (Int, Int))] = rdd.combineByKey(
      v => (v, 1),
      (t: (Int, Int), v) => {
        (t._1 + v, t._2 + 1)
      },
      (t1: (Int, Int), t2) => {
        (t1._1 + t2._1, t1._2 + t2._2)
      }
    )

    val resRDD: RDD[(String, Int)] = aRDD.mapValues {
      case (num, cnt) => {
        num / cnt
      }
    }

    resRDD.collect().foreach(println)

    sc.stop()
  }
}
