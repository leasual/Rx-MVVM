package com.common.rxmvvm.repository

import android.app.Application
import com.common.core.base.BaseResponse
import com.common.core.extensions.asBaseRespFlowable
import com.common.core.vo.NetworkBoundResource
import com.common.rxmvvm.api.APIService
import com.common.rxmvvm.models.FeedData
import com.common.rxmvvm.models.TodayResp
import io.reactivex.Flowable

class MainRepository(private val apiService: APIService, private val app: Application) {


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

    fun getCategoryList(category: String, page: Int): Flowable<Result<BaseResponse<List<FeedData>>>> {
        return object : NetworkBoundResource<List<FeedData>, List<FeedData>>(app) {
            override fun shouldFetch(data: List<FeedData>?): Boolean = true

            override fun shouldLoadFromCache(): Boolean = false

            override fun loadFromDB(): List<FeedData>? = null

            override fun cache(data: List<FeedData>) {

            }

            override fun callApi(): Flowable<BaseResponse<List<FeedData>>> = apiService.getCategoryList(category, page).asBaseRespFlowable()

        }.asFlowable()
    }
}