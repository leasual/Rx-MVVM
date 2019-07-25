package com.wesoft.rxmvvm

import com.wesoft.rxmvvm.data.repository.MainRepository
import com.wesoft.rxmvvm.main.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {
    factory { MainRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
}