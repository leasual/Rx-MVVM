package com.common.core.base

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseViewModel<R : BaseRepository>: ViewModel() {
    protected val dispose: CompositeDisposable = CompositeDisposable()

    protected var isLoading = MediatorLiveData<Boolean>()

    @Inject
    lateinit var repository: R


    override fun onCleared() {
        super.onCleared()
        if (!dispose.isDisposed) {
            dispose.dispose()
            dispose.clear()
        }
    }
}