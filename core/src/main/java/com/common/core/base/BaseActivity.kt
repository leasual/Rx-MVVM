package com.common.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.common.core.di.ViewModelTypeResolver
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

abstract class BaseActivity<VM: BaseViewModel<*>>: AppCompatActivity(), HasAndroidInjector {

    protected val dispose: CompositeDisposable = CompositeDisposable()

    //DI
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any>? = androidInjector

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        val viewModelType = ViewModelTypeResolver.findViewModelType<BaseViewModel<*>>(javaClass)
        if (viewModelType != null) {
            @Suppress("UNCHECKED_CAST")
            viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelType) as VM
        }
        setContentView(getLayoutId())
        setupViews()
        bindingViews()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setupViews()

    abstract fun bindingViews()

    override fun onDestroy() {
        super.onDestroy()
        if (!dispose.isDisposed) {
            dispose.dispose()
            dispose.clear()
        }
    }
}