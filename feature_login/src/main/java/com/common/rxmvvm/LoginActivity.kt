package com.common.rxmvvm

import android.widget.Toast
import com.common.core.base.BaseActivity
import com.common.core.extensions.disposedBag
import com.common.rxmvvm.di.loginModule
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import com.wesoft.mvvm.login.R
import kotlinx.android.synthetic.main.activity_login.*

import org.koin.core.context.loadKoinModules

private val loadLoginModule by lazy { loadKoinModules(loginModule) }

class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_login

    override fun initKoinModule() = loadLoginModule

    override fun setupViews() {

    }

    override fun bindingViews() {
        val output = viewModel.transforms(
            LoginViewModel.Inputs(
                et_user.textChanges().map { it.toString() },
                et_password.textChanges().map { it.toString() },
                bt_login.clicks()
            )
        )

        output.description.subscribe  {
            tv_show.text = it
        }.disposedBag(dispose)

        output.loginSuccess.subscribe {
            gotoMain()
        }.disposedBag(dispose)
    }

    private fun gotoMain() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
        startTo(this, Activities.Main){}
        this.finish()
    }
}
