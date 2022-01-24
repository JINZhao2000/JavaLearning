package com.ayy.spark.core.decoupling.common

import org.apache.spark.SparkContext

trait TController {
  def execute(): Unit
}
