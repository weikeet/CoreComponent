package com.weiwei.core.app

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiwei.core.lifecycle.AppViewModelProviders


/**
 * @author weicools
 * @date 2021.06.28
 */
open class BaseActivity : AppCompatActivity() {

  private var appScopeProvider: ViewModelProvider? = null
  private var activityScopeProvider: ViewModelProvider? = null

  protected fun <T : ViewModel> getAppScopeViewModel(modelClass: Class<T>): T {
    val provider = appScopeProvider
    return if (provider == null) {
      val viewModelProvider = AppViewModelProviders.of(this)
      appScopeProvider = viewModelProvider
      viewModelProvider.get(modelClass)
    } else {
      provider.get(modelClass)
    }
  }

  protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
    val provider = activityScopeProvider
    return if (provider == null) {
      val viewModelProvider = ViewModelProvider(this)
      activityScopeProvider = viewModelProvider
      viewModelProvider.get(modelClass)
    } else {
      provider.get(modelClass)
    }
  }

}