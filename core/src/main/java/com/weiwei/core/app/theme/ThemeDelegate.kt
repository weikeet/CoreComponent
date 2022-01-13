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

package com.weiwei.core.app.theme

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import com.weiwei.core.R
import kotlin.math.sqrt

/**
 * @author weiwei
 * @date 2022.01.04
 */
class ThemeDelegate {

  lateinit var root: View
  private lateinit var decorView: FrameLayout
  private lateinit var frontFakeThemeImageView: SimpleImageView
  private lateinit var behindFakeThemeImageView: SimpleImageView

  private var anim: Animator? = null
  private var themeAnimationListener: ThemeAnimationListener? = null

  fun setContentView(@LayoutRes layoutResID: Int, context: Context) {
    setContentView(LayoutInflater.from(context).inflate(layoutResID, decorView))
  }

  fun setContentView(view: View?) {
    decorView.removeAllViews()
    decorView.addView(view)
  }

  fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
    decorView.removeAllViews()
    decorView.addView(view, params)
  }

  fun createThemeView(activity: Activity) {
    // create root view
    root = FrameLayout(activity).apply {
      layoutParams = FrameLayout.LayoutParams(
        FrameLayout.LayoutParams.MATCH_PARENT,
        FrameLayout.LayoutParams.MATCH_PARENT
      )

      // create and add behindFakeThemeImageView
      addView(SimpleImageView(context).apply {
        layoutParams = FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.MATCH_PARENT,
          FrameLayout.LayoutParams.MATCH_PARENT
        )
        isVisible = false
        behindFakeThemeImageView = this
      })

      // create and add decorView
      addView(FrameLayout(context).apply {
        layoutParams = FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.MATCH_PARENT,
          FrameLayout.LayoutParams.MATCH_PARENT
        )
        decorView = this
        id = R.id.fake_theme_root_view
      })

      // create and add frontFakeThemeImageView
      addView(SimpleImageView(context).apply {
        layoutParams = FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.MATCH_PARENT,
          FrameLayout.LayoutParams.MATCH_PARENT
        )
        isVisible = false
        frontFakeThemeImageView = this
      })
    }
  }

  fun changeTheme(newTheme: AppTheme, sourceCoordinate: Coordinate, animDuration: Long, isReverse: Boolean, syncTheme: Runnable) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      // update theme and return: no animation
      syncTheme.run()
      return
    }

    if (frontFakeThemeImageView.visibility == View.VISIBLE
      || behindFakeThemeImageView.visibility == View.VISIBLE
      || isRunningChangeThemeAnimation()
    ) {
      return
    }

    // take screenshot
    val w = decorView.measuredWidth
    val h = decorView.measuredHeight
    val bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    decorView.draw(canvas)

    // update theme
    syncTheme.run()

    // create anim
    val finalRadius = sqrt((w * w + h * h).toDouble()).toFloat()
    if (isReverse) {
      frontFakeThemeImageView.bitmap = bitmap
      frontFakeThemeImageView.isVisible = true
      anim = ViewAnimationUtils.createCircularReveal(frontFakeThemeImageView, sourceCoordinate.x, sourceCoordinate.y, 0f, finalRadius)
    } else {
      behindFakeThemeImageView.bitmap = bitmap
      behindFakeThemeImageView.isVisible = true
      anim = ViewAnimationUtils.createCircularReveal(decorView, sourceCoordinate.x, sourceCoordinate.y, 0f, finalRadius)
    }

    // set duration
    anim?.duration = animDuration

    // set listener
    anim?.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator) {
        themeAnimationListener?.onAnimationStart(animation)
      }

      override fun onAnimationEnd(animation: Animator) {
        frontFakeThemeImageView.bitmap = null
        behindFakeThemeImageView.bitmap = null
        frontFakeThemeImageView.isVisible = false
        behindFakeThemeImageView.isVisible = false
        themeAnimationListener?.onAnimationEnd(animation)
      }

      override fun onAnimationCancel(animation: Animator) {
        themeAnimationListener?.onAnimationCancel(animation)
      }

      override fun onAnimationRepeat(animation: Animator) {
        themeAnimationListener?.onAnimationRepeat(animation)
      }
    })

    // start it
    anim?.start()
  }

  fun isRunningChangeThemeAnimation(): Boolean {
    return anim?.isRunning == true
  }

  fun setThemeAnimationListener(listener: ThemeAnimationListener) {
    this.themeAnimationListener = listener
  }

}