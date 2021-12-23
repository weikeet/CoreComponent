package com.weicools.core.app

import android.app.Application
import android.content.Context
import com.weicools.core.global.AppGlobal

/**
 * @author weicools
 * @date 2021.06.28
 */
open class BaseApplication : Application() {

  override fun attachBaseContext(base: Context) {
    super.attachBaseContext(base)
    AppGlobal.initAppContext(base)
  }

  override fun onCreate() {
    super.onCreate()

    AppGlobal.initApplication(this)
  }
}