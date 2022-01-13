package com.weiwei.extensions

import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.weiwei.extensions.core.LibGlobalScope
import kotlin.math.roundToInt

val Int.dp get() = (Resources.getSystem().displayMetrics.density * this).roundToInt()

val Float.dp get() = (Resources.getSystem().displayMetrics.density * this)

val widthPixels get() = (Resources.getSystem().displayMetrics.widthPixels)

val heightPixels get() = (Resources.getSystem().displayMetrics.heightPixels)

fun stringOf(@StringRes stringId: Int): String =
  LibGlobalScope.context.getString(stringId)

fun stringsOf(@StringRes stringId: Int, vararg formatArgs: Any) =
  LibGlobalScope.context.getString(stringId, *formatArgs)

fun colorOf(@ColorRes colorId: Int): Int =
  ContextCompat.getColor(LibGlobalScope.context, colorId)

fun colorsOf(@ColorRes colorId: Int): ColorStateList? =
  ContextCompat.getColorStateList(LibGlobalScope.context, colorId)

fun drawableOf(@DrawableRes drawableId: Int): Drawable? =
  if (atLeastL()) {
    ContextCompat.getDrawable(LibGlobalScope.context, drawableId)
  } else {
    AppCompatResources.getDrawable(LibGlobalScope.context, drawableId)
  }

fun dimenOf(@DimenRes dimenId: Int): Float =
  LibGlobalScope.context.resources.getDimension(dimenId)

fun dimenSizeOf(@DimenRes dimenId: Int): Int =
  LibGlobalScope.context.resources.getDimensionPixelSize(dimenId)

fun dimenOffsetOf(@DimenRes dimenId: Int): Int =
  LibGlobalScope.context.resources.getDimensionPixelOffset(dimenId)

fun fontOf(@FontRes fontId: Int): Typeface? =
  ResourcesCompat.getFont(LibGlobalScope.context, fontId)
