package com.common.rxmvvm.ui.main

import androidx.recyclerview.widget.RecyclerView
import com.common.core.base.BaseRefreshActivity
import com.common.core.base.CommonAdapter
import com.common.rxmvvm.R
import com.common.rxmvvm.models.FeedData
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_smart_refresh.*
import kotlinx.android.synthetic.main.listitem_feed.view.*

class SmartRefreshActivity : BaseRefreshActivity<SmartRefreshViewModel>() {

    override fun bindRefreshLayout(): SmartRefreshLayout = srl_refresh

    override fun bindRecyclerViewLayout(): RecyclerView = rv_feed

    override fun getLayoutId(): Int = R.layout.activity_smart_refresh


    override fun setupViews() {
        super.setupViews()
        initRecyclerView()
    }

    override fun bindingViews() {
        super.bindingViews()
    }

    private fun initRecyclerView() {
        rv_feed.adapter = feedListAdapter
        //rv_feed.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
    }

    private val feedListAdapter = CommonAdapter(arrayOf(R.layout.listitem_feed), arrayOf(FeedData::class.java),
        { itemView, model, _ ->
            when (model) {
                is FeedData -> {
                    itemView.tv_desc.text = model.desc
                    itemView.tv_type.text = model.type
                    itemView.tv_date.text = model.publishedAt
                }
            }
        }, { model, position ->

        })
}
