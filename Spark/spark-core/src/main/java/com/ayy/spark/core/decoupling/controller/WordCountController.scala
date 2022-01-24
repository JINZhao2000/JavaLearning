package com.ayy.spark.core.decoupling.controller

import com.ayy.spark.core.decoupling.common.{TController, TService}
import com.ayy.spark.core.decoupling.service.WordCountService
import org.apache.spark.SparkContext
import scala.collection.mutable

class WordCountController(path: String) extends TController {
  private var wordCountService: WordCountService = new WordCountService(path)

  override def execute(): Unit = {
    val result = wordCountService.analysis
    result.foreach(println)
  }
}
