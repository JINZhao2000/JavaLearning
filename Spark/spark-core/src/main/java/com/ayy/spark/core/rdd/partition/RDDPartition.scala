package com.ayy.spark.core.rdd.partition

import org.apache.spark.rdd.RDD
import org.apache.spark.{Partitioner, SparkConf, SparkContext}

object RDDPartition {
  def main(args: Array[String]): Unit = {
    val sparkConf: SparkConf = new SparkConf().setMaster("local").setAppName("Partition")
    val sc: SparkContext = new SparkContext(sparkConf)

    val rdd = sc.makeRDD(List(
      ("math1", "probability"),
      ("math2", "numeric analysis"),
      ("math3", "graph and optimization"),
      ("cs1", "operating system"),
      ("cs2", "database"),
      ("cs3", "scala")
    ))

    val partitionedRDD: RDD[(String, String)] = rdd.partitionBy(new PersonalPartitioner)
    partitionedRDD.saveAsTextFile("res/partition")



    sc.stop()
  }

  class PersonalPartitioner extends Partitioner{
    override def numPartitions: Int = 2

    override def getPartition(key: Any): Int = {
      if (!key.isInstanceOf[String]) {
        throw new IllegalArgumentException("Illegal Key Type")
      }
      var prefix = String.valueOf(key)
      prefix = prefix.substring(0, prefix.length-1)
      prefix match {
        case "math" => 0
        case "cs" => 1
        case _ => throw new IllegalArgumentException("Illegal Key")
      }
    }
  }
}
