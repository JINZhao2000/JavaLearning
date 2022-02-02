package com.ayy.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoder, Encoders, functions}
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! sql.expressions.Aggregator, not spark.Aggregator
import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Row, SparkSession}

object SparkSQLUDAF2 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark SQL UADF2")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()

    val dfJson = spark.read.option("multiline", "true").json("dataset/sql/data.json")

    dfJson.createOrReplaceTempView("user")

    spark.udf.register("ageAvg2", functions.udaf(new MyAvgUDAF2()))

    spark.sql("select ageAvg2(age) from user").show()
    
    spark.close()
  }

  case class Buf(var total: Long, var count: Long)

  class MyAvgUDAF2 extends Aggregator[Long, Buf, Long] {
    override def zero: Buf = Buf(0L, 0L)

    override def reduce(b: Buf, a: Long): Buf = {
      b.total += a
      b.count += 1
      b
    }

    override def merge(b1: Buf, b2: Buf): Buf = {
      b1.total += b2.total
      b1.count += b2.count
      b1
    }

    override def finish(reduction: Buf): Long = {
      reduction.total / reduction.count
    }

    override def bufferEncoder: Encoder[Buf] = Encoders.product

    override def outputEncoder: Encoder[Long] = Encoders.scalaLong
  }
}
