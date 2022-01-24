package com.ayy.spark.core.decoupling.application

import com.ayy.spark.core.decoupling.common.TApplication
import com.ayy.spark.core.decoupling.controller.WordCountController

object WordCountApplication extends App with TApplication{

  start(
    "local[*]",
    "WordCount") {
    val controller = new WordCountController("dataset/wordcount")
    controller.execute()
  }
}
