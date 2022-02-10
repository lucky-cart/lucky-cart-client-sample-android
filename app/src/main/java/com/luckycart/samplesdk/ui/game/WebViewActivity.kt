package com.luckycart.samplesdk.ui.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_GAME_URL
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.extras?.getString(INTENT_FRAGMENT_GAME_URL)
        if (url != null) {
            webView.loadUrl(url)
        }
        webView.settings.javaScriptEnabled = true
        webView.settings.domStorageEnabled = true
        webView.settings.builtInZoomControls = true
        webView.webViewClient = WebViewClient()
    }
}