package com.common.rxmvvm

import com.common.mvvm.main.R
import com.common.rxmvvm.base.BaseActivity
import com.common.rxmvvm.main.MainViewModel

//private val loadMainModule by lazy { loadKoinModules(mainModule) }

class MainActivity : BaseActivity<MainViewModel>() {

    override fun getLayoutId(): Int = R.layout.activity_main

//    override fun initKoinModule() = loadMainModule

    override fun bindingViews() {

    }
}
