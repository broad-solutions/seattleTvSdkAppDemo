package com.example.webviewdemoforsdk


import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.cloudinfinitegroup.seattle_tv_sdk.dpi
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.example.webviewdemoforsdk.databinding.ActivityMainBinding
import com.example.webviewdemoforsdk.fragment.Image
import com.example.webviewdemoforsdk.fragment.Section
import com.example.webviewdemoforsdk.fragment.Video
import com.example.webviewdemoforsdk.view.focus


class MainActivity : AppCompatActivity(), ViewTreeObserver.OnGlobalFocusChangeListener {

    private var binding: ActivityMainBinding? = null
    private val fragments =
        arrayListOf(Video.newInstance(), Image.newInstance(), Section.newInstance())
    private var currentItem = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.initAd()
        binding?.apply {
            root.viewTreeObserver.addOnGlobalFocusChangeListener(this@MainActivity)
            tabLayout.setTabs(
                listOf("BANNER", "IMAGE", "SECTION")
            ) { index ->
                setCurrentItem(index)
            }
        }
    }

    private fun setCurrentItem(index: Int) {
        if (this.currentItem == index) {
            return
        }
        this.currentItem = index
        supportFragmentManager.beginTransaction()
            .replace(R.id.adBanner, fragments[index])
            .commit()
    }

    private fun ActivityMainBinding.initAd() {
        val imageView = ImageView(this@MainActivity)
        imageView.setImageResource(R.drawable.logo_banner)
        imageView.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            40f.dpi
        )
        imageView.setOnClickListener {
            "点击了".print("adView0")
            val intent = Intent(this@MainActivity, AdActivity::class.java)
            startActivity(intent)
        }
        val focusView = imageView.focus("广告四")
        focusView.nextFocusLeftId = R.id.adBanner
        adView0.addAdView(focusView)
    }


    override fun onGlobalFocusChanged(oldFocus: View?, newFocus: View?) {
        "oldFocus$oldFocus newFocus$newFocus".print("Main")
    }
}
