package com.common.rxmvvm

import com.common.core.base.BaseRefreshViewModel
import com.common.core.base.BaseResponse
import com.common.rxmvvm.repository.MainRepository
import io.reactivex.Flowable

class SmartRefreshViewModel(private val repository: MainRepository): BaseRefreshViewModel() {

    override fun getDataApi(): Flowable<BaseResponse<List<Any>>> {
        return repository.getCategoryList("Android", currentPage) as  Flowable<BaseResponse<List<Any>>>
    }
}