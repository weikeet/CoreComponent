package com.weiwei.extensions.core

import android.content.Context

internal object LibGlobalScope {
  var appContext: Context? = null

  val context: Context
    get() = if (appContext == null) {
      throw RuntimeException("Please init LibGlobalScope.appContext")
    } else {
      appContext!!
    }
}