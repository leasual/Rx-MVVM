package com.common.rxmvvm

import com.common.core.di.networkModule
import com.common.rxmvvm.api.APIService
import com.common.rxmvvm.repository.LoginRepository
import com.common.rxmvvm.repository.MainRepository
import com.common.rxmvvm.ui.login.Login1ViewModel
import com.common.rxmvvm.ui.login.LoginViewModel
import com.common.rxmvvm.ui.main.MainViewModel
import com.common.rxmvvm.ui.main.SmartRefreshViewModel
import com.common.rxmvvm.ui.main.WebViewModel
import com.common.rxmvvm.ui.splash.SplashViewModel
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single {
        get<Retrofit>().create(APIService::class.java)
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get<OkHttpClient>())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
}

val splashModule = module {
    viewModel { SplashViewModel() }
}

val loginModule = module {
    single { LoginRepository(get(), get()) }
    viewModel { Login1ViewModel(get()) }
}

val mainModule = module {
    single { MainRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { WebViewModel() }
    viewModel { SmartRefreshViewModel(get()) }
}

val appModule = networkModule + apiModule + splashModule + loginModule + mainModule