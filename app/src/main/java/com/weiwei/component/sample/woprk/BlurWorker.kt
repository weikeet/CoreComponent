package com.weiwei.component.sample.woprk

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.weiwei.component.sample.common.BaseConstant
import com.weiwei.component.sample.common.BaseConstant.KEY_IMAGE_URI
import com.weiwei.component.sample.utils.blurBitmap
import com.weiwei.component.sample.utils.makeStatusNotification
import com.weiwei.component.sample.utils.writeBitmapToFile

/**
 * @author Weicools
 *
 * @date 2021.07.04
 */
class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {
  override fun doWork(): Result {
    val context = applicationContext
    val resourceUri = inputData.getString(BaseConstant.KEY_IMAGE_URI)

    // 通知开始处理图片
    makeStatusNotification("Blurring image", context)

    return try {
      // 图片处理逻辑
      if (TextUtils.isEmpty(resourceUri)) {
        throw IllegalArgumentException("Invalid input uri")
      }

      val resolver = context.contentResolver
      val picture = BitmapFactory.decodeStream(resolver.openInputStream(Uri.parse(resourceUri)))
      // 创建Bitmap文件
      val output = blurBitmap(picture, context)
      // 存入路径
      val outputUri = writeBitmapToFile(context, output)

      // 输出路径
      val outPutData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
      makeStatusNotification("Output is $outputUri", context)
      Result.success(outPutData)
    } catch (throwable: Throwable) {
      Result.failure()
    }
  }
}