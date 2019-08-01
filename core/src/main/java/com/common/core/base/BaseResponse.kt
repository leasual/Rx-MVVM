package com.common.core.base

open class BaseResponse<T>(
    open var code: Any,
    open var data: T
)