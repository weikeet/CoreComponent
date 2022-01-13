package com.weiwei.component.sample.binding

import android.widget.EditText
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.weiwei.component.sample.common.listener.SimpleWatcher
import jp.wasabeef.glide.transformations.RoundedCornersTransformation

/**
 * @author Weicools
 *
 * @date 2021.07.04
 */

@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl:String?){
  if(!imageUrl.isNullOrEmpty()){
    Glide.with(view.context)
      .asBitmap()
      .load(imageUrl)
      .centerCrop()
      .into(view)
  }
}

// 加载带圆角的头像
@BindingAdapter("imageTransFromUrl")
fun bindImageTransFromUrl(view: ImageView, imageUrl:String?){
  if(!imageUrl.isNullOrEmpty()){
    Glide.with(view.context)
      .load(imageUrl)
      .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.ALL)))
      .into(view)
  }
}

// 文本监听器
@BindingAdapter("addTextChangedListener")
fun addTextChangedListener(editText: EditText, simpleWatcher: SimpleWatcher) {
  editText.addTextChangedListener(simpleWatcher)
}