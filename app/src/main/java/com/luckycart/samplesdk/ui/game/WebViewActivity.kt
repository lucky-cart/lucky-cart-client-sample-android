package com.luckycart.samplesdk.ui.game

import android.os.Bundle
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.luckycart.samplesdk.R
import com.luckycart.samplesdk.utils.INTENT_FRAGMENT_GAME_URL
import kotlinx.android.synthetic.main.activity_webview.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)
        val url = intent.extras?.getString(INTENT_FRAGMENT_GAME_URL)
        if (url != null) {
            webView.loadUrl(url)
        }
        webView.isVerticalScrollBarEnabled = true
        webView.isHorizontalScrollBarEnabled = true
        webView.webViewClient = WebViewClient()
    }
}