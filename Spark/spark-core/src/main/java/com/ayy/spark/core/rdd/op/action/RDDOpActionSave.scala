package com.ayy.spark.core.rdd.op.action

import org.apache.spark.{SparkConf, SparkContext}

object RDDOpActionSave {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpActionSave")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(
      ("a", 1), ("a", 2), ("a", 3), ("a", 4)
    ))

    rdd.saveAsTextFile("outputText")
    rdd.saveAsObjectFile("outputObject")
    rdd.saveAsSequenceFile("outputSequence")

    sc.stop()
  }
}
