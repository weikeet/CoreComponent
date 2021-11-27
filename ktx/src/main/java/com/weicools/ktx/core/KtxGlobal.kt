package com.weicools.ktx.core

import android.content.Context

internal object KtxGlobal {
  var appContext: Context? = null

  val context: Context
    get() = if (appContext == null) throw RuntimeException("Please init application context: KtxInitializer.appContext = applicationContext") else appContext!!
}