package com.ayy.spark.core.wordcount

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable

object WordCountII {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("WordCountII")
    val sc: SparkContext = new SparkContext(sparkConf)
    val rdd: RDD[String] = sc.makeRDD(List("Hello Spark", "Hello Scala", "Hello World"))

    sc.stop()
  }

  // groupBy
  def wc1(rdd: RDD[String]): Unit = {
    val wordCount: RDD[(String, Int)] = rdd.flatMap(_.split(" ")).groupBy(w => w).mapValues(_.size)
  }

  // groupByKey
  def wc2(rdd: RDD[String]): Unit = {
    val wordCount: RDD[(String, Int)] = rdd.flatMap(_.split(" ")).map((_, 1)).groupByKey().mapValues(_.size)
  }

  // reduceByKey
  def wc3(rdd: RDD[String]): Unit = {
    val wordCount: RDD[(String, Int)] = rdd.flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _)
  }

  // aggregateByKey
  def wc4(rdd: RDD[String]): Unit = {
    val wordCount: RDD[(String, Int)] = rdd.flatMap(_.split(" ")).map((_, 1)).aggregateByKey(0)(_+_, _+_)
  }

  // foldByKey
  def wc5(rdd: RDD[String]): Unit = {
    val wordCount: RDD[(String, Int)] = rdd.flatMap(_.split(" ")).map((_, 1)).foldByKey(0)(_+_)
  }

  // combineByKey
  def wc6(rdd: RDD[String]): Unit = {
    val wordCount: RDD[(String, Int)] = rdd.flatMap(_.split(" ")).map((_, 1)).combineByKey(
      v=>v,
      (x:Int, y)=>x+y,
      (x:Int, y:Int)=>x+y
    )
  }

  // combineByKey
  def wc7(rdd: RDD[String]): Unit = {
    val wordCount: collection.Map[String, Long] = rdd.flatMap(_.split(" ")).map((_, 1)).countByKey()
  }

  // combineByValue
  def wc8(rdd: RDD[String]): Unit = {
    val wordCount: collection.Map[String, Long] = rdd.flatMap(_.split(" ")).countByValue()
  }

  // reduce or aggregate or fold
  def wc9(rdd: RDD[String]): Unit = {
    val wordCount: collection.Map[String, Long] = rdd.flatMap(_.split(" ")).
      map(w => mutable.Map[String, Long]((w, 1))).
      reduce((m1, m2) => {
        m2.foreach{
          case (w, c) => {
            val newCount = m1.getOrElse(w, 0L) + c
            m1.update(w, newCount)
          }
        }
        m1
      })
  }
}
