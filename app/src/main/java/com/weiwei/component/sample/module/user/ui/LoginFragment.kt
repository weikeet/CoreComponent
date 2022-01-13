package com.weiwei.component.sample.module.user.ui

import androidx.fragment.app.viewModels
import com.weiwei.component.sample.module.user.data.UserRepository
import com.weiwei.component.sample.module.user.viewmodel.LoginViewModel
import com.weiwei.component.sample.module.user.viewmodel.factory.LoginModelFactory
import com.weiwei.core.app.BaseFragment

/**
 * @author Weicools
 *
 * @date 2021.07.04
 */
class LoginFragment : BaseFragment() {
  private val loginViewModel by viewModels<LoginViewModel> {
    LoginModelFactory(requireContext(), UserRepository())
  }
}