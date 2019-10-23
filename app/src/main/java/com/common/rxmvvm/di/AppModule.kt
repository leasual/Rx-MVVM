package com.common.rxmvvm.di

import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.common.rxmvvm.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideSharePreferences(app: App): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(app)

    //    @Provides
//    @Singleton
//    fun provideSharePreferencesUtil(app: App): PreferencesUtil = PreferencesUtil(app)
}