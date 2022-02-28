package com.weiwei.core.global

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.Message
import androidx.annotation.RequiresApi

/**
 * @author weiwei
 * @date 2022.01.14
 */
class GlobalHandler(val handler: Handler) {

    val looper: Looper get() = handler.looper

    fun getMessageName(message: Message): String = handler.getMessageName(message)

    //region obtainMessage
    fun obtainMessage(): Message = Message.obtain(handler)

    fun obtainMessage(what: Int, obj: Any?): Message = Message.obtain(handler, what, obj)

    fun obtainMessage(what: Int, arg1: Int, arg2: Int): Message = Message.obtain(handler, what, arg1, arg2)

    fun obtainMessage(what: Int, arg1: Int, arg2: Int, obj: Any?): Message = Message.obtain(handler, what, arg1, arg2, obj)
    //endregion

    //region Callback
    fun post(r: Runnable): Boolean = handler.post(r)

    fun postDelayed(r: Runnable, delayMillis: Long): Boolean = handler.postDelayed(r, delayMillis)

    fun postAtTime(r: Runnable, uptimeMillis: Long): Boolean = handler.postAtTime(r, uptimeMillis)

    fun postAtFrontOfQueue(r: Runnable): Boolean = handler.postAtFrontOfQueue(r)

    fun removeCallbacks(r: Runnable) = handler.removeCallbacks(r)

    @RequiresApi(Build.VERSION_CODES.Q)
    fun hasCallbacks(r: Runnable): Boolean = handler.hasCallbacks(r)
    //endregion

    //region Message
    fun sendMessage(msg: Message): Boolean = handler.sendMessage(msg)

    fun sendMessageDelayed(msg: Message, delayMillis: Long): Boolean = handler.sendMessageDelayed(msg, delayMillis)

    fun sendMessageAtTime(msg: Message, delayMillis: Long): Boolean = handler.sendMessageAtTime(msg, delayMillis)

    fun sendEmptyMessage(what: Int): Boolean = handler.sendEmptyMessage(what)

    fun sendEmptyMessageDelayed(what: Int, delayMillis: Long): Boolean = handler.sendEmptyMessageDelayed(what, delayMillis)

    fun sendEmptyMessageAtTime(what: Int, uptimeMillis: Long): Boolean = handler.sendEmptyMessageAtTime(what, uptimeMillis)

    fun sendMessageAtFrontOfQueue(msg: Message): Boolean = handler.sendMessageAtFrontOfQueue(msg)

    fun removeMessages(what: Int) = handler.removeMessages(what)

    fun removeMessages(what: Int, obj: Any?) = handler.removeMessages(what, obj)

    fun hasMessages(what: Int): Boolean = handler.hasMessages(what)

    fun hasMessages(what: Int, obj: Any?): Boolean = handler.hasMessages(what, obj)
    //endregion

}