package com.common.rxmvvm.ui.main

import com.common.core.base.BaseActivity
import com.common.core.base.CommonAdapter
import com.common.core.extensions.disposedBag
import com.common.core.extensions.startTo
import com.common.rxmvvm.Activities
import com.common.rxmvvm.R
import com.common.rxmvvm.models.FeedData
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listitem_feed.view.*
import kotlinx.android.synthetic.main.listitem_title.view.*


class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var adapter: CommonAdapter

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun setupViews() {
        initFeedList()
    }

    override fun bindingViews() {
        val outputs = viewModel.transforms(MainViewModel.Inputs(srlSwipe.refreshes()))
        outputs.feedList.subscribe{
            adapter.dataList = it
            srlSwipe.isRefreshing = false
        }.disposedBag(dispose)
    }

    private fun initFeedList() {
        adapter = multiItemAdapter
        rvFeed.adapter = adapter
        srlSwipe.isRefreshing = true
    }

    //多个
    private val multiItemAdapter = CommonAdapter(arrayOf(R.layout.listitem_title, R.layout.listitem_feed),
        arrayOf(String::class.java, FeedData::class.java), { itemView, model, _ ->
            when (model) {
                is FeedData -> {
                    itemView.tvDesc.text = model.desc
                    itemView.tvType.text = model.type
                    itemView.tvDate.text = model.publishedAt
                }
                is String -> itemView.tvTitle.text = model
            }
        }, { model, _ ->
            when (model) {
                is FeedData -> gotoDetail(model.url)
            }
        })

    private fun gotoDetail(url: String) {
        startTo(Activities.WebView) {
            putExtra("url", url)
        }
    }
}
