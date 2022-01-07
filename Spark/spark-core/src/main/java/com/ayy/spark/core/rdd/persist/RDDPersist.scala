package com.ayy.spark.core.rdd.persist

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object RDDPersist {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Persist")
    val sc: SparkContext = new SparkContext(sparkConf)

    val list = List("Hello Spark", "Hello World", "Hello Scala")
    val rdd: RDD[String] = sc.makeRDD(list)
    val flatRDD = rdd.flatMap(_.split(" "))
    val mapRDD = flatRDD.map(w=>{
      println("#")
      (w, 1)
    })

    mapRDD.cache()
    mapRDD.persist()

    mapRDD.reduceByKey(_ + _).collect().foreach(println)
    mapRDD.groupByKey().collect().foreach(println)

    sc.stop()
  }
}
