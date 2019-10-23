package com.common.rxmvvm

import com.common.core.BaseApplication
import com.common.rxmvvm.di.DaggerAppComponent

class App: BaseApplication() {


    override fun injectDaggerApp() = DaggerAppComponent.factory().create(this).inject(this)

    override fun onCreate() {
        super.onCreate()

    }

}