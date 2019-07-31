package com.common.core.api

import com.common.core.base.BaseResponse
import com.common.core.models.FeedData
import com.common.core.models.TodayResp
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("today")
    fun getTodayList(): Flowable<BaseResponse<TodayResp>>

    @GET("data/{category}/10/{page}")
    fun getCategoryList(@Path("category") category: String, @Path("page") page: Int): Flowable<BaseResponse<List<FeedData>>>
}