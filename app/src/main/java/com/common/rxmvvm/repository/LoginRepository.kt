package com.common.rxmvvm.repository

import com.common.core.base.BaseRepository
import com.common.core.base.BaseResponse
import com.common.core.extensions.asBaseRespFlowable
import com.common.core.vo.NetworkBoundResource
import com.common.rxmvvm.App
import com.common.rxmvvm.api.APIService
import com.common.rxmvvm.models.TodayResp
import io.reactivex.Flowable
import javax.inject.Inject

class LoginRepository@Inject constructor(val app: App, val apiService: APIService): BaseRepository() {


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