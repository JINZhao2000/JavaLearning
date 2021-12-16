package com.ayy.spark.core.rdd.create

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDMemoryPartition {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[*]").setAppName("RDD3")
    // conf.set("spark.default.parallelism", "4");
    val sc = new SparkContext(conf)

    val seq = Seq[Int](1, 2, 3, 4)
    // val rdd: RDD[Int]= sc.parallelize(seq)

    val rdd: RDD[Int] = sc.makeRDD(seq, 2)
    rdd.saveAsTextFile("output")

    sc.stop()
  }
}
