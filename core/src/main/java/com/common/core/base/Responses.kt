package com.common.core.base

import com.google.gson.annotations.SerializedName


data class TodayResp(
    @SerializedName("Android") var androidList: List<FeedData>,
    @SerializedName("App") var appList: List<FeedData>,
    @SerializedName("iOS") var iOSList: List<FeedData>,
    @SerializedName("前端") var frontList: List<FeedData>
)

data class FeedData(
    @SerializedName("_id") var id: String,
    @SerializedName("createdAt") var createdAt: String,
    @SerializedName("desc") var desc: String,
    @SerializedName("publishedAt") var publishedAt: String,
    @SerializedName("type") var type: String,
    @SerializedName("url") var url: String,
    @SerializedName("who") var who: String
)