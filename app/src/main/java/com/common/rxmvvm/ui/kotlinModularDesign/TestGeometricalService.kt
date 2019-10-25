package com.common.rxmvvm.ui.kotlinModularDesign

interface TestGeometricalService<ENV> :
    HasGeometricalService<ENV> {

    override fun ENV.circleArea(radius: Double): Double = 5.0
    override fun ENV.rectangleArea(height: Double, width: Double): Double = 8.0
}