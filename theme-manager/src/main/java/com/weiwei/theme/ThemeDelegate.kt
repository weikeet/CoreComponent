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

package com.weiwei.theme

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
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
import kotlin.math.sqrt

/**
 * @author weiwei
 * @date 2022.01.04
 */
class ThemeDelegate(private val syncTheme: (AppTheme) -> Unit) {
  lateinit var root: View
    private set

  private lateinit var decorView: FrameLayout
  private lateinit var frontFakeThemeImageView: SimpleImageView
  private lateinit var behindFakeThemeImageView: SimpleImageView

  private var anim: Animator? = null
  private var themeAnimationListener: AnimatorListenerAdapter? = null

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
        visibility = View.GONE
        behindFakeThemeImageView = this
      })

      // create and add decorView
      addView(FrameLayout(context).apply {
        layoutParams = FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.MATCH_PARENT,
          FrameLayout.LayoutParams.MATCH_PARENT
        )
        id = R.id.theme_decor_view
        decorView = this
      })

      // create and add frontFakeThemeImageView
      addView(SimpleImageView(context).apply {
        layoutParams = FrameLayout.LayoutParams(
          FrameLayout.LayoutParams.MATCH_PARENT,
          FrameLayout.LayoutParams.MATCH_PARENT
        )
        visibility = View.GONE
        frontFakeThemeImageView = this
      })
    }
  }

  fun setContentView(context: Context, @LayoutRes layoutResID: Int) {
    setContentView(LayoutInflater.from(context).inflate(layoutResID, decorView, false))
  }

  fun setContentView(view: View?) {
    decorView.removeAllViews()
    decorView.addView(view)
  }

  fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
    decorView.removeAllViews()
    decorView.addView(view, params)
  }

  fun resumeSyncTheme() {
    ThemeManager.instance.setActivity(this)
    ThemeManager.instance.getCurrentTheme()?.let { syncTheme(it) }
  }

  fun changeTheme(newTheme: AppTheme, sourceCoordinate: Coordinate, animDuration: Long, isReverse: Boolean) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
      // update theme and return: no animation
      syncTheme(newTheme)
      return
    }

    if (frontFakeThemeImageView.visibility == View.VISIBLE ||
      behindFakeThemeImageView.visibility == View.VISIBLE ||
      isRunningChangeThemeAnimation()
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
    syncTheme(newTheme)

    //create anim
    val finalRadius = sqrt((w * w + h * h).toDouble()).toFloat()
    val animator: Animator = if (isReverse) {
      frontFakeThemeImageView.bitmap = bitmap
      frontFakeThemeImageView.visibility = View.VISIBLE

      ViewAnimationUtils.createCircularReveal(
        frontFakeThemeImageView,
        sourceCoordinate.x,
        sourceCoordinate.y,
        finalRadius,
        0f
      )
    } else {
      behindFakeThemeImageView.bitmap = bitmap
      behindFakeThemeImageView.visibility = View.VISIBLE

      ViewAnimationUtils.createCircularReveal(
        decorView,
        sourceCoordinate.x,
        sourceCoordinate.y,
        0f,
        finalRadius
      )
    }

    // set duration
    animator.duration = animDuration
    // set listener
    animator.addListener(object : Animator.AnimatorListener {
      override fun onAnimationStart(animation: Animator) {
        themeAnimationListener?.onAnimationStart(animation)
      }

      override fun onAnimationEnd(animation: Animator) {
        behindFakeThemeImageView.bitmap = null
        frontFakeThemeImageView.bitmap = null
        frontFakeThemeImageView.visibility = View.GONE
        behindFakeThemeImageView.visibility = View.GONE

        themeAnimationListener?.onAnimationEnd(animation)
      }

      override fun onAnimationCancel(animation: Animator) {
        themeAnimationListener?.onAnimationCancel(animation)
      }

      override fun onAnimationRepeat(animation: Animator) {
        themeAnimationListener?.onAnimationRepeat(animation)
      }
    })

    animator.start()
    anim = animator
  }

  fun isRunningChangeThemeAnimation(): Boolean = anim?.isRunning == true

  fun setThemeAnimationListener(listener: AnimatorListenerAdapter) {
    this.themeAnimationListener = listener
  }

}