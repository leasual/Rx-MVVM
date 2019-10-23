package com.common.rxmvvm.ui.main

import com.common.core.base.BaseRefreshViewModel
import com.common.core.base.BaseResponse
import com.common.core.extensions.asListAnyFlowable
import com.common.rxmvvm.repository.MainRepository
import io.reactivex.Flowable
import javax.inject.Inject

class SmartRefreshViewModel@Inject constructor(): BaseRefreshViewModel<MainRepository>() {

    init {
        totalPages = 2
    }

    override fun getDataApi(): Flowable<Result<BaseResponse<List<Any>>>> {
        return repository.getCategoryList("Android", currentPage).asListAnyFlowable()
    }
}