package com.common.rxmvvm.splash

import com.common.rxmvvm.R
import com.common.rxmvvm.base.BaseActivity
import com.common.core.extensions.disposedBag
import com.common.core.extensions.startActivity
import com.common.rxmvvm.login.LoginActivity

//private val loadSplashModule by lazy { loadKoinModules(splashModule) }

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

//    override fun initKoinModule() = loadSplashModule

    override fun setupViews() {

    }

    override fun bindingViews() {

        val output = viewModel.transforms(SplashViewModel.Inputs(""))
        output.startToLogin.subscribe {
            startToLogin()
        }.disposedBag(dispose)

    }

    private fun startToLogin() {
        startActivity<LoginActivity> {  }
        this.finish()
    }
}
