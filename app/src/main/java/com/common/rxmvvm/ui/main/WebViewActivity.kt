package com.common.rxmvvm.ui.main

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.common.core.base.BaseActivity
import com.common.rxmvvm.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : BaseActivity<WebViewModel>() {

    private lateinit var url: String

    override fun getLayoutId(): Int = R.layout.activity_web_view

    override fun setupViews() {
        url = intent.getStringExtra("url")
        initWebView()
    }

    override fun bindingViews() {
        viewModel.refreshWebView()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        wvWebView.webChromeClient = CustomChromeClient(srlSwipe)
        wvWebView.webViewClient = CustomWebClient()
        val settings = wvWebView.settings
        settings.javaScriptEnabled = true
        settings.cacheMode = WebSettings.LOAD_DEFAULT
        settings.builtInZoomControls = true
        settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        settings.loadWithOverviewMode = true
        settings.saveFormData = true
        settings.domStorageEnabled = true
        settings.javaScriptCanOpenWindowsAutomatically = true
        settings.defaultTextEncodingName = "UTF-8"

        srlSwipe.isRefreshing = true
        wvWebView.loadUrl(url)
        srlSwipe.setOnRefreshListener { wvWebView.loadUrl(url) }
    }

    class CustomWebClient: WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
        }

    }

    class CustomChromeClient(private val swipeView: SwipeRefreshLayout): WebChromeClient() {

        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            swipeView.isRefreshing = newProgress != 100
        }
    }
}
