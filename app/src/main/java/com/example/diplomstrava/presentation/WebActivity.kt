package com.example.diplomstrava.presentation

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.diplomstrava.R
import com.example.diplomstrava.databinding.ActivityMainBinding
import com.example.diplomstrava.databinding.ActivityWebBinding
import timber.log.Timber

class WebActivity : AppCompatActivity(R.layout.activity_web) {

    private val binding by viewBinding(ActivityWebBinding::bind, R.id.webView)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("web view")
        binding.webView.webViewClient = MyWebViewClient()
        handleIntentData()
    }

    private fun handleIntentData() {
        intent.data?.let {
            Timber.d(it.toString())
            binding.webView.loadUrl(it.toString())
        }
    }

    private class MyWebViewClient : WebViewClient() {
        @TargetApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            view.loadUrl(request.url.toString())
            return true
        }

        // Для старых устройств
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }
    }
}