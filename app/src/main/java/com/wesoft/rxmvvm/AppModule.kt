package com.wesoft.rxmvvm

import com.wesoft.rxmvvm.data.di.networkModule
import com.wesoft.rxmvvm.data.repository.LoginRepository
import com.wesoft.rxmvvm.data.repository.MainRepository
import com.wesoft.rxmvvm.login.LoginViewModel
import com.wesoft.rxmvvm.main.MainViewModel
import com.wesoft.rxmvvm.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = networkModule + module {

    factory { MainRepository(get(), get()) }
    factory { LoginRepository(get(), get()) }

    viewModel { SplashViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { MainViewModel(get()) }
}