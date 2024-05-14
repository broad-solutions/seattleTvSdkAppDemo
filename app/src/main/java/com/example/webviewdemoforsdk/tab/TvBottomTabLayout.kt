package com.example.webviewdemoforsdk.tab

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.cloudinfinitegroup.seattle_tv_sdk.dp
import com.cloudinfinitegroup.seattle_tv_sdk.dpi
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.example.webviewdemoforsdk.AdActivity
import com.example.webviewdemoforsdk.R
import com.example.webviewdemoforsdk.databinding.LayoutItemViewBinding
import com.example.webviewdemoforsdk.isAndroidTV
import com.example.webviewdemoforsdk.view.focus
import com.google.android.gms.ads.AdSize


class TvBottomTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        orientation = HORIZONTAL
        createView()
    }

    private fun createView() {
        for (i in 0..2) {
            val lp = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1f)
            val item = LayoutItemViewBinding.inflate(LayoutInflater.from(context), this, false)
            item.initView(i)
            addView(item.root, lp)
        }
    }

    private fun LayoutItemViewBinding.initView(index: Int) {
        adView.post {
            val size = AdSize.getLandscapeInlineAdaptiveBannerAdSize(
                context,
                adView.measuredWidth - 20.dpi
            )
            when (index) {
                0 -> {
                    imgBg.setBackgroundResource(R.drawable.splash)

                    if (!isAndroidTV(context)) {
                        "width:${adView.measuredWidth}".print("测试宽")
                        adView.loadAd {
                            adUnitId = "/6499/example/banner"
                            adType = TvAdSdk.MobilAdType.BANNER
                            adSize = size
                        }
                    } else {
                        val gd1 = TextView(context)
                        gd1.gravity = Gravity.CENTER
                        gd1.setBackgroundColor(Color.WHITE)
                        gd1.text = "广告1"
                        val lp = FrameLayout.LayoutParams(
                            size.width,
                            40f.dpi
                        )
                        gd1.setTextColor(Color.BLACK)
                        gd1.layoutParams = lp
                        gd1.setOnClickListener {
                            context?.startActivity(Intent(context, AdActivity::class.java))
                        }
                        val focusView = gd1.focus("广告1")
                        focusView.nextFocusUpId = R.id.fl_ad
                        adView.addAdView(focusView)
                    }
                }

                1 -> {
                    imgBg.setImageResource(R.drawable.ac)
                    val zixun = TextView(context)
                    zixun.gravity = Gravity.CENTER
                    zixun.setBackgroundColor(Color.WHITE)
                    zixun.text = "这里是资讯"
                    zixun.setTextColor(Color.BLACK)
                    val lp = FrameLayout.LayoutParams(
                        size.width,
                        40f.dpi
                    )
                    lp.gravity = Gravity.CENTER
                    zixun.layoutParams = lp
                    zixun.setOnClickListener {
                        context.startActivity(Intent(context, AdActivity::class.java))
                    }
                    val focusView = zixun.focus("资讯")
                    focusView.nextFocusUpId = R.id.fl_ad
                    adView.addAdView(focusView)
                }

                2 -> {
                    imgBg.setImageResource(R.drawable.jpeg)
                    val youxi = TextView(context)
                    youxi.gravity = Gravity.CENTER
                    youxi.setTextColor(Color.BLACK)
                    youxi.setBackgroundColor(Color.WHITE)
                    youxi.text = "这里是游戏"
                    val lp = FrameLayout.LayoutParams(
                        size.width,
                        40f.dpi
                    )
                    youxi.layoutParams = lp
                    youxi.setOnClickListener {
                        context.startActivity(Intent(context, AdActivity::class.java))
                    }
                    val focusView = youxi.focus("游戏")
                    focusView.nextFocusUpId = R.id.fl_ad
                    adView.addAdView(focusView)
                }
            }
        }
    }


}