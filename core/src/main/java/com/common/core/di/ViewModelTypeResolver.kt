package com.common.core.di

import java.lang.reflect.ParameterizedType

/**
 * Created by james on 2018/8/24.
 */

object ViewModelTypeResolver {


    inline fun <reified T> findViewModelType(cls: Class<*>): Class<out T>? {
        var parameterizedType: ParameterizedType? = null

        if (cls.genericSuperclass is ParameterizedType) {
            parameterizedType = cls.genericSuperclass as ParameterizedType
        }

        if (parameterizedType == null) {
            return null
        }

        parameterizedType.actualTypeArguments
                .filter {
                    it is Class<*>
                            && it !== T::class.java
                            && T::class.java.isAssignableFrom(it)
                }.forEach {
                    @Suppress("UNCHECKED_CAST")
                    return it as Class<out T>
                }

        return null
    }

}