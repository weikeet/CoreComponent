package com.weicools.utils.process

import android.annotation.SuppressLint
import android.app.Application
import android.os.Build
import android.os.Process
import android.text.TextUtils
import android.util.Log
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.lang.reflect.Method

/**
 * @author weicools
 * @date 2021.06.15
 *
 * References:
 * - https://juejin.cn/post/6877127949452050446
 * - https://developer.android.google.cn/reference/android/app/Application#getProcessName()
 */
object ProcessUtils {
  private const val TAG = "ProcessUtils"

  private var processName: String? = null

  @JvmStatic
  fun getProcessName(): String {
    val name = processName
    if (name != null && TextUtils.isEmpty(name)) {
      return name
    }

    if (Build.VERSION.SDK_INT >= 28) {
      val appProcessName = Application.getProcessName()
      Log.d(TAG, "getProcessName: by Application=$appProcessName")
      processName = appProcessName
      return appProcessName
    }

    val reflectProcessName = getProcessNameByReflect()
    if (reflectProcessName != null && TextUtils.isEmpty(reflectProcessName)) {
      Log.d(TAG, "getProcessName: by Reflect=$reflectProcessName")
      processName = reflectProcessName
      return reflectProcessName
    }

    val cmdlineProcessName = getProcessNameByCmdline()
    if (cmdlineProcessName != null && TextUtils.isEmpty(cmdlineProcessName)) {
      Log.d(TAG, "getProcessName: by Cmdline=$cmdlineProcessName")
      processName = cmdlineProcessName
      return cmdlineProcessName
    }

    return "";
  }

  private fun getProcessNameByReflect(): String? {
    if (Build.VERSION.SDK_INT < 18) {
      return null
    }

    var processName: String? = null
    try {
      @SuppressLint("PrivateApi")
      val activityThread = Class.forName("android.app.ActivityThread")

      @SuppressLint("PrivateApi")
      val getProcessName: Method = activityThread.getDeclaredMethod("currentProcessName")

      processName = getProcessName.invoke(null) as String?
    } catch (e: Throwable) {
    }
    return processName
  }

  private fun getProcessNameByCmdline(): String? {
    var processName: String? = null
    try {
      val file = File("/proc/" + Process.myPid() + "/" + "cmdline")
      val bufferedReader = BufferedReader(FileReader(file))
      processName = bufferedReader.readLine().trim { it <= ' ' }
      bufferedReader.close()
    } catch (e: Throwable) {
      e.printStackTrace()
    }
    return processName
  }
}