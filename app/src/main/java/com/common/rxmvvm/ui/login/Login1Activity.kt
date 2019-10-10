package com.common.rxmvvm.ui.login

import android.widget.Toast
import androidx.lifecycle.Observer
import com.common.core.base.BaseActivity
import com.common.core.extensions.disposedBag
import com.common.core.extensions.startTo
import com.common.rxmvvm.Activities
import com.common.rxmvvm.R
import com.jakewharton.rxbinding3.view.clicks
import com.jakewharton.rxbinding3.widget.textChanges
import kotlinx.android.synthetic.main.activity_login.*


class Login1Activity : BaseActivity<Login1ViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_login


    override fun setupViews() {

    }

    override fun bindingViews() {
        val output = viewModel.transforms(
            Login1ViewModel.Inputs(
                etUser.textChanges().map { it.toString() },
                etPassword.textChanges().map { it.toString() },
                btLogin.clicks()
            )
        )

        output.description.observe(this, Observer {
            tvShow.text = it
        })

        output.loginSuccess.observe(this, Observer {
            gotoMain()
        })
    }

    private fun gotoMain() {
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
        startTo(Activities.Main, finish = true){}
    }
}
