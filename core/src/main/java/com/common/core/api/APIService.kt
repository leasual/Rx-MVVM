package com.common.core.api

import com.common.core.base.BaseResponse
import com.common.core.models.TodayResp
import io.reactivex.Flowable
import retrofit2.http.GET

interface APIService {

    @GET("today")
    fun getTodayList(): Flowable<BaseResponse<TodayResp>>
}