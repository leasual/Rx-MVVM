package com.common.rxmvvm

import androidx.recyclerview.widget.RecyclerView
import com.common.core.base.BaseRefreshActivity
import com.common.core.base.CommonAdapter
import com.common.core.models.FeedData
import com.common.rxmvvm.di.mainModule
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.wesoft.mvvm.main.R
import kotlinx.android.synthetic.main.activity_smart_refresh.*
import kotlinx.android.synthetic.main.listitem_feed.view.*
import org.koin.core.context.loadKoinModules

private val loadMainModule by lazy { loadKoinModules(mainModule) }

class SmartRefreshActivity : BaseRefreshActivity<SmartRefreshViewModel>() {

    override fun bindRefreshLayout(): SmartRefreshLayout = srl_refresh

    override fun bindRecyclerViewLayout(): RecyclerView = rv_feed

    override fun getLayoutId(): Int = R.layout.activity_smart_refresh

    override fun initKoinModule() = loadMainModule


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
