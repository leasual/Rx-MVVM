package com.wesoft.rxmvvm.base.extension

import androidx.lifecycle.MediatorLiveData
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by james.li on 2018/8/23.
 */

fun <T> Single<T>.ioMain(): Single<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.ioMain(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Flowable<T>.ioMain(): Flowable<T> {
    return this.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.updateLoading(loading: MediatorLiveData<Boolean>): Single<T> {
    return this.doOnSubscribe { loading.value = true }
            .doFinally { loading.value = false }
}

fun <T> Observable<T>.updateLoading(loading: MediatorLiveData<Boolean>): Observable<T> {
    return this.doOnSubscribe { loading.value = true }
            .doFinally { loading.value = false }
}

fun <T> Flowable<T>.updateLoading(loading: MediatorLiveData<Boolean>): Flowable<T> {
    return this.doOnSubscribe { loading.value = true }
            .doFinally { loading.value = false }
}

fun Disposable.disposedBag(dispose: CompositeDisposable) {
    dispose.add(this)
}

class RetryWithDelay(private val maxRetries: Int, private var delay: Long = 0, private val delayAmount: Long = 100)
    : Function<Flowable<out Throwable>, Flowable<*>> {

    private var retryCount = 0
    override fun apply(t: Flowable<out Throwable>): Flowable<*> {
        return t.flatMap {
            if (++retryCount < maxRetries) {
                delay += delayAmount
                Flowable.timer(delay, TimeUnit.MILLISECONDS)
            } else {
                Flowable.error(it)
            }
        }
    }
}

/**
 * 无限重试
 */
class RetryWithAlways(private val delay: Long = 2000) : Function<Flowable<out Throwable>, Flowable<*>> {
    override fun apply(t: Flowable<out Throwable>): Flowable<*> {
        return t.flatMap {
            Flowable.timer(delay, TimeUnit.MILLISECONDS)
        }
    }
}
