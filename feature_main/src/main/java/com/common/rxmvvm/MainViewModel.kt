package com.common.rxmvvm

import android.util.Log
import com.common.core.base.BaseViewModel
import com.common.core.models.FeedData
import com.common.core.extensions.disposedBag
import com.common.rxmvvm.repository.MainRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainViewModel(private val repository: MainRepository): BaseViewModel() {

    data class Inputs(
        var refresh: Observable<Unit>
    )
    data class Outputs(
        var feedList: PublishSubject<List<FeedData>>
    )

    fun transforms(inputs: Inputs): Outputs {
        val feedList = PublishSubject.create<List<FeedData>>()
        inputs.refresh
            .startWith(Unit)
            .flatMap { repository.getTodayList().toObservable() }
            .subscribe(
                { feedList.onNext(it.data.androidList) },
                { Log.e("test", "error= ${it.message}") })
            .disposedBag(dispose)
        return Outputs(feedList)
    }
}