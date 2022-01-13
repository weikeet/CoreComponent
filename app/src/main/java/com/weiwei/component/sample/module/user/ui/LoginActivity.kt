package com.weiwei.component.sample.module.user.ui

import android.os.Bundle
import android.widget.FrameLayout
import com.weiwei.core.app.BaseActivity

/**
 * @author Weicools
 *
 * @date 2021.07.04
 */
class LoginActivity : BaseActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(FrameLayout(this))
  }
}