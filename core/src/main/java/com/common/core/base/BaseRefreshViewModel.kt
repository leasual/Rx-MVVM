package com.common.core.base


import android.util.Log
import com.common.core.extensions.disposedBag
import com.kennyc.view.MultiStateView
import io.reactivex.Flowable
import io.reactivex.subjects.PublishSubject

abstract class BaseRefreshViewModel: BaseViewModel() {

    open val dataListPublishSubject = PublishSubject.create<MutableList<Any>>()
    open val multiStateViewPublishSubject = PublishSubject.create<MultiStateView.ViewState>()
    private val refreshStatePublishSubject = PublishSubject.create<RefreshState>()
    private val refreshPublishSubject = PublishSubject.create<Int>()
    protected var currentPage = 1

    enum class RefreshState {
        REFRESH_SUCCESS,
        LOAD_MORE_SUCCESS,
        NO_MORE_DATA
    }
    data class RefreshInputs(
        val pullToRefreshObservable: PublishSubject<Boolean>,
        val loadMoreObservable: PublishSubject<Boolean>
    )

    data class RefreshOutputs(
        val refreshState: PublishSubject<RefreshState>
    )

    open fun transformsRefresh(inputs: RefreshInputs): RefreshOutputs {
        inputs.pullToRefreshObservable.subscribe {
            Log.d("test", "pullToRefresh onNext")
            currentPage = 1
            refreshPublishSubject.onNext(currentPage)
        }.disposedBag(dispose)
        inputs.loadMoreObservable.subscribe {
            Log.d("test", "loadMore onNext")
            currentPage += 1
            refreshPublishSubject.onNext(currentPage)
        }.disposedBag(dispose)

        val dataList = arrayListOf<Any>()
        refreshPublishSubject
            .flatMap { getDataApi().toObservable() }
            .subscribe{
                Log.d("test", "onNext= currentPage= $currentPage")
                if (it.isSuccess) {
                    Log.d("test", "it.isSuccess()")
                    if (currentPage == 1) {
                        dataList.clear()
                        if (it.getOrNull()?.data != null) {
                            dataList.addAll(it.getOrNull()!!.data)
                        }
                        dataListPublishSubject.onNext(dataList)
                        refreshStatePublishSubject.onNext(RefreshState.REFRESH_SUCCESS)
                        if (it.getOrNull()?.data == null || it.getOrNull()!!.data.isEmpty()) {
                            multiStateViewPublishSubject.onNext(MultiStateView.ViewState.EMPTY)
                        } else {
                            multiStateViewPublishSubject.onNext(MultiStateView.ViewState.CONTENT)
                        }
                    } else {
                        if (it.getOrNull()?.data != null) {
                            dataList.addAll(it.getOrNull()!!.data)
                        }
                        if (it.getOrNull()?.data == null || it.getOrNull()!!.data.size <= 10) {
                            dataListPublishSubject.onNext(dataList)
                            refreshStatePublishSubject.onNext(RefreshState.NO_MORE_DATA)
                        } else {
                            refreshStatePublishSubject.onNext(RefreshState.LOAD_MORE_SUCCESS)
                        }
                    }
                }else if (it.isFailure) {
                    it.exceptionOrNull()?.printStackTrace()
                    Log.d("test", "it.isError()")
                    if (currentPage == 1) {
                        refreshStatePublishSubject.onNext(RefreshState.REFRESH_SUCCESS)
                    }else {
                        refreshStatePublishSubject.onNext(RefreshState.LOAD_MORE_SUCCESS)
                    }
                    multiStateViewPublishSubject.onNext(MultiStateView.ViewState.ERROR)
                }
            }.disposedBag(dispose)

        return RefreshOutputs(refreshStatePublishSubject)
    }

    abstract fun  getDataApi(): Flowable<Result<BaseResponse<List<Any>>>>
}