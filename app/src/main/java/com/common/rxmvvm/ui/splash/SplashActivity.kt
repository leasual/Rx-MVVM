package com.common.rxmvvm.ui.splash

import com.common.core.base.BaseActivity
import com.common.core.extensions.disposedBag
import com.common.core.extensions.startTo
import com.common.rxmvvm.Activities
import com.common.rxmvvm.R

class SplashActivity : BaseActivity<SplashViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_splash

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
