package com.weicools.component.sample.module.user.viewmodel.factory

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weicools.component.sample.module.user.data.UserRepository
import com.weicools.component.sample.module.user.viewmodel.LoginViewModel

/**
 * @author Weicools
 *
 * @date 2021.07.04
 */
class LoginModelFactory(
  private val context: Context,
  private val userRepository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return LoginViewModel(userRepository) as T
  }

}