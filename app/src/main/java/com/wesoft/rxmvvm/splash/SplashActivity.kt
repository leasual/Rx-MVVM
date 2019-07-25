package com.wesoft.rxmvvm.splash

import com.wesoft.rxmvvm.R
import com.wesoft.rxmvvm.base.BaseActivity
import com.wesoft.rxmvvm.base.extension.disposedBag
import com.wesoft.rxmvvm.base.extension.startActivity
import com.wesoft.rxmvvm.login.LoginActivity

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
