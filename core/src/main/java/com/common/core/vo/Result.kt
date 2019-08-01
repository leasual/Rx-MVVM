package com.common.core.vo

//sealed class Result<T, E> {
//    data class Success<T, E>(
//        val data: T
//    ): Result<T, E>()
//    data class Error<T, E>(
//        val error: E
//    ): Result<T, E>()
//}

//open class Result<T>( val data: T? = null, val error: Throwable? = null) {
//    companion object {
//        fun <T> fromData( data: T ) : Result<T> {
//            return Result(data, null)
//        }
//
//        fun <T> fromError( error : Throwable ) : Result<T> {
//            return Result(null, error)
//        }
//    }
//
//    fun isError() : Boolean{
//        return error != null
//    }
//
//    fun isSuccess() : Boolean {
//        return data != null
//    }
//
//
//}
//
//fun <T> Observable<T>.toResult() : Observable<Result<T>> {
//    return map { Result.fromData(it) }
//        .onErrorResumeNext(Function {  Observable.just(Result.fromError(it)) })
//}
//
//fun <T> Observable<Result<T>>.onlySuccess() : Observable<T> {
//    return filter { it.isSuccess() }
//        .map { it.data!! }
//}
//
//fun <T> Observable<Result<T>>.onlyError() : Observable<Throwable> {
//    return filter { it.isError() }
//        .map { it.error!! }
//}
//
//fun <T> Observable<Result<T>>.onlyHttpException() : Observable<HttpException> {
//    return filter{ it.isError() && it.error is HttpException}
//        .map { it.error as HttpException }
//}
//
//fun <T> Observable<Result<T>>.onlyHttpException(code: Int) : Observable<HttpException> {
//    return onlyHttpException()
//        .filter { it.code() == code }
//}

//fun <T> Observable<Result<T>>.onlyHttpExceptionExcluding(vararg codes: Int) : Observable<HttpException> {
//    return onlyHttpException()
//        .filter { codes.contains(it.code()) }
//}