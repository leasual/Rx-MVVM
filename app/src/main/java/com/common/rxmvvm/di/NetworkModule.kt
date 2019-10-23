package com.common.rxmvvm.di

import com.common.core.di.module.BaseNetworkModule
import com.common.rxmvvm.BuildConfig
import com.common.rxmvvm.api.APIService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule: BaseNetworkModule() {

    override fun provideURL(): String = BuildConfig.BASE_URL

    override fun provideDebug(): Boolean = BuildConfig.DEBUG

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): APIService = retrofit.create(APIService::class.java)

}