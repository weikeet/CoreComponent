package com.weicools.component.sample.module.user.ui

import androidx.fragment.app.viewModels
import com.weicools.component.sample.module.user.data.UserRepository
import com.weicools.component.sample.module.user.viewmodel.LoginViewModel
import com.weicools.component.sample.module.user.viewmodel.factory.LoginModelFactory
import com.weicools.core.app.BaseFragment

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