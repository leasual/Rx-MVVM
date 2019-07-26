package com.common.rxmvvm.di

import com.common.rxmvvm.repository.MainRepository
import com.common.rxmvvm.MainViewModel
import com.common.rxmvvm.WebViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val mainModule = module {
    factory { MainRepository(get(), get()) }
    viewModel { MainViewModel(get()) }
    viewModel { WebViewModel() }
}