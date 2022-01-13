package com.weiwei.component.sample.common.listener

import android.text.Editable
import android.text.TextWatcher

/**
 * @author Weicools
 *
 * @date 2021.07.04
 */
open class SimpleWatcher : TextWatcher {
  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
  }

  override fun afterTextChanged(s: Editable?) {
  }
}