package com.example.webviewdemoforsdk

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.webviewdemoforsdk.databinding.ActivitySliderBinding

class Slider : AppCompatActivity() {
    private lateinit var fragmentList: ArrayList<Fragment>
    private var currentFragmentIndex = 0
    private lateinit var binding: ActivitySliderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivitySliderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        fragmentList = ArrayList()
        // 添加您的 Fragment 到 fragmentList 中
        fragmentList.add(Video())
        fragmentList.add(Image())
        fragmentList.add(Smaato())
        // 默认填充第一个 Fragment 到指定容器内
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, fragmentList[currentFragmentIndex])
                .commit()
        }
    }
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.action == KeyEvent.ACTION_DOWN) {
            when (event.keyCode) {
                KeyEvent.KEYCODE_DPAD_LEFT -> {
                    // 处理左按键事件
                    currentFragmentIndex--
                    switchFragment()
                    return true
                }
                KeyEvent.KEYCODE_DPAD_RIGHT -> {
                    // 处理右按键事件
                    currentFragmentIndex++
                    switchFragment()
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
            .replace(R.id.fragmentContainer, fragmentList[currentFragmentIndex])
            .commit()
    }
}