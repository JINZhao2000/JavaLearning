package com.ayy.spark.core.decoupling.dao

import com.ayy.spark.core.decoupling.common.TDAO
import com.ayy.spark.core.decoupling.util.EnvUtil

class WordCountDAO() extends TDAO{
  override def readFile(path: String) = {
    EnvUtil.get().textFile(path)
  }
}
