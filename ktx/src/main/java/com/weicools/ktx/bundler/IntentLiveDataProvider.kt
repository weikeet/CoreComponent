package com.weicools.ktx.bundler

import androidx.annotation.MainThread
import androidx.lifecycle.LiveData

/**
 * @author weicools
 * @date 2021.05.06
 */

/**
 * A provider for providing [LiveData] which has the desired intent data.
 */
@PublishedApi
internal object IntentLiveDataProvider {

  /**
   * Provide a [LiveData] which has a primitive and references type of extended data from intent.
   * The implementation of the [LiveData] is a [SingleShotLiveData] that emits data only a single time to
   * a single observer. We can observe only once using one observer. And the observer will be unregistered
   * from the [SingleShotLiveData] after observing data at once.
   *
   * @param initialValue An initial value for a new [LiveData].
   */
  @MainThread
  @PublishedApi
  internal inline fun <reified T : Any?> provideIntentLiveData(
    crossinline initialValue: () -> T?
  ): LiveData<T> = SingleShotLiveData<T>(initialValue())

  /**
   * Provide a [LiveData] which has a references array type of extended data from intent.
   * The implementation of the [LiveData] is a [SingleShotLiveData] that emits data only a single time to
   * a single observer. We can observe only once using one observer. And the observer will be unregistered
   * from the [SingleShotLiveData] after observing data at once.
   *
   * @param initialValue An initial value for a new [LiveData].
   */
  @MainThread
  @PublishedApi
  internal inline fun <reified T : Any> provideArrayIntentLiveData(
    crossinline initialValue: () -> Array<T>?
  ): LiveData<Array<T>> = SingleShotLiveData<Array<T>>(initialValue())

  /**
   * Provide a [LiveData] which has a references array list type of extended data from intent.
   * The implementation of the [LiveData] is a [SingleShotLiveData] that emits data only a single time to
   * a single observer. We can observe only once using one observer. And the observer will be unregistered
   * from the [SingleShotLiveData] after observing data at once.
   *
   * @param initialValue An initial value for a new [LiveData].
   */
  @MainThread
  @PublishedApi
  internal inline fun <reified T : Any> provideArrayListIntentLiveData(
    crossinline initialValue: () -> ArrayList<T>?
  ): LiveData<ArrayList<T>> = SingleShotLiveData<ArrayList<T>>(initialValue())
}
