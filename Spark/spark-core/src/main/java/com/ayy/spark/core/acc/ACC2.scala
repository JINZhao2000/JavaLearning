package com.ayy.spark.core.acc

import org.apache.spark.{SparkConf, SparkContext}

object ACC2 {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("ACC2")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    val sumACC = sc.longAccumulator("sum")
    val mapRDD = rdd.map(
      num => {
        sumACC.add(num)
        num
      }
    )

    mapRDD.collect()
    // mapRDD.collect()

    println(sumACC.value)

    sc.stop()
  }
}
