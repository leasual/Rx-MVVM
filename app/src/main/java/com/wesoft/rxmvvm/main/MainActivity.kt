package com.wesoft.rxmvvm.main

import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.wesoft.rxmvvm.R
import com.wesoft.rxmvvm.base.BaseActivity
import com.wesoft.rxmvvm.base.extension.disposedBag
import com.wesoft.rxmvvm.base.extension.startActivity
import kotlinx.android.synthetic.main.activity_main.*

//private val loadMainModule by lazy { loadKoinModules(mainModule) }

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var adapter: FeedAdapter

    override fun getLayoutId(): Int = R.layout.activity_main

//    override fun initKoinModule() = loadMainModule

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
        startActivity<WebViewActivity> {
            putExtra("url", url)
        }
    }
}
