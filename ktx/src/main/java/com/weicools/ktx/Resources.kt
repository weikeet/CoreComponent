package com.weicools.ktx

import android.content.res.Resources
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import com.weicools.ktx.core.KtxInitializer
import kotlin.math.roundToInt

val Int.dp get() = (Resources.getSystem().displayMetrics.density * this).roundToInt()

val Float.dp get() = (Resources.getSystem().displayMetrics.density * this)

val widthPixels get() = (Resources.getSystem().displayMetrics.widthPixels)

val heightPixels get() = (Resources.getSystem().displayMetrics.heightPixels)

fun stringOf(@StringRes stringId: Int) = KtxInitializer.context.getString(stringId)

fun colorOf(@ColorRes colorId: Int) = ContextCompat.getColor(KtxInitializer.context, colorId)

fun colorsOf(@ColorRes colorId: Int) = ContextCompat.getColorStateList(KtxInitializer.context, colorId)

fun drawableOf(@DrawableRes drawableId: Int) = if (atLeastL()) ContextCompat.getDrawable(KtxInitializer.context, drawableId) else AppCompatResources.getDrawable(KtxInitializer.context, drawableId)
