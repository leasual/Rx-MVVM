package com.wesoft.rxmvvm.data.repository

import android.app.Application
import com.wesoft.rxmvvm.data.api.APIService
import com.wesoft.rxmvvm.data.base.BaseResponse
import com.wesoft.rxmvvm.data.base.TodayResp
import com.wesoft.rxmvvm.data.vo.NetworkBoundResource
import io.reactivex.Flowable

class MainRepository(private val apiService: APIService, private val app: Application) {


    fun getTodayList(): Flowable<BaseResponse<TodayResp>> {
        return object : NetworkBoundResource<TodayResp, TodayResp>(app) {
            override fun shouldFetch(data: TodayResp?): Boolean = true

            override fun shouldLoadFromCache(): Boolean = false

            override fun loadFromDB(): TodayResp? = null

            override fun cache(data: TodayResp) {

            }

            override fun callApi(): Flowable<BaseResponse<TodayResp>> = apiService.getTodayList()

        }.asFlowable()
    }
}