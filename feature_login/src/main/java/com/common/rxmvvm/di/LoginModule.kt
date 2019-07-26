package com.common.rxmvvm.di

import com.common.rxmvvm.LoginViewModel
import com.common.rxmvvm.repository.LoginRepository
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    factory { LoginRepository(get(), get()) }
    viewModel { LoginViewModel(get()) }
}