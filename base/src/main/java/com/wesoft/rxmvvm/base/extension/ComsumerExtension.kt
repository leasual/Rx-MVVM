package com.wesoft.rxmvvm.base.extension

import androidx.annotation.NonNull
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Consumer



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

fun <S, T> takeWhen(@NonNull whenObservable: Observable<T>): ObservableTransformer<S, T> {
    return TakeWhenTransformer<S, T>(whenObservable) as ObservableTransformer<S, T>
}

class TakeWhenTransformer<S, T>(private val whenObservable: Observable<T>) :
    ObservableTransformer<S, S> {
    override fun apply(upstream: Observable<S>): ObservableSource<S> {
        return this.whenObservable.withLatestFrom(upstream, BiFunction { _, s -> s })
    }
}