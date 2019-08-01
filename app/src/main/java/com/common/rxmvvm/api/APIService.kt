package com.common.rxmvvm.api

import com.common.rxmvvm.models.FeedData
import com.common.rxmvvm.models.TodayResp
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface APIService {

    @GET("today")
    fun getTodayList(): Flowable<APIResponse<TodayResp>>

    @GET("data/{category}/10/{page}")
    fun getCategoryList(@Path("category") category: String, @Path("page") page: Int): Flowable<APIResponse<List<FeedData>>>
}