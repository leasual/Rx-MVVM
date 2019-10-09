package com.common.rxmvvm.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.common.core.base.BaseRefreshActivity
import com.common.core.base.CommonAdapter
import com.common.core.extensions.startTo
import com.common.rxmvvm.Activities
import com.common.rxmvvm.R
import com.common.rxmvvm.models.FeedData
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_smart_refresh.*
import kotlinx.android.synthetic.main.listitem_feed.view.*

class SmartRefreshActivity : BaseRefreshActivity<SmartRefreshViewModel>() {

    override fun bindRefreshLayout(): SmartRefreshLayout = srlRefresh

    override fun bindRecyclerViewLayout(): RecyclerView = rvFeed

    override fun getLayoutId(): Int = R.layout.activity_smart_refresh


    override fun setupViews() {
        super.setupViews()
        initRecyclerView()
    }

    override fun bindingViews() {
        super.bindingViews()
    }

    private fun initRecyclerView() {
        rvFeed.adapter = feedListAdapter
        //rvFeed.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private val feedListAdapter = CommonAdapter(arrayOf(R.layout.listitem_feed), arrayOf(FeedData::class.java),
        { itemView, model, _ ->
            when (model) {
                is FeedData -> {
                    itemView.tvDesc.text = model.desc
                    itemView.tvType.text = model.type
                    itemView.tvDate.text = model.publishedAt
                }
            }
        }, { model, position ->
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
