package com.ayy.spark.core.decoupling.common

import com.ayy.spark.core.decoupling.util.EnvUtil
import org.apache.spark.SparkContext

trait TDAO {
  def readFile(path: String) = {
    EnvUtil.get().textFile(path)
  }
}
