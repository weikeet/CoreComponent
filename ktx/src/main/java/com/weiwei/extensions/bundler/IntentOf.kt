@file:Suppress("unused")

package com.weiwei.extensions.bundler

import android.content.Context
import android.content.Intent
import android.net.Uri
import kotlin.reflect.KClass

/**
 * @author weiwei
 * @date 2021.05.06
 */

/**
 * Creates an instance of the intent.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@JvmSynthetic
@InlineIntentOnly
inline fun intentOf(crossinline block: Bundler.() -> Unit): Intent {
  return Bundler().apply(block).intent
}

/**
 * Creates an instance of the intent from an intent.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@JvmSynthetic
@InlineIntentOnly
inline fun Intent.intentOf(crossinline block: Bundler.() -> Unit): Intent {
  return Bundler(Intent(this)).apply(block).intent
}

/**
 * Creates an instance of the intent with an action.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@JvmSynthetic
@InlineIntentOnly
inline fun String.intentOf(crossinline block: Bundler.() -> Unit): Intent {
  return Bundler(Intent(this)).apply(block).intent
}

/**
 * Creates an instance of the intent with action and URI.
 *
 * @param uri The Intent data URI.
 * @param block A lambda domain scope of the [Bundler].
 */
@JvmSynthetic
@InlineIntentOnly
inline fun String.intentOf(uri: Uri, crossinline block: Bundler.() -> Unit): Intent {
  return Bundler(Intent(this, uri)).apply(block).intent
}

/**
 * Creates an instance of the intent with packageContext and a target class.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@JvmSynthetic
@InlineIntentOnly
inline fun <reified T : Any> Context.intentOf(
  crossinline block: Bundler.() -> Unit
): Intent {
  return Bundler(Intent(this, T::class.java)).apply(block).intent
}

/**
 * Creates an instance of the intent with packageContext, a target class, an action and URI.
 *
 * @param action The Intent action, such as ACTION_VIEW.
 * @param uri The Intent data URI.
 * @param block A lambda domain scope of the [Bundler].
 */
@JvmSynthetic
@InlineIntentOnly
inline fun <reified T : Any> Context.intentOf(
  action: String, uri: Uri,
  crossinline block: Bundler.() -> Unit
): Intent {
  return Bundler(Intent(action, uri, this, T::class.java)).apply(block).intent
}

/**
 * Creates an instance of the intent.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@Deprecated(
  message = "use intentOf() instead",
  replaceWith = ReplaceWith(
    "intentOf(block)",
    imports = ["com.weiwei.extensions.bundler.intentOf"]
  )
)
@JvmSynthetic
@InlineIntentOnly
inline fun intent(crossinline block: Bundler.() -> Unit): Intent {
  return Bundler().apply(block).intent
}

/**
 * Creates an instance of the intent from an intent.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@Deprecated(
  message = "use intentOf() instead",
  replaceWith = ReplaceWith(
    "intentOf(block)",
    imports = ["com.weiwei.extensions.bundler.intentOf"]
  )
)
@JvmSynthetic
@InlineIntentOnly
inline fun Intent.intent(crossinline block: Bundler.() -> Unit): Intent {
  return Bundler(Intent(this)).apply(block).intent
}

/**
 * Creates an instance of the intent with action and URI.
 *
 * @param uri The Intent data URI.
 * @param block A lambda domain scope of the [Bundler].
 */
@Deprecated(
  message = "use intentOf() instead",
  replaceWith = ReplaceWith(
    "intentOf(uri, block)",
    imports = ["com.weiwei.extensions.bundler.intentOf"]
  )
)
@JvmSynthetic
@InlineIntentOnly
inline fun String.intent(uri: Uri, crossinline block: Bundler.() -> Unit): Intent {
  return Bundler(Intent(this, uri)).apply(block).intent
}

/**
 * Creates an instance of the intent with an action.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@Deprecated(
  message = "use intentOf() instead",
  replaceWith = ReplaceWith(
    "intentOf(block)",
    imports = ["com.weiwei.extensions.bundler.intentOf"]
  )
)
@JvmSynthetic
@InlineIntentOnly
inline fun String.intent(crossinline block: Bundler.() -> Unit): Intent {
  return Bundler(Intent(this)).apply(block).intent
}

/**
 * Creates an instance of the intent with packageContext and a target class.
 *
 * @param block A lambda domain scope of the [Bundler].
 */
@Deprecated(
  message = "use intentOf<T>() instead",
  replaceWith = ReplaceWith(
    "intentOf<T>(block)",
    imports = ["com.weiwei.extensions.bundler.intentOf"]
  )
)
@JvmSynthetic
@InlineIntentOnly
inline fun <reified T : Any> Context.intent(
  clazz: KClass<T>,
  crossinline block: Bundler.() -> Unit
): Intent {
  return Bundler(Intent(this, clazz.java)).apply(block).intent
}

/**
 * Creates an instance of the intent with packageContext, a target class, an action and URI.
 *
 * @param clazz The component class that is to be used for the intent.
 * @param action The Intent action, such as ACTION_VIEW.
 * @param uri The Intent data URI.
 * @param block A lambda domain scope of the [Bundler].
 */
@Deprecated(
  message = "use intentOf<T>() instead",
  replaceWith = ReplaceWith(
    "intentOf<T>(action, uri, block)",
    imports = ["com.weiwei.extensions.bundler.intentOf"]
  )
)
@JvmSynthetic
@InlineIntentOnly
inline fun <reified T : Any> Context.intent(
  clazz: KClass<T>, action: String, uri: Uri,
  crossinline block: Bundler.() -> Unit
): Intent {
  return Bundler(Intent(action, uri, this, clazz.java)).apply(block).intent
}

/**
 * Creates a new bundle and put it into the [intent] with the given key/value pairs as elements.
 *
 * @param pairs key/value pairs.
 */
@JvmSynthetic
@InlineIntentOnly
fun Intent.bundleOf(vararg pairs: Pair<String, Any?>) = apply {
  putExtras(com.weiwei.extensions.bundler.bundleOf(*pairs))
}
