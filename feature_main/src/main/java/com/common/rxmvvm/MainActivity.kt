package com.common.rxmvvm

import com.common.core.base.BaseActivity
import com.common.core.base.CommonAdapter
import com.common.core.base.SingleItemAdapter
import com.common.core.extensions.disposedBag
import com.common.core.models.FeedData
import com.common.rxmvvm.di.mainModule
import com.jakewharton.rxbinding3.swiperefreshlayout.refreshes
import com.wesoft.mvvm.main.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.listitem_feed.view.*
import kotlinx.android.synthetic.main.listitem_title.view.*
import org.koin.core.context.loadKoinModules

private val loadMainModule by lazy { loadKoinModules(mainModule) }

class MainActivity : BaseActivity<MainViewModel>() {

    private lateinit var adapter: CommonAdapter

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initKoinModule() = loadMainModule

    override fun setupViews() {
        initFeedList()
    }

    override fun bindingViews() {
        val outputs = viewModel.transforms(MainViewModel.Inputs(srl_swipe.refreshes()))
        outputs.feedList.subscribe{
            adapter.dataList = it
            srl_swipe.isRefreshing = false
        }.disposedBag(dispose)
    }

    private fun initFeedList() {
        adapter = multiItem2Adapter2
        rv_feed.adapter = adapter
        srl_swipe.isRefreshing = true
    }

    private val singleAdapter = SingleItemAdapter<FeedData>(R.layout.listitem_feed, {
            itemView, model, _ ->
        itemView.tv_desc.text = model.desc
        itemView.tv_type.text = model.type
        itemView.tv_date.text = model.publishedAt
    }, {
            model, _ ->
        gotoDetail(model.url)
    }, {
            old, new ->
        old.id == new.id
    })

    //多个
    private val multiItemAdapter = CommonAdapter(arrayOf(R.layout.listitem_title, R.layout.listitem_feed),
        arrayOf(String::class.java, FeedData::class.java), { itemView, model, _ ->
            when (model) {
                is FeedData -> {
                    itemView.tv_desc.text = model.desc
                    itemView.tv_type.text = model.type
                    itemView.tv_date.text = model.publishedAt
                }
                is String -> itemView.tv_title.text = model
            }
        }, { model, _ ->
            when (model) {
                is FeedData -> gotoDetail(model.url)
            }
        })

    //单个
    private val multiItem2Adapter2 = CommonAdapter(arrayOf(R.layout.listitem_feed),
        arrayOf(FeedData::class.java), { itemView, model, _ ->
            when (model) {
                is FeedData -> {
                    itemView.tv_desc.text = model.desc
                    itemView.tv_type.text = model.type
                    itemView.tv_date.text = model.publishedAt
                }
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
