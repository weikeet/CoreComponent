package com.weicools.ktx.bundler

import androidx.fragment.app.Fragment

/**
 * @author weicools
 * @date 2021.05.06
 */

/**
 * Returns an instance of a bundler that has the arguments of a fragment.
 */
fun Fragment.fragmentBundler(): Bundler = Bundler().replaceExtras(arguments)

/**
 * Retrieves a primitive type of extended data from arguments lazily.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentVariableBundler(
  defaultValue: T, crossinline initializer: Bundler.() -> T?
): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
  fragmentBundler().initializer() ?: defaultValue
}

/**
 * Retrieves a primitive type of extended data from arguments immediately.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentVariableBundlerValue(
  defaultValue: T, crossinline initializer: Bundler.() -> T?
): T = fragmentBundler().initializer() ?: defaultValue

/**
 * Retrieves a primitive type of extended data from arguments lazily.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentTypedBundler(
  crossinline defaultValue: () -> T? = { null },
  crossinline initializer: Bundler.() -> T?
): Lazy<T?> = lazy(LazyThreadSafetyMode.NONE) {
  fragmentBundler().initializer() ?: defaultValue()
}

/**
 * Retrieves a primitive type of extended data from arguments immediately.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentTypedBundlerValue(
  crossinline defaultValue: () -> T? = { null },
  crossinline initializer: Bundler.() -> T?
): T? = fragmentBundler().initializer() ?: defaultValue()

/**
 * Retrieves a primitive type of extended data from arguments lazily.
 *
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentNonNullTypedBundler(
  crossinline initializer: Bundler.() -> T
): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) {
  fragmentBundler().initializer()
}

/**
 * Retrieves a primitive type of extended data from arguments immediately.
 *
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentNonNullTypedBundlerValue(
  crossinline initializer: Bundler.() -> T
): T = fragmentBundler().initializer()

/**
 * Retrieves a primitive type of extended data from arguments lazily.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentArrayBundler(
  crossinline defaultValue: () -> Array<T>? = { null },
  crossinline initializer: Bundler.() -> Array<T>?
): Lazy<Array<T>?> = lazy(LazyThreadSafetyMode.NONE) {
  fragmentBundler().initializer() ?: defaultValue()
}

/**
 * Retrieves a primitive type of extended data from arguments immediately.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentArrayBundlerValue(
  crossinline defaultValue: () -> Array<T>? = { null },
  crossinline initializer: Bundler.() -> Array<T>?
): Array<T>? = fragmentBundler().initializer() ?: defaultValue()

/**
 * Retrieves a primitive type of extended data from arguments lazily.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentArrayListBundler(
  crossinline defaultValue: () -> ArrayList<T>? = { null },
  crossinline initializer: Bundler.() -> ArrayList<T>?
): Lazy<ArrayList<T>?> = lazy(LazyThreadSafetyMode.NONE) {
  fragmentBundler().initializer() ?: defaultValue()
}

/**
 * Retrieves a primitive type of extended data from arguments immediately.
 *
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 * @param initializer The initializer for providing an instance of the type parameter.
 */
@PublishedApi
@JvmSynthetic
@InlineBundleDsl
internal inline fun <reified T : Any> Fragment.fragmentArrayListBundlerValue(
  crossinline defaultValue: () -> ArrayList<T>? = { null },
  crossinline initializer: Bundler.() -> ArrayList<T>?
): ArrayList<T>? = fragmentBundler().initializer() ?: defaultValue()
