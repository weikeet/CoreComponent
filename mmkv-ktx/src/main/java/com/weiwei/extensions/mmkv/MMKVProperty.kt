/*
 * Copyright (c) 2022 Weiwei. https://github.com/lecymeng
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weiwei.extensions.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * A class that has a MMKV object. If you want to customize the MMKV,
 * you can override the kv object. For example:
 *
 * ```kotlin
 * object DataRepository : MMKVOwner {
 *   override val kv: MMKV = MMKV.mmkvWithID("MyID")
 * }
 * ```
 *
 * @author weicools
 */
interface MMKVStoreOwner {
  val kv: MMKV get() = MMKV.defaultMMKV()
}

/**
 * 可以通过 property.name 取到变量名作为 key，但是容易引入 BUG，比如变量重命名
 */

//region MMKV Property
class MMKVIntProperty(
  private val key: String,
  private val defaultValue: Int
) : ReadWriteProperty<MMKVStoreOwner, Int> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): Int =
    thisRef.kv.decodeInt(key, defaultValue)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: Int) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVLongProperty(
  private val key: String,
  private val defaultValue: Long
) : ReadWriteProperty<MMKVStoreOwner, Long> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): Long =
    thisRef.kv.decodeLong(key, defaultValue)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: Long) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVFloatProperty(
  private val key: String,
  private val defaultValue: Float
) : ReadWriteProperty<MMKVStoreOwner, Float> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): Float =
    thisRef.kv.decodeFloat(key, defaultValue)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: Float) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVDoubleProperty(
  private val key: String,
  private val defaultValue: Double
) : ReadWriteProperty<MMKVStoreOwner, Double> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): Double =
    thisRef.kv.decodeDouble(key, defaultValue)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: Double) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVBooleanProperty(
  private val key: String,
  private val defaultValue: Boolean
) : ReadWriteProperty<MMKVStoreOwner, Boolean> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): Boolean =
    thisRef.kv.decodeBool(key, defaultValue)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: Boolean) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVStringProperty(
  private val key: String
) : ReadWriteProperty<MMKVStoreOwner, String?> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): String? =
    thisRef.kv.decodeString(key, null)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: String?) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVStringWithDefaultProperty(
  private val key: String,
  private val defaultValue: String
) : ReadWriteProperty<MMKVStoreOwner, String> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): String =
    thisRef.kv.decodeString(key, defaultValue)!!

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: String) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVStringSetProperty(
  private val key: String
) : ReadWriteProperty<MMKVStoreOwner, Set<String>?> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): Set<String>? =
    thisRef.kv.decodeStringSet(key, null)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: Set<String>?) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVStringSetWithDefaultProperty(
  private val key: String,
  private val defaultValue: Set<String>
) : ReadWriteProperty<MMKVStoreOwner, Set<String>> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): Set<String> =
    thisRef.kv.decodeStringSet(key, defaultValue)!!

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: Set<String>) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVByteArrayProperty(
  private val key: String
) : ReadWriteProperty<MMKVStoreOwner, ByteArray?> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): ByteArray? =
    thisRef.kv.decodeBytes(key, null)

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: ByteArray?) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVByteArrayWithDefaultProperty(
  private val key: String,
  private val defaultValue: ByteArray
) : ReadWriteProperty<MMKVStoreOwner, ByteArray> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): ByteArray =
    thisRef.kv.decodeBytes(key, defaultValue)!!

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: ByteArray) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVParcelableProperty<V : Parcelable>(
  private val clazz: Class<V>,
  private val key: String
) : ReadWriteProperty<MMKVStoreOwner, V?> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): V? {
    return thisRef.kv.decodeParcelable(property.name, clazz)
  }

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: V?) {
    thisRef.kv.encode(key, value)
  }
}

class MMKVParcelablePropertyWithDefault<V : Parcelable>(
  private val clazz: Class<V>,
  private val key: String,
  private val defaultValue: V
) : ReadWriteProperty<MMKVStoreOwner, V> {
  override fun getValue(thisRef: MMKVStoreOwner, property: KProperty<*>): V {
    return thisRef.kv.decodeParcelable(property.name, clazz) ?: defaultValue
  }

  override fun setValue(thisRef: MMKVStoreOwner, property: KProperty<*>, value: V) {
    thisRef.kv.encode(key, value)
  }
}
//endregion

fun MMKVStoreOwner.mmkvInt(key: String, default: Int = 0) =
  MMKVIntProperty(key, default)

fun MMKVStoreOwner.mmkvLong(key: String, default: Long = 0L) =
  MMKVLongProperty(key, default)

fun MMKVStoreOwner.mmkvFloat(key: String, default: Float = 0f) =
  MMKVFloatProperty(key, default)

fun MMKVStoreOwner.mmkvDouble(key: String, default: Double = 0.0) =
  MMKVDoubleProperty(key, default)

fun MMKVStoreOwner.mmkvBoolean(key: String, default: Boolean = false) =
  MMKVBooleanProperty(key, default)

fun MMKVStoreOwner.mmkvString(key: String) =
  MMKVStringProperty(key)

fun MMKVStoreOwner.mmkvString(key: String, default: String) =
  MMKVStringWithDefaultProperty(key, default)

fun MMKVStoreOwner.mmkvStringSet(key: String) =
  MMKVStringSetProperty(key)

fun MMKVStoreOwner.mmkvStringSet(key: String, default: Set<String>) =
  MMKVStringSetWithDefaultProperty(key, default)

fun MMKVStoreOwner.mmkvBytes(key: String) =
  MMKVByteArrayProperty(key)

fun MMKVStoreOwner.mmkvBytes(key: String, default: ByteArray) =
  MMKVByteArrayWithDefaultProperty(key, default)

inline fun <reified T : Parcelable> MMKVStoreOwner.mmkvParcelable(key: String) =
  MMKVParcelableProperty(T::class.java, key)

inline fun <reified T : Parcelable> MMKVStoreOwner.mmkvParcelable(key: String, default: T) =
  MMKVParcelablePropertyWithDefault(T::class.java, key, default)
