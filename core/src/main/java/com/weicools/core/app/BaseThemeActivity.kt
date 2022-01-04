/*
 * Copyright (c) 2022 Weiwei. https://github.com/lecymeng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weicools.core.app

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.weicools.core.app.theme.*

/**
 * @author weiwei
 * @date 2022.01.04
 */
abstract class BaseThemeActivity : BaseActivity() {

  private val themeDelegate: ThemeDelegate by lazy(LazyThreadSafetyMode.NONE) {
    ThemeDelegate()
  }

  private fun createThemeView() {
    ThemeManager.instance.init(this, getStartTheme())
    themeDelegate.createThemeView(this)
    super.setContentView(themeDelegate.root)
  }

  override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
    super.onCreate(savedInstanceState, persistentState)
    createThemeView()
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    createThemeView()
  }

  override fun onResume() {
    super.onResume()
    ThemeManager.instance.setActivity(this)
    ThemeManager.instance.getCurrentTheme()?.let { syncTheme(it) }
  }

  override fun setContentView(@LayoutRes layoutResID: Int) {
    themeDelegate.setContentView(layoutResID, this)
  }

  override fun setContentView(view: View?) {
    themeDelegate.setContentView(view)
  }

  override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
    themeDelegate.setContentView(view, params)
  }

  fun changeTheme(newTheme: AppTheme, sourceCoordinate: Coordinate, animDuration: Long, isReverse: Boolean) {
    themeDelegate.changeTheme(newTheme, sourceCoordinate, animDuration, isReverse) {
      syncTheme(newTheme)
    }
  }

  fun isRunningChangeThemeAnimation(): Boolean = themeDelegate.isRunningChangeThemeAnimation()

  fun setThemeAnimationListener(listener: ThemeAnimationListener) {
    themeDelegate.setThemeAnimationListener(listener)
  }

  fun getThemeManager(): ThemeManager = ThemeManager.instance

  /**
   * to sync ui with selected theme
   */
  abstract fun syncTheme(appTheme: AppTheme)

  /**
   * to save the theme for the next time, save it in onDestroy() (exp: in pref or DB) and return it here.
   * it just used for the first time (first activity).
   */
  abstract fun getStartTheme(): AppTheme

}