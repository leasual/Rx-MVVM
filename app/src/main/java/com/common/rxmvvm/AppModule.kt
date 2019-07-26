package com.common.rxmvvm

import com.common.core.di.networkModule
import com.common.rxmvvm.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val splashModule = networkModule + module {
    viewModel { SplashViewModel() }
}