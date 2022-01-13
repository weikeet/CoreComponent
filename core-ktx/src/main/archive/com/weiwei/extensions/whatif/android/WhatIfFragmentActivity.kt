package com.weiwei.ktx.whatif.android

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.weiwei.ktx.whatif.whatIfNotNullAs

/**
 * @author weiwei
 *
 * An expression for invoking [whatIf] when the [FragmentActivity] has an attached fragment [T].
 *
 * @param id A fragment currently on the back stack associated with this ID resource.
 * @param whatIf An executable lambda function if the [FragmentActivity] has a fragment [T].
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : Fragment> FragmentActivity.whatIfFindFragment(
  @IdRes id: Int,
  whatIf: (T) -> Unit
) {
  whatIfFindFragment(id, whatIf, {})
}

/**
 * @author weiwei
 *
 * An expression for invoking [whatIf] when the [FragmentActivity] has an attached fragment [T].
 * If [FragmentActivity] has not an attached fragment [T], [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param id A fragment currently on the back stack associated with this ID resource.
 * @param whatIf An executable lambda function if the [FragmentActivity] has a fragment [T].
 * @param whatIfNot An executable lambda function if the [FragmentActivity] has not an attached fragment [T].
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : Fragment> FragmentActivity.whatIfFindFragment(
  @IdRes id: Int,
  whatIf: (T) -> Unit,
  whatIfNot: () -> Unit
) {
  supportFragmentManager.findFragmentById(id).whatIfNotNullAs(whatIf, whatIfNot)
}

/**
 * @author weiwei
 *
 * An expression for invoking [whatIf] when the [FragmentActivity] has an attached fragment [T].
 *
 * @param id A fragment currently on the back stack associated with this tag name.
 * @param whatIf An executable lambda function if the [FragmentActivity] has a fragment [T].
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : Fragment> FragmentActivity.whatIfFindFragment(
  tag: String?,
  whatIf: (T) -> Unit
) {
  whatIfFindFragment(tag, whatIf, {})
}

/**
 * @author weiwei
 *
 * An expression for invoking [whatIf] when the [FragmentActivity] has an attached fragment [T].
 * If [FragmentActivity] has not an attached fragment [T], [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param tag A fragment currently on the back stack associated with this tag name.
 * @param whatIf An executable lambda function if the [FragmentActivity] has a fragment [T].
 * @param whatIfNot An executable lambda function if the [FragmentActivity] has not an attached fragment [T].
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : Fragment> FragmentActivity.whatIfFindFragment(
  tag: String?,
  whatIf: (T) -> Unit,
  whatIfNot: () -> Unit
) {
  supportFragmentManager.findFragmentByTag(tag).whatIfNotNullAs(whatIf, whatIfNot)
}
