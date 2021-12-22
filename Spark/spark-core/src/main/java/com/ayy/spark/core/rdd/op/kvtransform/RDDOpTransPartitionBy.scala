package com.ayy.spark.core.rdd.op.kvtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

object RDDOpTransPartitionBy {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpPartitionBy")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4), 2)

    val mapRDD: RDD[(Int, Int)] = rdd.map((_,1))

    val rddPartitioned: RDD[(Int, Int)] = mapRDD.partitionBy(new HashPartitioner(2))

    rddPartitioned.collect().foreach(println)

    sc.stop()
  }
}
