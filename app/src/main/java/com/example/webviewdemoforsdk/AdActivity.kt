package com.example.webviewdemoforsdk


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.cloudinfinitegroup.seattle_web_sdk.AnalyticsDelegate
import com.cloudinfinitegroup.seattle_web_sdk.AnalyticsDelegateImpl
import com.cloudinfinitegroup.seattle_web_sdk.SeattleSdk
import com.example.webviewdemoforsdk.databinding.ActivityAdBinding


class AdActivity : AppCompatActivity(), AnalyticsDelegate by AnalyticsDelegateImpl() {

    private var binding: ActivityAdBinding? = null
    private var mySdk: SeattleSdk? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        SeattleSdk(this)
        mySdk?.apply {
            initSdk {
                showContent(binding!!.adContainer)
            }
        }
    }
}
