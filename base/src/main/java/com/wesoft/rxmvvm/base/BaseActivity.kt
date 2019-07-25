package com.wesoft.rxmvvm.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.disposables.CompositeDisposable
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.reflect.ParameterizedType
import kotlin.reflect.KClass

abstract class BaseActivity<VM: BaseViewModel>: AppCompatActivity() {
    protected val dispose: CompositeDisposable = CompositeDisposable()

    protected val viewModel: VM by viewModel(viewModelClass())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        initKoinModule()
        setContentView(getLayoutId())
        setupViews()
        bindingViews()
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun setupViews()

    abstract fun bindingViews()

//    abstract fun initKoinModule()

    override fun onDestroy() {
        super.onDestroy()
        if (!dispose.isDisposed) {
            dispose.dispose()
            dispose.clear()
        }
    }
    @Suppress("UNCHECKED_CAST")
    private fun viewModelClass(): KClass<VM> {
        // dirty hack to get generic type https://stackoverflow.com/a/1901275/719212
        return ((javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<VM>).kotlin
    }

}