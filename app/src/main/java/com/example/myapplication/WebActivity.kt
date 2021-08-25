package com.example.myapplication

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.webkit.*
import com.example.myapplication.base.BaseActivity
import com.example.myapplication.databinding.WebViewBinding


/**
 * Description: 展示网页用的activity，目前是用来展示新闻资讯
 */
class WebActivity : BaseActivity<WebViewBinding>() {

    /*   private var mWebView: WebView? = null

       private var loading_rl: View? = null
       private var rlError: LinearLayout? = null*/
    private var url: String? = null
    override fun getViewBinding() = WebViewBinding.inflate(layoutInflater)

    @SuppressLint("SetJavaScriptEnabled", "JavascriptInterface", "AddJavascriptInterface")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //setTitleText(getString(R.string.news))
        binding.webview.settings.javaScriptEnabled = true // 设置使用够执行JS脚本
        binding.webview.settings.setAppCacheEnabled(false)
        // val intent: Intent = intent
        val bundle = intent.extras
        if (bundle != null) {
            url = bundle.getString("url")
            val title = bundle.getString("title")
        }
        binding.loadingRl.visibility = View.VISIBLE
        binding.webview.loadUrl(url)
        binding.webview.webViewClient = object : WebViewClient() {

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                binding.rlError.visibility = View.INVISIBLE
                binding.webview.visibility = View.VISIBLE
            }

            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                val hitTestResult = view.hitTestResult
                return if (!TextUtils.isEmpty(url) && hitTestResult == null) {
                    true
                } else false
            }

            override fun onReceivedError(
                view: WebView,
                request: WebResourceRequest,
                error: WebResourceError
            ) {
                super.onReceivedError(view, request, error)
                binding.rlError.visibility = View.VISIBLE
                binding.webview.visibility = View.INVISIBLE
            }
        }

        binding.webview.webChromeClient = object : WebChromeClient() {
            override fun onJsAlert(
                view: WebView, url: String, message: String,
                result: JsResult
            ): Boolean {
                val b2: AlertDialog.Builder = AlertDialog.Builder(
                    this@WebActivity
                )
                    .setTitle(getString(R.string.tip_warn))
                    .setMessage(message)
                    .setPositiveButton("ok"
                    ) { _, which ->
                        result.confirm()
                        // MyWebView.this.finish();
                    }
                b2.setCancelable(false)
                b2.create()
                b2.show()
                return true
            }

            override fun onProgressChanged(view: WebView, newProgress: Int) {
                if (newProgress == 100) {
                    binding.loadingRl.visibility = View.GONE
                }
            }
        }
        binding.webview.addJavascriptInterface(this, "wst")
        binding.webview.clearCache(true)
        binding.webview.clearHistory()

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            binding.webview.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        binding.webview.settings.blockNetworkImage = false
    }

}