package com.ayy.spark.core.acc

import org.apache.spark.util.AccumulatorV2
import org.apache.spark.{SparkConf, SparkContext}

import scala.collection.mutable
import scala.jdk.Accumulator

object ACC3WordCount {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("ACC Word Count")
    val sc = new SparkContext(sparkConf)
    val rdd = sc.makeRDD(List("Hello", "Scala", "Hello", "Spark"))

    val wcAcc = new PersonalACC
    sc.register(wcAcc, "wcAcc")

    rdd.foreach(
      word=>{
        wcAcc.add(word)
      }
    )

    println(wcAcc.value)

    sc.stop()
  }

  class PersonalACC extends AccumulatorV2[String, mutable.Map[String, Long]] {
    private var wcMap = mutable.Map[String, Long]()

    override def isZero: Boolean = {
      wcMap.isEmpty
    }

    override def copy(): AccumulatorV2[String, mutable.Map[String, Long]] = {
      new PersonalACC()
    }

    override def reset(): Unit = {
      wcMap.clear()
    }

    override def add(v: String): Unit = {
      val newCnt = wcMap.getOrElse(v, 0L) + 1
      wcMap.update(v, newCnt)
    }

    override def merge(other: AccumulatorV2[String, mutable.Map[String, Long]]): Unit = {
      val map1 = this.wcMap
      val map2 = other.value
      map2.foreach{
        case (word, count) => {
          val newCnt = map1.getOrElse(word, 0L) + count
          map1.update(word, newCnt)
        }
      }
    }

    override def value: mutable.Map[String, Long] = {
      wcMap
    }
  }
}
