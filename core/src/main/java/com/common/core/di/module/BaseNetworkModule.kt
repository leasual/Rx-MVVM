package com.common.core.di.module

import com.common.core.api.NullStringToEmptyAdapterFactory
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Created by james.li on 2017/bg/20.
 */
@Module
abstract class BaseNetworkModule {

    val DEFAULT_TIMEOUT: Long = 60

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder()
        .serializeNulls()
        //.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
        .registerTypeAdapterFactory(NullStringToEmptyAdapterFactory())
        .create()

//    @Provides
//    @Singleton
//    fun provideCache(app: Application): Cache {
//        val cacheSize = 10 * 1024 * 1024 //10MB
//        return Cache(app.cacheDir, cacheSize.toLong())
//    }

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() = HttpLoggingInterceptor().apply {
        level = if (provideDebug()) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(/*cache: Cache, */interceptor: HttpLoggingInterceptor): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
        .retryOnConnectionFailure(false)
        .build()

    abstract fun provideURL(): String

    abstract fun provideDebug(): Boolean


    @Provides
    @Singleton
    fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(provideURL())
        .addConverterFactory(GsonConverterFactory.create(gson))
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

//    @Provides
//    @Singleton
//    fun provideWSService(retrofit: Retrofit): BaseAPIService = retrofit.create(BaseAPIService::class.java)
}