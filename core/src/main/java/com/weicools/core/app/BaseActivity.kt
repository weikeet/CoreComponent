package com.weicools.core.app

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner


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
      if (application == null) {
        throw IllegalStateException(
          "Your activity/fragment is not yet attached to Application. You can't request ViewModel before onCreate call."
        )
      }

      val viewModelStore: ViewModelStoreOwner = requireNotNull(application as? ViewModelStoreOwner) {
        "Your Application is not implements ViewModelStoreOwner."
      }
      val viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(application)
      val viewModelProvider = ViewModelProvider(viewModelStore, viewModelFactory)
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