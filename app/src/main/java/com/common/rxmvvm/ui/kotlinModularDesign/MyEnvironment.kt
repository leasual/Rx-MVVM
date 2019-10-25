package com.common.rxmvvm.ui.kotlinModularDesign

interface MyEnvironment :
    HasTotalAreaService<MyEnvironment>,
    HasGeometricalService<MyEnvironment>