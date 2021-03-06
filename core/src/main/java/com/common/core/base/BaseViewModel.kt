package com.common.rxmvvm.base

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel: ViewModel() {
    protected val dispose: CompositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        if (!dispose.isDisposed) {
            dispose.dispose()
            dispose.clear()
        }
    }
}