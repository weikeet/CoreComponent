@file:Suppress("UNCHECKED_CAST", "unused")

package com.weiwei.extensions.bundler

import android.app.Activity
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * @author weiwei
 * @date 2021.05.06
 */

/**
 * Retrieves a primitive type of extended data from the Intent lazily.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.bundle(key: String, defaultValue: T): Lazy<T> {
  return activityVariableBundler(defaultValue) {
    when (defaultValue) {
      is Boolean -> intent.getBooleanExtra(key, defaultValue)
      is Byte -> intent.getByteExtra(key, defaultValue)
      is Char -> intent.getCharExtra(key, defaultValue)
      is Double -> intent.getDoubleExtra(key, defaultValue)
      is Float -> intent.getFloatExtra(key, defaultValue)
      is Int -> intent.getIntExtra(key, defaultValue)
      is Long -> intent.getLongExtra(key, defaultValue)
      is Short -> intent.getShortExtra(key, defaultValue)
      is CharSequence -> intent.getStringExtra(key)

      else -> throw IllegalArgumentException("Illegal value type ${defaultValue.javaClass} for key: $key")
    } as? T
  }
}

/**
 * Retrieves a references type of extended data from the Intent lazily.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.bundle(
  key: String, crossinline defaultValue: () -> T? = { null }
): Lazy<T?> {
  val objectType = T::class.javaObjectType
  return activityTypedBundler(defaultValue) {
    when {
      // references
      Bundle::class.java.isAssignableFrom(objectType) -> intent.getBundleExtra(key) as? T
      CharSequence::class.java.isAssignableFrom(objectType) -> intent.getCharSequenceExtra(key) as? T
      Parcelable::class.java.isAssignableFrom(objectType) -> intent.getParcelableExtra<Parcelable>(key) as? T
      Serializable::class.java.isAssignableFrom(objectType) -> intent.getSerializableExtra(key) as? T

      // scalar arrays
      BooleanArray::class.java.isAssignableFrom(objectType) -> intent.getBooleanArrayExtra(key) as? T
      ByteArray::class.java.isAssignableFrom(objectType) -> intent.getByteArrayExtra(key) as? T
      CharArray::class.java.isAssignableFrom(objectType) -> intent.getCharArrayExtra(key) as? T
      DoubleArray::class.java.isAssignableFrom(objectType) -> intent.getDoubleArrayExtra(key) as? T
      FloatArray::class.java.isAssignableFrom(objectType) -> intent.getFloatArrayExtra(key) as? T
      IntArray::class.java.isAssignableFrom(objectType) -> intent.getIntArrayExtra(key) as? T
      LongArray::class.java.isAssignableFrom(objectType) -> intent.getLongArrayExtra(key) as? T
      ShortArray::class.java.isAssignableFrom(objectType) -> intent.getShortArrayExtra(key) as? T

      else -> throw IllegalArgumentException("Illegal value type $objectType for key: $key")
    }
  }
}

/**
 * Retrieves a non-null references type of extended data from the Intent lazily.
 *
 * @param key The name of the desired item.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle] or null.
 * @exception NullPointerException When there is no desired value from the Intent.
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.bundleNonNull(
  key: String
): Lazy<T> {
  val objectType = T::class.javaObjectType
  return activityNonNullTypedBundler {
    when {
      // references
      Bundle::class.java.isAssignableFrom(objectType) -> intent.getBundleExtra(key) as T
      CharSequence::class.java.isAssignableFrom(objectType) -> intent.getCharSequenceExtra(key) as T
      Parcelable::class.java.isAssignableFrom(objectType) -> intent.getParcelableExtra<Parcelable>(key) as T
      Serializable::class.java.isAssignableFrom(objectType) -> intent.getSerializableExtra(key) as T

      // scalar arrays
      BooleanArray::class.java.isAssignableFrom(objectType) -> intent.getBooleanArrayExtra(key) as T
      ByteArray::class.java.isAssignableFrom(objectType) -> intent.getByteArrayExtra(key) as T
      CharArray::class.java.isAssignableFrom(objectType) -> intent.getCharArrayExtra(key) as T
      DoubleArray::class.java.isAssignableFrom(objectType) -> intent.getDoubleArrayExtra(key) as T
      FloatArray::class.java.isAssignableFrom(objectType) -> intent.getFloatArrayExtra(key) as T
      IntArray::class.java.isAssignableFrom(objectType) -> intent.getIntArrayExtra(key) as T
      LongArray::class.java.isAssignableFrom(objectType) -> intent.getLongArrayExtra(key) as T
      ShortArray::class.java.isAssignableFrom(objectType) -> intent.getShortArrayExtra(key) as T

      else -> throw IllegalArgumentException("Illegal value type $objectType for key: $key")
    }
  }
}

/**
 * Retrieves a references array type of extended data from the Intent lazily.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.bundleArray(
  key: String, crossinline defaultValue: () -> Array<T>? = { null }
): Lazy<Array<T>?> {
  val javaObjectType = T::class.javaObjectType
  return activityArrayBundler(defaultValue) {
    val result = when {
      String::class.java.isAssignableFrom(javaObjectType) -> intent.getStringArrayExtra(key)
      CharSequence::class.java.isAssignableFrom(javaObjectType) -> intent.getCharSequenceArrayExtra(key)
      Parcelable::class.java.isAssignableFrom(javaObjectType) -> intent.getParcelableArrayExtra(key)

      else -> throw IllegalArgumentException("Illegal value type $javaObjectType for key: $key")
    } as? Array<*>
    result?.filterIsInstance<T>()?.toTypedArray()
  }
}

/**
 * Retrieves a references array list type of extended data from the Intent lazily.
 *
 * @param key The name of the desired item.
 * @param defaultValue The value to be returned if no value of the desired type is stored with the given name.
 *
 * @throws IllegalArgumentException When a value is not a supported type of [Bundle].
 */
@JvmSynthetic
@InlineBundleDsl
inline fun <reified T : Any> Activity.bundleArrayList(
  key: String, crossinline defaultValue: () -> ArrayList<T>? = { null }
): Lazy<ArrayList<T>?> {
  val javaObjectType = T::class.javaObjectType
  return activityArrayListBundler(defaultValue) {
    when {
      String::class.java.isAssignableFrom(javaObjectType) -> intent.getStringArrayListExtra(key)
      CharSequence::class.java.isAssignableFrom(javaObjectType) -> intent.getCharSequenceArrayListExtra(key)
      Parcelable::class.java.isAssignableFrom(javaObjectType) -> intent.getParcelableArrayListExtra<Parcelable>(key)

      else -> throw IllegalArgumentException("Illegal value type $javaObjectType for key: $key")
    } as ArrayList<T>?
  }
}
