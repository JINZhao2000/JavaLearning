package com.ayy.spark.sql

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object SparkSQLBasic {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[*]").setAppName("Spark SQL Basic")
    val spark = SparkSession.builder().config(sparkConf).getOrCreate()
    import spark.implicits._

    val dfJson = spark.read.option("multiline", "true").json("dataset/sql/data.json")
    dfJson.show()

    dfJson.createOrReplaceTempView("user")
    spark.sql("select * from user").show()
    spark.sql("select name, age from user").show()
    spark.sql("select avg(age) as avg_age from user").show()

    dfJson.select("name", "age").show()
    dfJson.select($"age"+1).show()
    dfJson.select(Symbol("age")+1).show()

    val seq = Seq(1, 2, 3, 4)
    val dsSeq = seq.toDS()
    dfJson.show()

    val rdd = spark.sparkContext.makeRDD(List((1, "A", 20), (2, "B", 22)))
    val df = rdd.toDF("id", "name", "age")
    val rddRow = df.rdd

    val ds = df.as[User]
    val dfFromDS = ds.toDF()

    val dsFromRDD = rdd.map {
      case (id, name, age) => {
        User(id, name, age)
      }
    }.toDS()
    val rddDS = dsFromRDD.rdd

    spark.close()
  }

  case class User(id: Int, name: String, age: Int)
}
