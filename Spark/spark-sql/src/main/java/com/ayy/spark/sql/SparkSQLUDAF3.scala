package com.ayy.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.{Encoder, Encoders, functions}
// !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! sql.expressions.Aggregator, not spark.Aggregator
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Aggregator

object SparkSQLUDAF3 {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark SQL UADF3")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._
    val dfJson = spark.read.option("multiline", "true").json("dataset/sql/data.json")

    val dsJson = dfJson.as[User]

    val udafColumn = new MyAvgUDAF3().toColumn

    dsJson.select(udafColumn).show()

    spark.close()
  }

  case class User(name: String, age: Long)

  case class Buf(var total: Long, var count: Long)

  class MyAvgUDAF3 extends Aggregator[User, Buf, Long] {
    override def zero: Buf = Buf(0L, 0L)

    override def reduce(b: Buf, a: User): Buf = {
      b.total += a.age
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
