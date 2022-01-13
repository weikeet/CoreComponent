package com.weiwei.extensions.bundler

import android.util.Log
import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

/**
 * @author weiwei
 * @date 2021.05.06
 */

/**
 * SingleShotLiveData is an implementation of the [MutableLiveData] that emits [T] data
 * only a single time to a single observer. We can observe only once using one observer.
 * And the observer will be unregistered from the [SingleShotLiveData] after observing data at once.
 */
@PublishedApi
internal class SingleShotLiveData<T> constructor(initialValue: T? = null) : MutableLiveData<T>() {

  private val emitted = AtomicBoolean(false)

  init {
    value = initialValue
  }

  @MainThread
  override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
    if (emitted.compareAndSet(false, true)) {
      super.observe(owner) {
        observer.onChanged(it)
        removeObserver(observer)
      }
    } else {
      Log.i(TAG, "SingleShotLiveData already has been emitted data.")
    }
  }

  @MainThread
  override fun observeForever(observer: Observer<in T>) {
    if (emitted.compareAndSet(false, true)) {
      super.observeForever {
        observer.onChanged(it)
        removeObserver(observer)
      }
    } else {
      Log.i(TAG, "SingleShotLiveData already has been emitted data.")
    }
  }

  @MainThread
  override fun setValue(value: T?) {
    if (!emitted.get()) {
      super.setValue(value)
    } else {
      Log.i(TAG, "SingleShotLiveData already has been emitted data.")
    }
  }

  override fun postValue(value: T?) {
    if (!emitted.get()) {
      super.postValue(value)
    } else {
      Log.i(TAG, "SingleShotLiveData already has been emitted data.")
    }
  }

  companion object {
    private const val TAG = "SingleShotLiveData"
  }
}
