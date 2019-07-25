package com.wesoft.rxmvvm.data.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.wesoft.rxmvvm.data.BuildConfig
import com.wesoft.rxmvvm.data.api.APIService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


val networkModule = module {

    single {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        GsonBuilder()
            .serializeNulls()
            .create()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get<Gson>()))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(get<OkHttpClient>())
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }

    single {
        get<Retrofit>().create(APIService::class.java)
    }
}