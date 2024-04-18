package com.example.webviewdemoforsdk

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.cloudinfinitegroup.seattle_tv_sdk.DefaultAdListener
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.example.webviewdemoforsdk.databinding.ActivitySplashBinding
import com.google.gson.Gson

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
        val client_secret = "17fadc2e6b084965ab5e690bf4bd8f6c"
        mBinding.tvSdkView.setPlaceHolder(R.drawable.splash)
        mBinding.tvSdkView.setPlayersMuted(true)
        TvAdSdk.getAuthorized(client_id, client_secret) {
            if (it.isNullOrEmpty()) {
                return@getAuthorized
            }
            val accessTokenResponse = Gson().fromJson(it, Datautils::class.java)
            val tokenRequest = accessTokenResponse.access_token
            GlobalVariables.token = tokenRequest
            TvAdSdk.init(tokenRequest, packageName)
            mBinding.tvSdkView.startAd(
                TvAdSdk.AdType.SPLASH,
                listener = object : DefaultAdListener() {
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

                }
            )
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