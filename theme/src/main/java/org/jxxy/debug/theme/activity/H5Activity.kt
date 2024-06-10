package org.jxxy.debug.theme.activity

import android.view.KeyEvent
import android.view.View
import android.webkit.*
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.theme.databinding.ActivityH5Binding

internal class H5Activity : BaseActivity<ActivityH5Binding>() {
    var count = 0
    override fun bindLayout(): ActivityH5Binding {
        return ActivityH5Binding.inflate(layoutInflater)
    }

    override fun initView() {
        initWebViewSettings()
        val url = intent.getStringExtra("url")
        url?.let { startWebH5(it) }
    }

    override fun subscribeUi() {
    }

    // 按下返回键
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        // 是否触发按键为back键
        return if (keyCode == KeyEvent.KEYCODE_BACK) {
            onBackPressed()
            true
        } else { // 如果不是back键正常响应
            super.onKeyDown(keyCode, event)
        }
    }

    override fun onBackPressed() {
        finish()
    }

    fun startWebH5(url: String) {
        view.webView.clearFormData()
        view.webView.clearHistory()
        view.webView.clearCache(true)
        view.webView.loadUrl(url)
    }
    val mWebViewClient: WebViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onRenderProcessGone(
            view: WebView?,
            detail: RenderProcessGoneDetail?
        ): Boolean {
            return super.onRenderProcessGone(view, detail)
        }
    }
    val mWebChromeClient = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            this@H5Activity.view.loadingProgressBar.setProgress(newProgress)
            if (newProgress >= 80 || count == 1) {
                // 页面加载完成时隐藏 ProgressBar
                this@H5Activity.view.loadingProgressBar.setVisibility(View.INVISIBLE)
                this@H5Activity.view.loadingTv.hide()
                count = 1
            } else {
                // 页面正在加载中时显示 ProgressBar
                this@H5Activity.view.loadingProgressBar.setVisibility(View.VISIBLE)
            }

            super.onProgressChanged(view, newProgress)
        }
    }

    fun initWebViewSettings() {
        val webSetting = view.webView.getSettings()
        webSetting.setJavaScriptEnabled(true)
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true)
        webSetting.setAllowFileAccess(true)
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS)
        webSetting.setSupportZoom(true)
        webSetting.setBuiltInZoomControls(true)
        webSetting.setUseWideViewPort(true)
        webSetting.setSupportMultipleWindows(true)
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true)
        webSetting.setSupportZoom(false) // 禁用缩放
        webSetting.displayZoomControls = false
        webSetting.setDisplayZoomControls(false)
        webSetting.setDatabaseEnabled(true)
        webSetting.setDomStorageEnabled(true)
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        view.webView.setHorizontalScrollBarEnabled(false)
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND)
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE)
        webSetting.setSaveFormData(false)
        view.webView.setWebViewClient(mWebViewClient)
        view.webView.webChromeClient = mWebChromeClient
    }
}
