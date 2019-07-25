package com.wesoft.rxmvvm

import com.wesoft.mvvm.main.R
import com.wesoft.rxmvvm.base.BaseActivity
import com.wesoft.rxmvvm.main.MainViewModel

//private val loadMainModule by lazy { loadKoinModules(mainModule) }

class MainActivity : BaseActivity<MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

//    override fun initKoinModule() = loadMainModule

    override fun bindingViews() {

    }
}
