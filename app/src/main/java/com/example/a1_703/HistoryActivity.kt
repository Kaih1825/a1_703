package com.example.a1_703

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class HistoryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        findViewById<WebView>(R.id.webview).loadUrl("file:///android_asset/技能競賽主題網站.html")
        findViewById<WebView>(R.id.webview).settings.allowFileAccess=true
    }
}