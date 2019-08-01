package com.common.rxmvvm.repository

import android.app.Application
import com.common.core.base.BaseResponse
import com.common.core.extensions.asBaseRespFlowable
import com.common.core.vo.NetworkBoundResource
import com.common.rxmvvm.api.APIService
import com.common.rxmvvm.models.TodayResp
import io.reactivex.Flowable

class LoginRepository(private val apiService: APIService, private val app: Application) {


    fun getTodayList(): Flowable<Result<BaseResponse<TodayResp>>> {
        return object : NetworkBoundResource<TodayResp, TodayResp>(app) {
            override fun shouldFetch(data: TodayResp?): Boolean = true

            override fun shouldLoadFromCache(): Boolean = false

            override fun loadFromDB(): TodayResp? = null

            override fun cache(data: TodayResp) {

            }

            override fun callApi(): Flowable<BaseResponse<TodayResp>> = apiService.getTodayList().asBaseRespFlowable()

        }.asFlowable()
    }
}