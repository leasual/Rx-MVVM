package com.common.rxmvvm

import com.common.core.di.networkModule
import com.common.rxmvvm.api.APIService
import com.common.rxmvvm.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val splashModule = networkModule + module {
    single {
        get<Retrofit>().create(APIService::class.java)
    }
    viewModel { SplashViewModel() }
}
