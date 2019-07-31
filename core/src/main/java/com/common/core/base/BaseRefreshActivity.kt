package com.common.core.base

import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.RecyclerView
import com.common.core.extensions.disposedBag
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.constant.SpinnerStyle
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import io.reactivex.subjects.PublishSubject

abstract class BaseRefreshActivity<VM: BaseRefreshViewModel>: BaseActivity<VM>() {
    private lateinit var smartRefreshLayout: SmartRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private var putToRefreshPublishSubject = PublishSubject.create<Boolean>()
    private var loadMorePublishSubject = PublishSubject.create<Boolean>()

    override fun setupViews() {
        configPullToRefreshAndLoadMore()
        smartRefreshLayout = bindRefreshLayout()
        recyclerView = bindRecyclerViewLayout()
        //是否禁止下拉刷新
        if (onlyLoadMore()) {
            smartRefreshLayout.setEnableRefresh(false)
        }
    }

    override fun bindingViews() {
        val refreshOutputs = viewModel.transformsRefresh(BaseRefreshViewModel.RefreshInputs(putToRefreshPublishSubject, loadMorePublishSubject))
        smartRefreshLayout.setOnRefreshListener {
            putToRefreshPublishSubject.onNext(true)
        }
        smartRefreshLayout.setOnLoadMoreListener {
            loadMorePublishSubject.onNext(true)
        }

        refreshOutputs.refreshState.subscribe {
            when(it) {
                BaseRefreshViewModel.RefreshState.REFRESH_SUCCESS -> {
                    smartRefreshLayout.finishRefresh()
                    smartRefreshLayout.resetNoMoreData()
                }
                BaseRefreshViewModel.RefreshState.LOAD_MORE_SUCCESS -> {
                    smartRefreshLayout.finishLoadMore()
                }
                BaseRefreshViewModel.RefreshState.NO_MORE_DATA -> {
                    smartRefreshLayout.finishLoadMoreWithNoMoreData()
                }
                else -> {

                }
            }
        }.disposedBag(dispose)
        //设置显示no more data layout,不设置显示会有异常
        smartRefreshLayout.setEnableFooterFollowWhenNoMoreData(true)
        smartRefreshLayout.autoRefresh()
        putToRefreshPublishSubject.onNext(true)

        viewModel.dataListPublishSubject.subscribe {
            (recyclerView.adapter as CommonAdapter).dataList = it
            (recyclerView.adapter as CommonAdapter).notifyDataSetChanged()
        }.disposedBag(dispose)
    }

    abstract fun bindRecyclerViewLayout(): RecyclerView

    abstract fun bindRefreshLayout(): SmartRefreshLayout

    //是否只有上拉加载更多
    open fun onlyLoadMore(): Boolean = false

    private fun configPullToRefreshAndLoadMore() {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            layout.setEnableAutoLoadMore(true)
            layout.setEnableScrollContentWhenLoaded(true)
            //这里设置无效
            layout.setEnableFooterFollowWhenNoMoreData(true)
            layout.setEnableOverScrollDrag(true)
            layout.setEnableOverScrollBounce(true)
            layout.setEnableLoadMoreWhenContentNotFull(false)
        }
        //配置刷新头
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(true)
            ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate)
        }
        //配置加载更多
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate)
        }
    }
}