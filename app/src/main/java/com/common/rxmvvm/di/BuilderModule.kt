package com.common.rxmvvm.di

import com.common.core.di.scope.PerActivity
import com.common.rxmvvm.ui.login.Login1Activity
import com.common.rxmvvm.ui.login.LoginActivity
import com.common.rxmvvm.ui.main.MainActivity
import com.common.rxmvvm.ui.main.SmartRefreshActivity
import com.common.rxmvvm.ui.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuilderModule {

    @PerActivity
    @ContributesAndroidInjector
    abstract fun splashActivity(): SplashActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun login1Activity(): Login1Activity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun loginActivity(): LoginActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun mainActivity(): MainActivity

    @PerActivity
    @ContributesAndroidInjector
    abstract fun smartRefreshActivity(): SmartRefreshActivity

}