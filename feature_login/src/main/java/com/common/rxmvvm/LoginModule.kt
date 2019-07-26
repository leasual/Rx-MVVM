package com.common.rxmvvm

import com.common.core.repository.LoginRepository
import com.common.rxmvvm.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { LoginRepository(get(), get()) }
    viewModel { LoginViewModel(get()) }
}