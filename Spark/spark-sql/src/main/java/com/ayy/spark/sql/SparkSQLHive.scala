package com.ayy.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQLHive {
  def main(args: Array[String]): Unit = {
    // should include mysql driver
    System.setProperty("HADOOP_USER_NAME", "XXX")
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark SQL Hive")
    val spark = SparkSession.builder().enableHiveSupport().config(sparkConf).getOrCreate()

    spark.sql("show tables").show()

    spark.close()
  }
}
