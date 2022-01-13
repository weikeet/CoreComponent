@file:JvmName("WhatIfCollections")
@file:JvmMultifileClass

package com.weicools.ktx.whatif

/**
 * An expression for invoking [whatIf] when the [List] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [List] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T> List<T>?.whatIfNotNullOrEmpty(
  whatIf: (List<T>) -> Unit
): List<T>? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [List] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf]
 *
 * @param whatIf An executable lambda function if the [List] it not null or empty.
 * @param whatIfNot An executable lambda function if the [List] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T> List<T>?.whatIfNotNullOrEmpty(
  whatIf: (List<T>) -> Unit,
  whatIfNot: () -> Unit
): List<T>? = apply {

  if (!this.isNullOrEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [Set] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [Set] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T> Set<T>?.whatIfNotNullOrEmpty(
  whatIf: (Set<T>) -> Unit
): Set<T>? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [Set] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [Set] it not null or empty.
 * @param whatIfNot An executable lambda function if the [Set] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T> Set<T>?.whatIfNotNullOrEmpty(
  whatIf: (Set<T>) -> Unit,
  whatIfNot: () -> Unit
): Set<T>? = apply {

  if (!this.isNullOrEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for invoking [whatIf] when the [Map] is not null and not empty.
 *
 * @param whatIf An executable lambda function if the [Map] it not null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T, R> Map<T, R>?.whatIfNotNullOrEmpty(
  whatIf: (Map<T, R>) -> Unit
): Map<T, R>? = apply {

  this.whatIfNotNullOrEmpty(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for invoking [whatIf] when the [Map] is not null and not empty.
 * If the array is null or empty, [whatIfNot] will be invoked instead of the [whatIf].
 *
 * @param whatIf An executable lambda function if the [Map] it not null or empty.
 * @param whatIfNot An executable lambda function if the [Map] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <T, R> Map<T, R>?.whatIfNotNullOrEmpty(
  whatIf: (Map<T, R>) -> Unit,
  whatIfNot: () -> Unit
): Map<T, R>? = apply {

  if (!this.isNullOrEmpty()) {
    whatIf(this)
  } else {
    whatIfNot()
  }
}

/**
 * An expression for adding an [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element An element should be added into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addWhatIfNotNull(
  element: E?,
  whatIf: (T) -> Unit
): T = apply {

  this.addWhatIfNotNull(
    element = element,
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for adding an [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element An element should be added into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 * @param whatIfNot An executable lambda function if the [element] it null or empty.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addWhatIfNotNull(
  element: E?,
  whatIf: (T) -> Unit,
  whatIfNot: (T) -> Unit
): T = apply {

  element?.whatIfNotNullAs<E>(
    whatIf = {
      add(it)
      whatIf(this)
    },
    whatIfNot = {
      whatIfNot(this)
    }
  )
}

/**
 * An expression for adding an [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element n collection of elements should be added into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addAllWhatIfNotNull(
  element: Collection<E>?,
  whatIf: (T) -> Unit
): T = apply {

  this.addAllWhatIfNotNull(
    element = element,
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for adding an [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element n collection of elements should be added into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 * @param whatIfNot An executable lambda function if the [element] it null.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.addAllWhatIfNotNull(
  element: Collection<E>?,
  whatIf: (T) -> Unit,
  whatIfNot: (T) -> Unit
): T = apply {

  element?.whatIfNotNull(
    whatIf = {
      addAll(it)
      whatIf(this)
    },
    whatIfNot = {
      whatIfNot(this)
    }
  )
}

/**
 * An expression for removing an [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element An element should be removed into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeWhatIfNotNull(
  element: E?,
  whatIf: (T) -> Unit
): T = apply {

  this.removeWhatIfNotNull(
    element = element,
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for removing an [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element An element should be removed into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 * @param whatIfNot An executable lambda function if the [element] it null.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeWhatIfNotNull(
  element: E?,
  whatIf: (T) -> Unit,
  whatIfNot: (T) -> Unit
): T = apply {

  element?.whatIfNotNullAs<E>(
    whatIf = {
      remove(it)
      whatIf(this)
    },
    whatIfNot = {
      whatIfNot(this)
    }
  )
}

/**
 * An expression for removing a collection of [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element A collection of elements should be removed into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeAllWhatIfNotNull(
  element: Collection<E>?,
  whatIf: (T) -> Unit
): T = apply {

  this.removeAllWhatIfNotNull(
    element = element,
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for removing an [element] and invoking [whatIf] when the [element] is not null.
 *
 * @param element A collection of elements should be removed into the target.
 * @param whatIf An executable lambda function if the [element] it not null.
 * @param whatIfNot An executable lambda function if the [element] it null.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun <reified T : MutableCollection<E>, reified E> T.removeAllWhatIfNotNull(
  element: Collection<E>?,
  whatIf: (T) -> Unit,
  whatIfNot: (T) -> Unit
): T = apply {

  element?.whatIfNotNull(
    whatIf = {
      removeAll(it)
      whatIf(this)
    },
    whatIfNot = {
      whatIfNot(this)
    }
  )
}

/**
 * An expression for operating `And` operator to a list of the nullable-Boolean.
 *
 * @param whatIf An executable lambda function if the result of the `And` operation is true.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Iterable<Boolean?>.whatIfAnd(
  whatIf: () -> Unit
): Iterable<Boolean?> {
  return this.whatIfAnd(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for operating `And` operator to a list of the nullable-Boolean.
 *
 * @param whatIf An executable lambda function if the result of the `And` operation is true.
 * @param whatIfNot An executable lambda function if the result of the `And` operation is false.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Iterable<Boolean?>.whatIfAnd(
  whatIf: () -> Unit,
  whatIfNot: (() -> Unit)
): Iterable<Boolean?> {
  var predicate: Boolean? = null

  this.forEach {
    val p = predicate
    predicate = if (p == null) {
      it
    } else {
      p and (it == true)
    }
  }

  if (predicate == true) {
    whatIf()
  } else {
    whatIfNot()
  }
  return this
}

/**
 * An expression for operating `Or` operator to a list of the nullable-Boolean.
 *
 * @param whatIf An executable lambda function if the result of the `Or` operation is true.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Iterable<Boolean?>.whatIfOr(
  whatIf: () -> Unit
): Iterable<Boolean?> {
  return this.whatIfOr(
    whatIf = whatIf,
    whatIfNot = { }
  )
}

/**
 * An expression for operating `Or` operator to a list of the nullable-Boolean.
 *
 * @param whatIf An executable lambda function if the result of the `Or` operation is true.
 * @param whatIfNot An executable lambda function if the result of the `Or` operation is false.
 *
 * @return Returns the original target object.
 */
@JvmSynthetic
@WhatIfInlineOnly
inline fun Iterable<Boolean?>.whatIfOr(
  whatIf: () -> Unit,
  whatIfNot: (() -> Unit)
): Iterable<Boolean?> {
  var predicate: Boolean? = null

  this.forEach {
    val p = predicate
    predicate = if (p == null) {
      it
    } else {
      p or (it == true)
    }
  }

  if (predicate == true) {
    whatIf()
  } else {
    whatIfNot()
  }
  return this
}
