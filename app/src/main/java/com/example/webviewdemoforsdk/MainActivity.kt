package com.example.webviewdemoforsdk

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.webviewdemoforsdk.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var fragmentList: ArrayList<Fragment>
    private var currentFragmentIndex = 0

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentList = ArrayList()
        // 添加您的 Fragment 到 fragmentList 中
        fragmentList.add(Video.newInstance())
        fragmentList.add(Image.newInstance())
        fragmentList.add(Smaato.newInstance())
        // 默认填充第一个 Fragment 到指定容器内
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.videoContentMain, fragmentList[currentFragmentIndex])
                .commit()


            initIndicator()
        }

        binding.textView8.isFocusable = true
        binding.textView8.requestFocus()
        binding.textView8.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        // 处理确认按键事件
                        val intent = Intent(this, Third::class.java)
                        startActivity(intent)
                        return@setOnKeyListener true
                    }
                }
            }
            false
        }
        binding.textView8.setOnFocusChangeListener { view, hasFocus ->
            val text = binding.textView8
            if (hasFocus) {
                // 当View获取到焦点时执行的逻辑
                text.setTextColor(Color.YELLOW)
            } else {
                // 当View失去焦点时执行的逻辑
                text.setTextColor(Color.WHITE)
            }
        }
        binding.textView8.setOnClickListener {
            val intent = Intent(this, Third::class.java)
            startActivity(intent)
        }
        binding.sliderItem.isFocusable = true
        binding.sliderItem.requestFocus()
        binding.sliderItem.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN) {
                when (keyCode) {
                    KeyEvent.KEYCODE_DPAD_LEFT -> {
                        // 处理向左按键事件
                        return@setOnKeyListener true
                    }

                    KeyEvent.KEYCODE_DPAD_RIGHT -> {
                        // 处理向右按键事件
                        return@setOnKeyListener true
                    }

                    KeyEvent.KEYCODE_DPAD_CENTER -> {
                        // 处理确认按键事件
                        val intent = Intent(this, Slider::class.java)
                        startActivity(intent)
                        return@setOnKeyListener true
                    }
                }
            }
            false
        }
        binding.sliderItem.setOnFocusChangeListener { view, hasFocus ->
            val text = binding.sliderItem
            if (hasFocus) {
                // 当View获取到焦点时执行的逻辑
                text.setTextColor(Color.YELLOW)
            } else {
                // 当View失去焦点时执行的逻辑
                text.setTextColor(Color.WHITE)
            }
        }
        binding.sliderItem.setOnClickListener {
            val intent = Intent(this, Slider::class.java)
            startActivity(intent)
        }
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    runOnUiThread {
                        // 处理左按键事件
                        currentFragmentIndex--
                        switchFragment()
                    }
                    return true
                }

                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    runOnUiThread {
                        // 处里右边按键事件
                        currentFragmentIndex++
                        switchFragment()
                    }
                    return true
                }
            }
        }
        return super.dispatchKeyEvent(event)
    }

    private fun switchFragment() {
        if (currentFragmentIndex < 0) {
            currentFragmentIndex = 0
        } else if (currentFragmentIndex >= fragmentList.size) {
            currentFragmentIndex = fragmentList.size - 1
        }
        Log.d("FragmentSwitch", "Switching to fragment at index: $currentFragmentIndex")
        supportFragmentManager.beginTransaction()
            .replace(R.id.videoContentMain, fragmentList[currentFragmentIndex])
            .commit()
        // 更新指示器
        updateIndicator()
    }

    private fun updateIndicator() {
        for (i in 0 until fragmentList.size) {
            val indicator = binding.indicatorLayout.getChildAt(i) as ImageView
            indicator.setImageResource(if (i == currentFragmentIndex) R.drawable.ic_filled_circle else R.drawable.ic_empty_circle)
        }
    }

    private fun initIndicator() {
        for (i in 0 until fragmentList.size) {
            val indicator = ImageView(this)
            indicator.setImageResource(if (i == 0) R.drawable.ic_filled_circle else R.drawable.ic_empty_circle)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(8, 0, 8, 0)
            indicator.layoutParams = params
            binding.indicatorLayout.addView(indicator)
        }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(0)
    }
}