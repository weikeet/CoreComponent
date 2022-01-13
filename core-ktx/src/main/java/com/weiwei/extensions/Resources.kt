package com.weiwei.extensions

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.weiwei.extensions.core.LibGlobalScope
import kotlin.math.roundToInt

val Int.dp get() = (Resources.getSystem().displayMetrics.density * this).roundToInt()

val Float.dp get() = (Resources.getSystem().displayMetrics.density * this)

val widthPixels get() = (Resources.getSystem().displayMetrics.widthPixels)

val heightPixels get() = (Resources.getSystem().displayMetrics.heightPixels)

fun stringOf(@StringRes stringId: Int): String = LibGlobalScope.context.getString(stringId)

fun colorOf(@ColorRes colorId: Int): Int = ContextCompat.getColor(LibGlobalScope.context, colorId)

fun colorsOf(@ColorRes colorId: Int): ColorStateList? = ContextCompat.getColorStateList(LibGlobalScope.context, colorId)

fun drawableOf(@DrawableRes drawableId: Int): Drawable? =
  if (atLeastL()) {
    ContextCompat.getDrawable(LibGlobalScope.context, drawableId)
  } else {
    AppCompatResources.getDrawable(LibGlobalScope.context, drawableId)
  }
