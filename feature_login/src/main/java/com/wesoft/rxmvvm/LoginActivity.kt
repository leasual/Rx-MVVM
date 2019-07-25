package com.wesoft.rxmvvm

import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import com.wesoft.mvvm.login.R
import com.wesoft.rxmvvm.base.ActivityLaunchHelper
import com.wesoft.rxmvvm.base.BaseActivity
import com.wesoft.rxmvvm.base.extension.disposedBag
import com.wesoft.rxmvvm.login.LoginViewModel
import kotlinx.android.synthetic.main.activity_login.*

//private val loadLoginModule by lazy { loadKoinModules(loginModule) }

class LoginActivity : BaseActivity<LoginViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_login

//    override fun initKoinModule() = loadLoginModule

    override fun bindingViews() {
        val output = viewModel.transforms(
            LoginViewModel.Inputs(
                RxTextView.textChanges(et_user).map { it.toString() },
                RxTextView.textChanges(et_password).map { it.toString() },
                RxView.clicks(bt_login)
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
        ActivityLaunchHelper.launchMain(this, null, null)
        this.finish()
    }
}
