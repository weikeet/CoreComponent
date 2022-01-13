package com.weiwei.core.app

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weiwei.core.lifecycle.AppViewModelProviders
import com.weiwei.core.utils.getStackTrace

/**
 * @author weicools
 * @date 2021.06.28
 * todo handle setMaxLifecycle
 */
open class BaseFragment : Fragment() {
  lateinit var attachActivity: AppCompatActivity

  //region provider ViewModel
  private var appScopeProvider: ViewModelProvider? = null
  private var activityScopeProvider: ViewModelProvider? = null
  private var fragmentScopeProvider: ViewModelProvider? = null

  /**
   * provider Application scope ViewModel
   */
  protected fun <T : ViewModel> getAppScopeViewModel(modelClass: Class<T>): T {
    val provider = appScopeProvider
    return if (provider == null) {
      val viewModelProvider = AppViewModelProviders.of(attachActivity)
      appScopeProvider = viewModelProvider
      viewModelProvider.get(modelClass)
    } else {
      provider.get(modelClass)
    }
  }

  /**
   * provider Activity scope ViewModel
   */
  protected fun <T : ViewModel> getActivityScopeViewModel(modelClass: Class<T>): T {
    val provider = activityScopeProvider
    return if (provider == null) {
      val viewModelProvider = ViewModelProvider(attachActivity)
      activityScopeProvider = viewModelProvider
      viewModelProvider.get(modelClass)
    } else {
      provider.get(modelClass)
    }
  }

  /**
   * provider Fragment scope ViewModel
   */
  protected fun <T : ViewModel> getFragmentScopeViewModel(modelClass: Class<T>): T {
    val provider = fragmentScopeProvider
    return if (provider == null) {
      val viewModelProvider = ViewModelProvider(this)
      fragmentScopeProvider = viewModelProvider
      viewModelProvider.get(modelClass)
    } else {
      provider.get(modelClass)
    }
  }
  //endregion

  override fun onAttach(context: Context) {
    super.onAttach(context)

    attachActivity = context as AppCompatActivity
  }

  override fun onResume() {
    super.onResume()
    isFragmentResumed = true

    if (!isCallUserVisibleHint) {
      isVisibleForUser = !isHidden
    }
    handleVisibleChanged()
  }

  override fun onPause() {
    super.onPause()
    isFragmentResumed = false

    handleVisibleChanged()
  }

  override fun onDestroyView() {
    super.onDestroyView()

    resetVisibleRecord()
  }

  @Suppress("DEPRECATION")
  override fun setUserVisibleHint(isVisibleToUser: Boolean) {
    super.setUserVisibleHint(isVisibleToUser)
    isVisibleForUser = isVisibleToUser
    isCallUserVisibleHint = true

    handleVisibleChanged()
  }

  override fun onHiddenChanged(hidden: Boolean) {
    super.onHiddenChanged(hidden)
    isVisibleForUser = !hidden

    handleVisibleChanged()
  }

  //region handle fragment visible
  /**
   * is call [Fragment.setUserVisibleHint]
   * handle (add+show+hide)/(replace) Fragment is not callback [Fragment.setUserVisibleHint]
   * and (add/replace) Fragment is not callback [Fragment.onHiddenChanged]
   */
  private var isCallUserVisibleHint = false

  /**
   * current Fragment isVisible For User
   * if(Fragment.setUserVisibleHint) [Fragment.getUserVisibleHint] else ![Fragment.isHidden]
   */
  private var isVisibleForUser = false

  /**
   * when use ViewPager+Fragment form, [Fragment.setUserVisibleHint] will be called before the Fragment lifecycle function
   * although [Fragment.getUserVisibleHint] may be true, it is not visible, so Flag is needed to assist in judging the visibility.
   */
  var isFragmentResumed = false

  /**
   * the user is truly visible
   */
  var isUserVisible = false

  var userVisibleCount = 0

  /**
   * suitable for tasks that require lazy loading
   * trigger timing: ([isFragmentResumed] && [Fragment.getUserVisibleHint]) or ([isFragmentResumed] && ![Fragment.isHidden])
   */
  protected open fun onUserFirstVisible() {}

  protected open fun onUserVisibleChanged(visible: Boolean) {}

  private fun handleVisibleChanged() {
    Log.d(
      javaClass.simpleName, "handleVisibleChanged"
          + ", isVisibleForUser=" + isVisibleForUser
          + ", isFragmentResumed=" + isFragmentResumed
          + ", isUserVisible=" + isUserVisible
          + ", userVisibleCount=" + userVisibleCount
          + ", stack=" + getStackTrace(this, 1)
    )

    if (isVisibleForUser && isFragmentResumed) {
      if (++userVisibleCount == 1) {
        onUserFirstVisible()
      }

      if (!isUserVisible) {
        isUserVisible = true
        onUserVisibleChanged(true)
      }
    } else {
      if (isUserVisible) {
        isUserVisible = false
        onUserVisibleChanged(false)
      }
    }
  }

  private fun resetVisibleRecord() {
    isCallUserVisibleHint = false
    isVisibleForUser = false
    isUserVisible = false

    userVisibleCount = 0
  }
  //endregion
}