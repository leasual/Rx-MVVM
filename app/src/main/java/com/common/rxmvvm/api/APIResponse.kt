package com.common.rxmvvm.api

import com.common.core.base.BaseResponse
import com.google.gson.annotations.SerializedName

data class APIResponse<T>(
    @SerializedName("error") override var code: Any,
    @SerializedName("results") override var data: T
): BaseResponse<T>(code, data)