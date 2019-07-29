package com.common.rxmvvm

import com.common.core.base.BaseActivity
import com.common.core.base.MultiItem2Adapter
import com.common.core.base.MultiItemAdapter
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

    private lateinit var adapter: MultiItemAdapter

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
        adapter = multiAdapter
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

    private val multiAdapter = MultiItemAdapter({
        when (it) {
            is FeedData -> R.layout.listitem_feed
            is String -> R.layout.listitem_title
            else -> R.layout.listitem_feed
        }
    }, { itemView, model, _ ->
        if (model is FeedData) {
            itemView.tv_desc.text = model.desc
            itemView.tv_type.text = model.type
            itemView.tv_date.text = model.publishedAt
        }else if (model is String) {
            itemView.tv_title.text = model
        }
    }, { model, _ ->
        if (model is FeedData) {
            gotoDetail(model.url)
        }
    })

    private val multiItem2Adapter = MultiItem2Adapter(arrayListOf(R.layout.listitem_title, R.layout.listitem_feed),
        arrayListOf(String::class, FeedData::class), { itemView, model, _ ->
            if (model is FeedData) {
                itemView.tv_desc.text = model.desc
                itemView.tv_type.text = model.type
                itemView.tv_date.text = model.publishedAt
            }else if (model is String) {
                itemView.tv_title.text = model
            }
        }, { model, _ ->
            if (model is FeedData) {
                gotoDetail(model.url)
            }
        })

    private fun gotoDetail(url: String) {
        startTo(Activities.WebView) {
            putExtra("url", url)
        }
    }
}
