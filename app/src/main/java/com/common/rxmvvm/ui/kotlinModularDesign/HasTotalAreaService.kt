package com.common.rxmvvm.ui.kotlinModularDesign

interface HasTotalAreaService<ENV> {
    fun ENV.totalArea(radius: Double, width: Double, height: Double): Double
}