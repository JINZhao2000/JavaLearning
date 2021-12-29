package com.ayy.spark.core.rdd.op.action

import org.apache.spark.{SparkConf, SparkContext}

object RDDOpActionForeach2 {
  def main(args: Array[String]): Unit = {
    val conf: SparkConf = new SparkConf().setMaster("local[*]").setAppName("OpActionForeach2")
    val sc: SparkContext = new SparkContext(conf)

    val rdd = sc.makeRDD(List(1, 2, 3, 4))

    val user = new User()

    // closure check serializable
    rdd.foreach(
      num => {
        println(user.age + num)
      }
    )

    sc.stop()
  }

  // class User extends Serializable {
  case class User() {
    var age = 18
  }
}
