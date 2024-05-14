package com.example.webviewdemoforsdk

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.media3.common.util.UnstableApi
import com.cloudinfinitegroup.seattle_tv_sdk.DefaultAdListener
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.example.webviewdemoforsdk.databinding.ActivitySplashBinding
import com.google.ads.interactivemedia.v3.api.AdEvent
import com.google.android.gms.ads.AdSize
import com.google.gson.Gson
import javax.security.auth.login.LoginException

class SplashActivity : AppCompatActivity() {
    private val mBinding: ActivitySplashBinding by lazy(mode = LazyThreadSafetyMode.NONE) {
        ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(mBinding.root)
        //client_id和client_secret需要从sdk提供方获取
        val client_id = "S766494354"
//        val client_secret = "17fadc2e6b084965ab5e690bf4bd8f6c"
        val client_secret = "b709ab7f31cf4e21ad7e2c79227112d3"
        mBinding.tvSdkView.setPlaceHolder(R.drawable.splash)
        mBinding.tvSdkView.setPlayersMuted(true)
        TvAdSdk.setDebug(true)
        TvAdSdk.getAuthorized(client_id, client_secret) {
            if (it.isNullOrEmpty()) {
                return@getAuthorized
            }
            Gson().runCatching {
                val accessTokenResponse = fromJson(it, Datautils::class.java)
                val tokenRequest = accessTokenResponse.access_token
                TvAdSdk.initV2(this@SplashActivity, tokenRequest, packageName) { result ->

                    mBinding.tvSdkView.startAd(
                        TvAdSdk.AdType.SPLASH,
                        listener = object : DefaultAdListener() {

                            override fun onAdPaused() {
                                super.onAdPaused()
                                goToMain()
                            }

                            override fun onSkip() {
                                goToMain()
                            }

                            override fun onAdCompleted() {
                                goToMain()
                            }

                            override fun onAdFailedToLoad(errorMessage: String) {
                                goToMain()
                            }

                            override fun onAdIsEmpty(errorMessage: String) {
                                goToMain()
                            }

                        })
                }

            }.onFailure {
                Toast.makeText(this@SplashActivity, it.message, Toast.LENGTH_LONG).show()
                goToMain()
            }

        }
    }

    fun goToMain() {
        val mainIntent = Intent(this, MainActivity::class.java)
        mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(mainIntent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    override fun onStop() {
        mBinding.tvSdkView.pauseAd()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.tvSdkView.destroyAd()
    }
}