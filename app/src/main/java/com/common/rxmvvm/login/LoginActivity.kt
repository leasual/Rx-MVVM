package com.common.rxmvvm.login

import android.widget.Toast
import com.common.core.extensions.disposedBag
import com.common.core.extensions.startActivity
import com.common.rxmvvm.R
import com.common.rxmvvm.base.BaseActivity
import com.common.rxmvvm.main.MainActivity
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_login.*

//private val loadLoginModule by lazy { loadKoinModules(loginModule) }

class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_login

//    override fun initKoinModule() = loadLoginModule

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
        startActivity<MainActivity> {  }
        this.finish()
    }
}
