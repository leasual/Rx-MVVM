package com.common.rxmvvm

import com.common.core.di.networkModule
import com.common.core.repository.LoginRepository
import com.common.core.repository.MainRepository
import com.common.rxmvvm.login.LoginViewModel
import com.common.rxmvvm.main.MainViewModel
import com.common.rxmvvm.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = networkModule + module {

    factory { MainRepository(get(), get()) }
    factory { LoginRepository(get(), get()) }

    viewModel { SplashViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
}