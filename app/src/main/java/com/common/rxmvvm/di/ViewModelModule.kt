package com.common.rxmvvm.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.common.core.di.DaggerViewModelFactory
import com.common.core.di.ViewModelKey
import com.common.rxmvvm.ui.login.Login1ViewModel
import com.common.rxmvvm.ui.main.MainViewModel
import com.common.rxmvvm.ui.main.SmartRefreshViewModel
import com.common.rxmvvm.ui.main.WebViewModel
import com.common.rxmvvm.ui.splash.SplashViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    abstract fun bindsSplashViewModel(viewModel: SplashViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(Login1ViewModel::class)
    abstract fun bindsLogin1ViewModel(viewModel: Login1ViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindsMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(WebViewModel::class)
    abstract fun bindsWebViewModel(viewModel: WebViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SmartRefreshViewModel::class)
    abstract fun bindsSmartRefreshViewModel(viewModel: SmartRefreshViewModel): ViewModel


}