@file:JvmName("WhatIfString")
@file:JvmMultifileClass

package com.weiwei.ktx.whatif

/**
 * An expression for invoking [whatIf] when the [String] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [String] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun String?.whatIfNotNullOrEmpty(
  whatIf: (String) -> Unit
): String? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [String] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [String] it not null or empty.
 * @param whatIfNot An executable lambda function if the [String] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun String?.whatIfNotNullOrEmpty(
  whatIf: (String) -> Unit,
  whatIfNot: () -> Unit
): String? = apply {

  if (!this.isNullOrEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}
