package com.wesoft.rxmvvm

import com.wesoft.rxmvvm.data.repository.LoginRepository
import com.wesoft.rxmvvm.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { LoginRepository(get(), get()) }
    viewModel { LoginViewModel(get()) }
}