package com.weicools.core.global

import android.content.Context
import android.content.pm.ApplicationInfo
import com.weicools.core.app.BaseApplication

/**
 * @author Weicools
 *
 * @date 2021.07.11
 */
object AppGlobal {
  private var innerAppContext: Context? = null
  val appContext: Context get() = requireNotNull(innerAppContext) { "Please initAppContext on Application#attachBaseContext" }

  private var innerApplication: BaseApplication? = null
  val application: BaseApplication get() = requireNotNull(innerApplication) { "Please initApplication on Application#onCreate" }

  private var innerDebuggable: Boolean? = null
  val debuggable: Boolean
    get() = if (innerDebuggable == null) {
      val debug = (appContext.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
      innerDebuggable = debug
      debug
    } else {
      innerDebuggable!!
    }

  fun initAppContext(appContext: Context) {
    innerAppContext = appContext
  }

  fun initApplication(application: BaseApplication) {
    innerApplication = application
  }

  fun initDebuggable(debuggable: Boolean) {
    innerDebuggable = debuggable
  }

  @JvmStatic
  fun get() = this
}