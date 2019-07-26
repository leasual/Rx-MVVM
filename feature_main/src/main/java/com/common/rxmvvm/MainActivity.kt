package com.common.rxmvvm

import com.common.core.base.BaseActivity
import com.common.core.extensions.disposedBag
import com.common.rxmvvm.di.mainModule
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.wesoft.mvvm.main.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.core.context.loadKoinModules

private val loadMainModule by lazy { loadKoinModules(mainModule) }

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var adapter: FeedAdapter

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initKoinModule() = loadMainModule

    override fun setupViews() {
        initFeedList()
    }

    override fun bindingViews() {
        val outputs = viewModel.transforms(MainViewModel.Inputs(srl_swipe.refreshes()))
        outputs.feedList.subscribe{
            adapter.feedList = it
            srl_swipe.isRefreshing = false
        }.disposedBag(dispose)
    }

    private fun initFeedList() {
        adapter = FeedAdapter { gotoDetail(it.url) }
        rv_feed.adapter = adapter
        srl_swipe.isRefreshing = true
    }

    private fun gotoDetail(url: String) {
        startTo(this, Activities.WebView) {
            putExtra("url", url)
        }
        this.finish()
    }
}
