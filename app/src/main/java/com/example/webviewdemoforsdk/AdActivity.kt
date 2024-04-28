package com.example.webviewdemoforsdk


import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.cloudinfinitegroup.seattle_web_sdk.AnalyticsDelegate
import com.cloudinfinitegroup.seattle_web_sdk.AnalyticsDelegateImpl
import com.cloudinfinitegroup.seattle_web_sdk.SeattleSdk
import com.example.webviewdemoforsdk.databinding.ActivityAdBinding
import com.google.gson.Gson


class AdActivity : AppCompatActivity(), AnalyticsDelegate by AnalyticsDelegateImpl() {

    private var binding: ActivityAdBinding? = null
    private var mySdk: SeattleSdk? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        mySdk = SeattleSdk(this)
        TvAdSdk.setDebug(true)
        mySdk?.apply {
            showContent(binding!!.adContainer)
            initSdk(packageName, TvAdSdk.token!!) {
                "initSdk $it".print("initSdk")
            }
        }

    }
}
