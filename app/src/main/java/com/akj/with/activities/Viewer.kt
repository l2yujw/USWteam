package com.akj.with.activities

import android.annotation.SuppressLint
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.webkit.WebViewAssetLoader
import com.akj.with.R

private const val BASE_URL = "https://appassets.androidplatform.net/assets/www"

class Viewer : AppCompatActivity() {
    private lateinit var webView: WebView
    private var targetStation:String?=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewer)

        if (intent.hasExtra("targetStation")) {
            targetStation = intent.getStringExtra("targetStation")
        } else {
            Toast.makeText(applicationContext, "targetStation 검색 실패", Toast.LENGTH_SHORT).show()

        }
        val btnBack = findViewById<ImageView>(R.id.btn_viewer_back)
        val targetStationtext = findViewById<TextView>(R.id.tv_viewer_target)

        btnBack.setOnClickListener {
            finish()
        }
        targetStationtext.text = targetStation
        // Setup webView
        initWebView()
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        webView = findViewById(R.id.webview_viewer)
        webView.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
            allowContentAccess = true
        }
        loadScene()
    }

    private fun loadScene() {
        val assetLoader = WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", WebViewAssetLoader.AssetsPathHandler(this))
            .build()
        webView.loadUrl("$BASE_URL/index.html")
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                loadObjModel()
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
            }

            @SuppressWarnings("deprecation") // for API < 21
            override fun shouldInterceptRequest(
                view: WebView?,
                url: String?
            ): WebResourceResponse {
                return assetLoader.shouldInterceptRequest(Uri.parse(url))!!
            }

            @RequiresApi(21)
            override fun shouldInterceptRequest(
                view: WebView?,
                request: WebResourceRequest?
            ): WebResourceResponse? {
                return assetLoader.shouldInterceptRequest(request!!.url)
            }
        }
    }

    private fun loadObjModel() {
        val action = "javascript:loadModel('$BASE_URL/models/','${targetStation}','base')"
        webView.loadUrl(action)
    }
}