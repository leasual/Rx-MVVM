package com.common.rxmvvm.splash

import com.common.rxmvvm.base.BaseViewModel
import com.common.core.extensions.disposedBag
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit


class SplashViewModel: BaseViewModel() {

    data class Inputs (
        var userName: String
    )

    data class Outputs  (
        var startToLogin: Observable<Boolean>
    )

    fun transforms(inputs: Inputs): Outputs {
        val startToLogin = PublishSubject.create<Boolean>()
        Flowable.create<Int>({
            it.onNext(1)
        }, BackpressureStrategy.BUFFER)
            .delay(1, TimeUnit.SECONDS)
            .subscribe {
                startToLogin.onNext(true)
            }
            .disposedBag(dispose)

        return Outputs(startToLogin)
    }
}