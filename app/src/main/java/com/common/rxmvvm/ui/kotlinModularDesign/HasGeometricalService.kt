package com.common.rxmvvm.ui.kotlinModularDesign

interface HasGeometricalService<ENV> {
    fun ENV.circleArea(radius: Double): Double
    fun ENV.rectangleArea(height: Double, width: Double): Double
}