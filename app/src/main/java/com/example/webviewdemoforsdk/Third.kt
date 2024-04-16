package com.example.webviewdemoforsdk

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.seattle_tv_sdk.TvAdSdk

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
        TvAdSdk.setAdType(TvAdSdk.AdType.BANNER)
        TvAdSdk.sdkStart(this,"https://storage.googleapis.com/gvabox/media/samples/stock.mp4",R.id.testView,GlobalVariables.token,this.packageName)
    }
}