package com.example.webviewdemoforsdk.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.cloudinfinitegroup.seattle_tv_sdk.dpi
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.example.webviewdemoforsdk.AdActivity
import com.example.webviewdemoforsdk.R
import com.example.webviewdemoforsdk.databinding.FragmentVideoBinding
import com.example.webviewdemoforsdk.isAndroidTV
import com.example.webviewdemoforsdk.view.focus
import com.google.android.gms.ads.AdSize


class Video : FocusFragment() {

    private var mBinding: FragmentVideoBinding? = null
    override fun requestFocus() {
        "viewPaper give me focus".print("Video")
        mBinding?.tvSdkView?.requestFocus()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentVideoBinding.inflate(inflater, container, false).apply {
            mBinding = this
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding?.apply {
            tvSdkView.startAd(
                TvAdSdk.AdType.BANNER,
                "https://storage.googleapis.com/gvabox/media/samples/stock.mp4"
            )
            tvSdkView.getPlayerView()?.nextFocusDownId = R.id.mobileSdk
            mobileSdk.postDelayed({
                val adSize0: AdSize =
                    AdSize.getLandscapeInlineAdaptiveBannerAdSize(
                        root.context, tvSdkView.measuredWidth / 2
                    )
                Log.e("adapter", "adSize0:$adSize0")
                if (isAndroidTV(view.context)) {
                    mobileSdk.loadAd {
                        adUnitId = "/6499/example/banner"  //固定式横幅
                        //                    adUnitId = "/6499/example/interstitial" 插槽
                        //                    adUnitId = "/6499/example/app-open"
                        adType = TvAdSdk.MobilAdType.BANNER
                        adSize = adSize0
                        adWarp = {
                            it.focus("video横幅")
                        }
                    }

                } else {
                    val youxi = TextView(context)
                    youxi.gravity = Gravity.CENTER
                    youxi.setBackgroundColor(Color.WHITE)
                    youxi.text = "广告1"
                    val lp = FrameLayout.LayoutParams(
                        adSize0.width,
                        40f.dpi
                    )
                    youxi.setTextColor(Color.BLACK)
                    youxi.layoutParams = lp
                    youxi.setOnClickListener {
                        context?.startActivity(Intent(context, AdActivity::class.java))
                    }
                    val focus = youxi.focus("video横幅")
                    focus.nextFocusUpId = tvSdkView.id
                    mobileSdk.addAdView(focus)
                }
            }, 100)
        }

    }

    override fun onResume() {
        super.onResume()
        mBinding?.tvSdkView?.resumeAd()
    }

    override fun onPause() {
        super.onPause()
        mBinding?.tvSdkView?.pauseAd()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding?.tvSdkView?.destroyAd()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Video()
    }
}