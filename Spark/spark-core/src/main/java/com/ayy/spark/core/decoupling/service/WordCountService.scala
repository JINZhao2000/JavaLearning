package com.ayy.spark.core.decoupling.service

import com.ayy.spark.core.decoupling.common.TService
import com.ayy.spark.core.decoupling.dao.WordCountDAO
import org.apache.spark.SparkContext


class WordCountService(path: String) extends TService{
  private val wordCountDAO = new WordCountDAO()

  override def analysis = {
    val data = wordCountDAO.readFile(path)
    data
      .flatMap(_.split(" "))
      .groupBy(word=>word)
      .map {
        case (word, list) => {
          (word, list.size)
        }
      }
      .collect()
  }
}
