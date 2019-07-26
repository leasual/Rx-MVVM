package com.common.rxmvvm

import android.util.Log
import com.common.rxmvvm.base.BaseViewModel
import com.common.rxmvvm.base.extension.disposedBag
import com.common.rxmvvm.base.extension.takeWhen
import com.common.core.repository.LoginRepository
import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import io.reactivex.subjects.PublishSubject

class LoginViewModel(private val repository: LoginRepository): BaseViewModel() {

    data class Inputs (
        var userName: Observable<String>,
        var password: Observable<String>,
        var login: Observable<Any>
    )

    data class Outputs  (
        var description: Observable<String>,
        var loginSuccess: Observable<Boolean>
    )

    fun transforms(inputs: Inputs): Outputs {

        val description = PublishSubject.create<String>()
        val loginSuccess = PublishSubject.create<Boolean>()
        var error = ""

        val userName = inputs.userName.map { it.isNotEmpty() && it.length >= 6 }
        val password = inputs.password.map { it.isNotEmpty() && (it.length in 6..18) }

        val userNamePassword = Observables.combineLatest(userName, password) { user, pwd ->
            error = if (user && !pwd) "请输入正确的密码"
            else if (!user && pwd) "请输入正确的用户名"
            else if (!user && !pwd) "请输入正确的用户名和密码"
            else ""
            user && pwd
        }.distinctUntilChanged()

        userNamePassword
            .compose(takeWhen(inputs.login))
            .subscribe {
                if (it as Boolean) { login(loginSuccess) }
                description.onNext(error)
            }
            .disposedBag(dispose)

        return Outputs(description, loginSuccess)
    }

    private fun login(loginSuccess: PublishSubject<Boolean>) {
        repository.getTodayList()
            .subscribe(
                {
                    Log.d("test", "data= ${it.data.androidList.size}")
                    loginSuccess.onNext(true)
                },
                {
                    Log.e("test", "error= ${it.message}")
                })
            .disposedBag(dispose)
    }
}