package com.common.rxmvvm

import com.common.core.di.networkModule
import com.common.rxmvvm.api.APIService
import com.common.rxmvvm.repository.LoginRepository
import com.common.rxmvvm.repository.MainRepository
import com.common.rxmvvm.ui.login.LoginViewModel
import com.common.rxmvvm.ui.main.MainViewModel
import com.common.rxmvvm.ui.main.SmartRefreshViewModel
import com.common.rxmvvm.ui.main.WebViewModel
import com.common.rxmvvm.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit>().create(APIService::class.java)
    }
}

val splashModule = module {
    viewModel { SplashViewModel() }
}

val loginModule = module {
    single { LoginRepository(get(), get()) }
    viewModel { LoginViewModel(get()) }
}

val mainModule = module {
    single { MainRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { WebViewModel() }
    viewModel { SmartRefreshViewModel(get()) }
}

val appModule = networkModule + apiModule + splashModule + loginModule + mainModule