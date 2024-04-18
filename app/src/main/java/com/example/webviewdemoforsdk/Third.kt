package com.example.webviewdemoforsdk

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.cloudinfinitegroup.seattle_tv_sdk.ui.TvSdkView

class Third : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_thrid)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val tvSdkView = findViewById<TvSdkView>(R.id.testView)
        tvSdkView.startAd(
            TvAdSdk.AdType.BANNER
        )
    }
}