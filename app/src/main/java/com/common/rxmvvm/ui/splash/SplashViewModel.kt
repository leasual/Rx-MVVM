package com.common.rxmvvm.ui.splash

import com.common.core.base.BaseViewModel
import com.common.core.extensions.disposedBag
import com.common.rxmvvm.repository.MainRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SplashViewModel@Inject constructor(): BaseViewModel<MainRepository>() {

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