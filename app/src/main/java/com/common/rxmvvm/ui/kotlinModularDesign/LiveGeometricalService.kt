package com.common.rxmvvm.ui.kotlinModularDesign

import java.lang.Math.PI
import kotlin.math.pow

interface LiveGeometricalService<ENV> :
    HasGeometricalService<ENV> {

    override fun ENV.circleArea(radius: Double) =
        PI * powerOfTwo(radius)
    override fun ENV.rectangleArea(height: Double, width: Double) =
        height * width
    fun powerOfTwo(n: Double) =
        n.pow(2)

}