package com.wesoft.rxmvvm.main

import android.util.Log
import com.wesoft.rxmvvm.base.BaseViewModel
import com.wesoft.rxmvvm.base.extension.disposedBag
import com.wesoft.rxmvvm.data.base.FeedData
import com.wesoft.rxmvvm.data.repository.MainRepository
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