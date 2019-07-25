package com.wesoft.rxmvvm.data.api

import com.wesoft.rxmvvm.data.base.BaseResponse
import com.wesoft.rxmvvm.data.base.TodayResp
import io.reactivex.Flowable
import retrofit2.http.GET

interface APIService {

    @GET("today")
    fun getTodayList(): Flowable<BaseResponse<TodayResp>>
}