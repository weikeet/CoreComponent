@file:JvmName("WhatIfArray")
@file:JvmMultifileClass

package com.weiwei.ktx.whatif

/**
 * An expression for invoking [whatIf] when the [Array] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [Array] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T> Array<out T>?.whatIfNotNullOrEmpty(
  whatIf: (Array<out T>) -> Unit
): Array<out T>? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [Array] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [Array] it not null or empty.
 * @param whatIfNot An executable lambda function if the [Array] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T> Array<out T>?.whatIfNotNullOrEmpty(
  whatIf: (Array<out T>) -> Unit,
  whatIfNot: () -> Unit
): Array<out T>? = apply {

  if (!this.isNullOrEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [ByteArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [ByteArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun ByteArray?.whatIfNotNullOrEmpty(
  whatIf: (ByteArray) -> Unit
): ByteArray? = apply {

  return this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [ByteArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [ByteArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [ByteArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun ByteArray?.whatIfNotNullOrEmpty(
  whatIf: (ByteArray) -> Unit,
  whatIfNot: () -> Unit
): ByteArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [ShortArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [ShortArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun ShortArray?.whatIfNotNullOrEmpty(
  whatIf: (ShortArray) -> Unit
): ShortArray? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [ShortArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [ShortArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [ShortArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun ShortArray?.whatIfNotNullOrEmpty(
  whatIf: (ShortArray) -> Unit,
  whatIfNot: () -> Unit
): ShortArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [IntArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [IntArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun IntArray?.whatIfNotNullOrEmpty(
  whatIf: (IntArray) -> Unit
): IntArray? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [IntArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [IntArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [IntArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun IntArray?.whatIfNotNullOrEmpty(
  whatIf: (IntArray) -> Unit,
  whatIfNot: () -> Unit
): IntArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [LongArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [LongArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun LongArray?.whatIfNotNullOrEmpty(
  whatIf: (LongArray) -> Unit
): LongArray? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [LongArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [LongArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [LongArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun LongArray?.whatIfNotNullOrEmpty(
  whatIf: (LongArray) -> Unit,
  whatIfNot: () -> Unit
): LongArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [FloatArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [FloatArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun FloatArray?.whatIfNotNullOrEmpty(
  whatIf: (FloatArray) -> Unit
): FloatArray? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [FloatArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [FloatArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [FloatArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun FloatArray?.whatIfNotNullOrEmpty(
  whatIf: (FloatArray) -> Unit,
  whatIfNot: () -> Unit
): FloatArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [DoubleArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [DoubleArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun DoubleArray?.whatIfNotNullOrEmpty(
  whatIf: (DoubleArray) -> Unit
): DoubleArray? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [DoubleArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [DoubleArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [DoubleArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun DoubleArray?.whatIfNotNullOrEmpty(
  whatIf: (DoubleArray) -> Unit,
  whatIfNot: () -> Unit
): DoubleArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [BooleanArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [BooleanArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun BooleanArray?.whatIfNotNullOrEmpty(
  whatIf: (BooleanArray) -> Unit
): BooleanArray? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [BooleanArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [BooleanArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [BooleanArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun BooleanArray?.whatIfNotNullOrEmpty(
  whatIf: (BooleanArray) -> Unit,
  whatIfNot: () -> Unit
): BooleanArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [CharArray] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [CharArray] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun CharArray?.whatIfNotNullOrEmpty(
  whatIf: (CharArray) -> Unit
): CharArray? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [CharArray] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [CharArray] it not null or empty.
 * @param whatIfNot An executable lambda function if the [CharArray] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun CharArray?.whatIfNotNullOrEmpty(
  whatIf: (CharArray) -> Unit,
  whatIfNot: () -> Unit
): CharArray? = apply {

  if (this != null && this.isNotEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}
