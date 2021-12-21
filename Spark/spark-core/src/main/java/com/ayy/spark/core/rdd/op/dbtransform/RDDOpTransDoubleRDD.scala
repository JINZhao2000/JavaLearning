package com.ayy.spark.core.rdd.op.dbtransform

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDOpTransDoubleRDD {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpDoubleRDD")
    val sc: SparkContext = new SparkContext(conf)

    val rdd1 = sc.makeRDD(List(1, 2, 3, 4))
    val rdd2 = sc.makeRDD(List(3, 4, 5, 6))

    val rddIntersection: RDD[Int] = rdd1.intersection(rdd2)
    println(rddIntersection.collect().mkString(","))

    val rddUnion: RDD[Int] = rdd1.union(rdd2)
    println(rddUnion.collect().mkString(","))

    val rddSub: RDD[Int] = rdd1.subtract(rdd2)
    println(rddSub.collect().mkString(","))

    val rddZip: RDD[(Int, Int)] = rdd1.zip(rdd2)
    println(rddZip.collect().mkString(","))

    sc.stop()
  }
}
