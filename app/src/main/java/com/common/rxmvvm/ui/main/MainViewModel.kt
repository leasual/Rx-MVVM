package com.common.rxmvvm.ui.main

import com.common.core.base.BaseViewModel
import com.common.core.extensions.disposedBag
import com.common.rxmvvm.repository.MainRepository
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject

class MainViewModel@Inject constructor(): BaseViewModel<MainRepository>() {

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
            .subscribe {
                if (it.isSuccess && it.getOrNull() != null) {
                    dataList.clear()
                    dataList.add("Android")
                    dataList.addAll(it.getOrNull()!!.data.androidList)
                    dataList.add("iOS")
                    dataList.addAll(it.getOrNull()!!.data.iOSList)
                    dataList.add("前端")
                    dataList.addAll(it.getOrNull()!!.data.frontList)
                    dataList.add("App")
                    dataList.addAll(it.getOrNull()!!.data.appList)
                    feedList.onNext(dataList)
                }else {

                }
            }.disposedBag(dispose)
        return Outputs(feedList)
    }
}