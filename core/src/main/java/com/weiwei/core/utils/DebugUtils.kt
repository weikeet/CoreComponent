@file:JvmName("DebugsUtils")

package com.weiwei.core.utils

import android.util.Log
import androidx.annotation.IntRange
import com.weiwei.core.global.AppGlobal

/**
 * @author weicools
 * @date 2020.11.23
 */

fun getAllStackTrace(): String? {
  if (!AppGlobal.debuggable) {
    return null
  }
  return Log.getStackTraceString(Exception())
}

fun getStackTrace(self: Any): String? {
  return getStackTrace(self, 4)
}

fun getStackTrace(self: Any, @IntRange(from = 1) limit: Int): String? {
  if (!AppGlobal.debuggable) {
    return null
  }

  var length = 0
  val result = StringBuilder()
  for ((i, it) in Thread.currentThread().stackTrace.withIndex()) {
    if (i < 4 || it.methodName.contains("\$default")) {
      continue
    }

    if (length > 0) {
      result.append(" <- ")
    }
    if (self.javaClass.name != it.className) {
      result.append(it.className).append(".")
    }
    result.append(it.methodName.replace("\$app_debug", ""))

    length++
    if (length >= limit) {
      break
    }
  }
  return result.toString()
}