package com.ayy.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

object ACC {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("ACC")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    val sumACC = sc.longAccumulator("sum")
    rdd.foreach(
      num => {
        sumACC.add(num)
      }
    )

    println(sumACC.value)

    sc.stop()
  }
}
