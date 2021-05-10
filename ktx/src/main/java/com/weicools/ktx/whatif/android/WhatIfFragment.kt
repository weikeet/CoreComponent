@file:Suppress("unused")
@file:JvmName("WhatIfFragment")
@file:JvmMultifileClass

package com.weicools.ktx.whatif.android

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.weicools.ktx.whatif.whatIfNotNull
import com.weicools.ktx.whatif.whatIfNotNullAs

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Context] is not null.
 *
 * @param whatIf An executable lambda function if the [Fragment]'s context is not null.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Fragment?.whatIfNotNullContext(
  whatIf: (Context) -> Unit
) {

  this?.context.whatIfNotNull {
    whatIf(it)
  }
}

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Context] is not null.
 * If the activity is null, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [Fragment]'s context is not null.
 * @param whatIfNot An executable lambda function if the [Fragment]'s context is null.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Fragment?.whatIfNotNullContext(
  whatIf: (Context) -> Unit,
  whatIfNot: () -> Unit
) {

  this?.context.whatIfNotNull(
    whatIf = whatIf,
    whatIfNot = whatIfNot
  )
}

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Activity] is not null.
 *
 * @param whatIf An executable lambda function if the [Fragment]'s parent Activity is not null.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Fragment?.whatIfNotNullActivity(
  whatIf: (FragmentActivity) -> Unit
) {

  this?.activity.whatIfNotNull {
    whatIf(it)
  }
}

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Activity] is not null.
 * If the activity is null, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [Fragment]'s parent Activity is not null.
 * @param whatIfNot An executable lambda function if the [Fragment]'s parent Activity is null.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Fragment?.whatIfNotNullActivity(
  whatIf: (FragmentActivity) -> Unit,
  whatIfNot: () -> Unit
) {

  this?.activity.whatIfNotNull(
    whatIf = whatIf,
    whatIfNot = whatIfNot
  )
}

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Fragment.getArguments] is not null.
 *
 * @param whatIf An executable lambda function if the [Fragment] has arguments.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Fragment?.whatIfHasArguments(
  whatIf: (Bundle) -> Unit
) {

  whatIfHasArguments(whatIf) { }
}

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Fragment.getArguments] is not null.
 *
 * @param whatIf An executable lambda function if the [Fragment] has arguments.
 * @param whatIfNot An executable lambda function if the [Fragment] has not any arguments.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Fragment?.whatIfHasArguments(
  whatIf: (Bundle) -> Unit,
  whatIfNot: () -> Unit
) {

  this?.arguments.whatIfNotNull(whatIf, whatIfNot)
}

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Fragment] has an [T] interface as a parent.
 *
 * @param whatIf An executable lambda function if the [Fragment] has an [T] interface as a parent.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T> Fragment?.whatIfFindParentInterface(
  whatIf: (T) -> Unit
) {

  whatIfFindParentInterface(whatIf) { }
}

/**
 * @author weicools
 *
 * An expression for invoking [whatIf] when the [Fragment] has an [T] interface as a parent.
 *
 * @param whatIf An executable lambda function if the [Fragment] has an [T] interface as a parent.
 * @param whatIfNot An executable lambda function if the [Fragment] has not an [T] interface as a parent.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T> Fragment?.whatIfFindParentInterface(
  whatIf: (T) -> Unit,
  whatIfNot: () -> Unit
) {

  this?.activity.whatIfNotNullAs(whatIf, whatIfNot)
}
