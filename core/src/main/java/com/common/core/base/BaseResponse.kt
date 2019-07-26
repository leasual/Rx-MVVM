package com.common.core.base

import com.google.gson.annotations.SerializedName

data class BaseResponse<T>(
    @SerializedName("error") var code: Boolean,
    @SerializedName("results") var data: T
)