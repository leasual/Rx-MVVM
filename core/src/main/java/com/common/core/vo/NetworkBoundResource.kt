package com.common.core.vo

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.common.core.api.APIException
import com.common.core.base.BaseResponse
import com.common.core.extensions.RetryWithDelay
import com.common.core.extensions.ioMain
import com.common.core.extensions.isNetworkAvailable
import com.common.core.extensions.toast
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.internal.operators.flowable.FlowableCreate
import retrofit2.HttpException

/**
 * Created by james on 2018/8/22.
 */

/**
 * 网络帮助类
 * @param ResultType 需要返回的数据类型
 * @param RequestType 网络请求回来的数据类型
 */
abstract class NetworkBoundResource<ResultType, RequestType>(val app: Application) {

    private val flowableOnSubscribe: FlowableOnSubscribe<BaseResponse<ResultType>>

    init {

        //whether should fetch from network
        val networkState = app.applicationContext.isNetworkAvailable()
        @Suppress("LeakingThis")
        when (networkState) {
            true -> {
                flowableOnSubscribe = if (shouldLoadFromCache()) {
                    FlowableOnSubscribe { emitter ->
                        Log.d("test", "get data from cache")
                        val cacheData = loadFromDB()
                        cacheData?.let { emitter.onNext(BaseResponse(false, cacheData)) }
                        if (shouldFetch(cacheData)) {
                            fetchFromNetwork(emitter)
                        } else {
                            emitter.onNext(BaseResponse(false, Any() as ResultType))
                        }
                    }
                } else {
                    FlowableOnSubscribe { emitter ->
                        fetchFromNetwork(emitter)
                    }
                }
            }
            else -> {
                flowableOnSubscribe = if (shouldLoadFromCache()) {
                    FlowableOnSubscribe { emitter ->
                        val cacheData = loadFromDB()
                        cacheData?.let { emitter.onNext(BaseResponse(false, cacheData)) }
                            ?: emitter.onNext(BaseResponse(false, Any() as ResultType))
                    }
                } else {
                    FlowableOnSubscribe { emitter ->
                        emitter.onComplete()
                    }
                }
                app.applicationContext.toast("网络未连接，请检查网络再重试")
            }
        }
    }

    @SuppressLint("CheckResult")
    @Suppress("UNCHECKED_CAST")
    private fun fetchFromNetwork(emitter: FlowableEmitter<BaseResponse<ResultType>>) {
        callApi()
            // .flatMap { (it as BaseResponse<ResultType>).filterData(isFilterSpace, isRegister) }
            .retryWhen(RetryWithDelay(3))
            .flatMap {
                val data = it
                if (!data.code) {
                    data.data?.let {
                        cache(data.data)
                    }
                }
                Flowable.just(it)
            }
            .ioMain()
            .subscribe(
                { data ->
                    /**
                     *  这里请注意，Rxjava 2.x之后不支持发送null的数据，
                     *  所以当data.data也就是返回的数据为空时，需要判定，如果为空
                     *  这是我们需要给他赋予一个新的值，才能发送。并且需要在APIService中定义返回类型为Any
                     */
                    if (!data.code) {
                        data.data?.let {
                            emitter.onNext(data)
                        }
                            ?: emitter.onNext(BaseResponse(false, Any() as ResultType))
                        Log.d("test", "get data from network")
                    } else {
                        emitter.onError(APIException("", -1))
                    }
                },
                { throwable ->
                    when (throwable) {
                        is HttpException -> {

                            if (throwable.response().code() == 404) {
                                app.applicationContext.toast("服务器地址不存在")
                            } else {
                                app.applicationContext.toast("网络不给力，请重试")
                            }
                        }
                        is APIException -> {
                            //app.applicationContext.toast(throwable.info)
                        }
                        else -> {
                            //Timber.d("throwable:" + throwable.message)
                            app.applicationContext.toast("数据异常，请重试")
                        }
                    }
                    emitter.onError(throwable)
                },
                {
                    emitter.onComplete()
                }
            )
    }

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun shouldLoadFromCache(): Boolean

    protected abstract fun loadFromDB(): ResultType?

    protected abstract fun cache(data: ResultType)

    protected abstract fun callApi(): Flowable<BaseResponse<ResultType>>

    fun asFlowable(): Flowable<BaseResponse<ResultType>> {
        return FlowableCreate(flowableOnSubscribe, BackpressureStrategy.LATEST)
            .ioMain()
    }
}