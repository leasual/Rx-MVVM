package com.common.rxmvvm.ui.kotlinModularDesign

interface LiveTotalAreaService<ENV> :
    HasTotalAreaService<ENV> where ENV : HasGeometricalService<ENV> {

    override fun ENV.totalArea(radius: Double, width: Double, height: Double): Double {
        val c = circleArea(radius)
        val r = rectangleArea(height, width)
        return c + r
    }
}