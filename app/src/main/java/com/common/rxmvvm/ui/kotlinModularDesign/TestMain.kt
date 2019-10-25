package com.common.rxmvvm.ui.kotlinModularDesign

import android.util.Log

class TestMain {

    fun main() {
        //通过模块化思想，达到快速切换环境的过程，如TestGeometricalService替换为LiveGeometricalService,
        //开发当中运用得当，可以在测试和生产环境快速切换

        //1:测试环境
        val testEnvironment: MyEnvironment = object : MyEnvironment,
            HasTotalAreaService<MyEnvironment> by object :
                LiveTotalAreaService<MyEnvironment> {},
            HasGeometricalService<MyEnvironment> by object :
                TestGeometricalService<MyEnvironment> {}{}
        //2:生产环境
//        val testEnvironment: MyEnvironment = object : MyEnvironment,
//            HasTotalAreaService<MyEnvironment> by object :
//                LiveTotalAreaService<MyEnvironment> {},
//            HasGeometricalService<MyEnvironment> by object :
//                LiveGeometricalService<MyEnvironment> {}{}

        testEnvironment.run {
            val total = totalArea(4.0, 5.0, 6.0)
           println("testModule total= $total")
        }
    }
}