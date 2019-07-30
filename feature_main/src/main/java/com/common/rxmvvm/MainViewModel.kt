package com.common.rxmvvm

import android.util.Log
import com.common.core.base.BaseViewModel
import com.common.core.extensions.disposedBag
import com.common.rxmvvm.repository.MainRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class MainViewModel(private val repository: MainRepository): BaseViewModel() {

    data class Inputs(
        var refresh: Observable<Unit>
    )
    data class Outputs(
        var feedList: PublishSubject<MutableList<Any>>
    )

    fun transforms(inputs: Inputs): Outputs {
        val feedList = PublishSubject.create<MutableList<Any>>()
        val dataList = arrayListOf<Any>()
        inputs.refresh
            .startWith(Unit)
            .flatMap { repository.getTodayList().toObservable() }
            .subscribe(
                {
                    dataList.clear()
                    dataList.add("Android")
                    dataList.addAll(it.data.androidList)
                    dataList.add("iOS")
                    dataList.addAll(it.data.iOSList)
                    dataList.add("前端")
                    dataList.addAll(it.data.frontList)
                    dataList.add("App")
                    dataList.addAll(it.data.appList)
                    feedList.onNext(dataList)
                },
                { Log.e("test", "error= ${it.message}") })
            .disposedBag(dispose)
        return Outputs(feedList)
    }
}