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

package com.weiwei.theme

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View

/**
 * @author weiwei
 * @date 2022.02.28
 */
internal class SimpleImageView(context: Context) : View(context) {
  var bitmap: Bitmap? = null
    set(value) {
      field = value
      invalidate()
    }

  override fun onDraw(canvas: Canvas) {
    val bitmap = this.bitmap
    if (bitmap != null) {
      canvas.drawBitmap(bitmap, 0f, 0f, null)
    }
  }
}