package com.common.rxmvvm.di

import com.common.rxmvvm.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    AppModule::class,
    ViewModelModule::class,
    BuilderModule::class,
    NetworkModule::class
])

interface AppComponent : AndroidInjector<App> {

    @Component.Factory
    abstract class Builder : AndroidInjector.Factory<App>
}
