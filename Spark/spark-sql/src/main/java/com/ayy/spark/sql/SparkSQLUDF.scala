package com.ayy.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQLUDF {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark SQL UDF")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    val dfJson = spark.read.option("multiline", "true").json("dataset/sql/data.json")

    dfJson.createOrReplaceTempView("user")

    spark.udf.register("prefixN", (name: String) => "Name: "+name)

    spark.sql("select prefixN(name), age from user").show()

    spark.close()
  }
}
