package com.weiwei.extensions.bundler

import android.app.Activity
import androidx.lifecycle.LiveData
import com.weiwei.extensions.bundler.IntentLiveDataProvider.provideArrayIntentLiveData
import com.weiwei.extensions.bundler.IntentLiveDataProvider.provideArrayListIntentLiveData
import com.weiwei.extensions.bundler.IntentLiveDataProvider.provideIntentLiveData

/**
 * @author weiwei
 * @date 2021.05.06
 */

/**
 * Returns a [LiveData] which has a retrieved primitive type of extended data from the Intent.
 * The implementation of the [LiveData] is a [SingleShotLiveData] that emits data only a single time to
 * a single observer. We can observe only once using one observer. And the observer will be unregistered
 * from the [SingleShotLiveData] after observing data at once.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.observeBundle(
  key: String, defaultValue: T
): Lazy<LiveData<T>> = lazy(LazyThreadSafetyMode.NONE) {
  val initialValue by bundle(key, defaultValue)
  provideIntentLiveData { initialValue }
}

/**
 * Returns a [LiveData] which has a references primitive type of extended data from the Intent.
 * The implementation of the [LiveData] is a [SingleShotLiveData] that emits data only a single time to
 * a single observer. We can observe only once using one observer. And the observer will be unregistered
 * from the [SingleShotLiveData] after observing data at once.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any?> Activity.observeBundle(
  key: String, crossinline defaultValue: () -> T? = { null }
): Lazy<LiveData<T>> = lazy(LazyThreadSafetyMode.NONE) {
  val initialValue by bundle(key, defaultValue)
  provideIntentLiveData { initialValue }
}

/**
 * Returns a [LiveData] which has a references array type of extended data from the Intent.
 * The implementation of the [LiveData] is a [SingleShotLiveData] that emits data only a single time to
 * a single observer. We can observe only once using one observer. And the observer will be unregistered
 * from the [SingleShotLiveData] after observing data at once.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.observeBundleArray(
  key: String, crossinline defaultValue: () -> Array<T>? = { null }
): Lazy<LiveData<Array<T>>> = lazy(LazyThreadSafetyMode.NONE) {
  val initialValue by bundleArray(key, defaultValue)
  provideArrayIntentLiveData { initialValue }
}

/**
 * Returns a [LiveData] which has a references array list type of extended data from the Intent.
 * The implementation of the [LiveData] is a [SingleShotLiveData] that emits data only a single time to
 * a single observer. We can observe only once using one observer. And the observer will be unregistered
 * from the [SingleShotLiveData] after observing data at once.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.observeBundleArrayList(
  key: String, crossinline defaultValue: () -> ArrayList<T>? = { null }
): Lazy<LiveData<ArrayList<T>>> = lazy(LazyThreadSafetyMode.NONE) {
  val initialValue by bundleArrayList(key, defaultValue)
  provideArrayListIntentLiveData { initialValue }
}
