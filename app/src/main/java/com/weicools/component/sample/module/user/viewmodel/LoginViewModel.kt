package com.weicools.component.sample.module.user.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.weicools.component.sample.module.user.data.UserRepository

/**
 * @author Weicools
 *
 * @date 2021.07.04
 */
class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
  val userIdData: MutableLiveData<Int> = MutableLiveData()

  init {
    userIdData.value = userRepository.getUerId()
  }
}