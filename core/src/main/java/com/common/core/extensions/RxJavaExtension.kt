package com.common.core.extensions

import androidx.annotation.NonNull
import androidx.lifecycle.MediatorLiveData
import com.common.core.base.BaseResponse
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

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

fun <T> Consumer<T>.asObserver(): Observer<T> = object : Observer<T> {

    override fun onComplete() {
    }

    override fun onSubscribe(d: Disposable) {
    }

    override fun onNext(t: T) {
        accept(t)
    }

    override fun onError(e: Throwable) {
    }
}

@Suppress("UNCHECKED_CAST")
fun <S, T> takeWhen(@NonNull whenObservable: Observable<T>): ObservableTransformer<S, T> {
    return TakeWhenTransformer<S, T>(whenObservable) as ObservableTransformer<S, T>
}

class TakeWhenTransformer<S, T>(private val whenObservable: Observable<T>) :
    ObservableTransformer<S, S> {
    override fun apply(upstream: Observable<S>): ObservableSource<S> {
        return this.whenObservable.withLatestFrom(upstream, BiFunction { _, s -> s })
    }
}

@Suppress("UNCHECKED_CAST")
fun <T: BaseResponse<D>, D> Flowable<T>.asBaseRespFlowable(): Flowable<BaseResponse<D>> {
    return this as (Flowable<BaseResponse<D>>)
}

@Suppress("UNCHECKED_CAST")
fun <T: BaseResponse<D>, D> Flowable<Result<T>>.asListAnyFlowable(): Flowable<Result<BaseResponse<List<Any>>>> {
    return this as Flowable<Result<BaseResponse<List<Any>>>>
}