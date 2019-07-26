package com.common.rxmvvm

import com.common.core.repository.MainRepository
import com.common.rxmvvm.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {
    factory { MainRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
}