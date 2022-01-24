package com.ayy.spark.core.decoupling.util

import org.apache.spark.SparkContext

object EnvUtil {
  private val thLocal = new ThreadLocal[SparkContext]()

  def put (sc: SparkContext): Unit = thLocal.synchronized {
    if (thLocal.get() == null) {
      thLocal.set(sc)
    }
  }

  def get(): SparkContext = thLocal.synchronized {
    thLocal.get()
  }

  def clean(): Unit = thLocal.synchronized {
    thLocal.remove()
  }
}
