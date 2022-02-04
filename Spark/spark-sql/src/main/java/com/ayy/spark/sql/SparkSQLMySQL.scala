package com.ayy.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQLMySQL {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark SQL MySQL")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    spark.read.format("jdbc")
      .option("url", "jdbc:mysql://:3306/")
      .option("driver", "com.mysql.cj.jdbc.Driver")
      .option("user", "")
      .option("password", "")
      .option("dbtable", "")
      .load().show()

    spark.close()
  }
}
