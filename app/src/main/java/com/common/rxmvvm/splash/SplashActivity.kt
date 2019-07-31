package com.common.rxmvvm.splash

import com.common.core.base.BaseActivity
import com.common.core.extensions.disposedBag
import com.common.rxmvvm.Activities
import com.common.rxmvvm.R
import com.common.rxmvvm.splashModule
import com.common.rxmvvm.startTo
import org.koin.core.context.loadKoinModules


private val loadSplashModule by lazy { loadKoinModules(splashModule) }

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

    override fun initKoinModule() = loadSplashModule

    override fun setupViews() {

    }

    override fun bindingViews() {

        val output = viewModel.transforms(SplashViewModel.Inputs(""))
        output.startToLogin.subscribe {
            startToLogin()
        }.disposedBag(dispose)

    }

    private fun startToLogin() {
        startTo(Activities.SmartRefresh, finish = true){}
    }
}
