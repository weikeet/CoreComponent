package com.weicools.core.app

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**
 * @author weicools
 * @date 2021.06.28
 */
open class BaseApplication : Application(), ViewModelStoreOwner {
  private lateinit var appViewModelStore: ViewModelStore

  override fun getViewModelStore(): ViewModelStore = appViewModelStore

  override fun onCreate() {
    super.onCreate()

    appViewModelStore = ViewModelStore()
  }
}