package com.ayy.spark.core.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDPersistCheckPoint {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("PersistCheckPoint")
    val sc: SparkContext = new SparkContext(sparkConf)
    sc.setCheckpointDir("./checkpoint") // hdfs://

    val list = List("Hello Spark", "Hello World", "Hello Scala")
    val rdd: RDD[String] = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(w=>{
      println("#")
      (w, 1)
    })

    mapRDD.checkpoint()

    mapRDD.reduceByKey(_ + _).collect().foreach(println)
    mapRDD.groupByKey().collect().foreach(println)

    sc.stop()
  }
}
