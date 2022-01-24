package com.ayy.spark.core.decoupling.common

import com.ayy.spark.core.decoupling.controller.WordCountController
import com.ayy.spark.core.decoupling.util.EnvUtil
import org.apache.spark.{SparkConf, SparkContext}

trait TApplication {
  def start[K <: TController](master: String, appName: String)(operation: => Unit): Unit = {
    val sparkConf = new SparkConf().setMaster(master).setAppName(appName)
    val sc = new SparkContext(sparkConf)
    EnvUtil.put(sc)

    try {
      operation
    } catch {
      case ex => println(ex.getMessage)
    }

    EnvUtil.clean()
    sc.stop()
  }
}
