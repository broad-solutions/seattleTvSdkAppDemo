package com.example.webviewdemoforsdk.tab

import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.util.AttributeSet
import android.view.Gravity
import android.view.MotionEvent
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import com.cloudinfinitegroup.seattle_tv_sdk.dpi
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.example.webviewdemoforsdk.R


class TvTabLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var oldFocusChild: TextView? = null
    private var currentIndex = 0

    init {
        orientation = HORIZONTAL
    }

    var onClickListener: (Int) -> Unit = { _ -> }
    override fun onFocusChanged(gainFocus: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect)
        "onFocusChanged $gainFocus $focusedChild".print("TvTabLayout")
    }

    fun requestTabFocus(index: Int) {
        if (index < 0 || index >= childCount) {
            return
        }
        //记录当前选中的tab
        this.currentIndex = index
        getChildAt(index).requestFocus()

    }

    fun setTabs(
        tabs: List<String>,
        onClickListener: (Int) -> Unit = { _ -> }
    ) {
        this.onClickListener = onClickListener
        tabs.forEachIndexed { index, tab ->
            // 添加 Tab 到布局中
            val tabItem = TextView(context)
            val layoutParams =
                LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams.marginEnd = 16
            layoutParams.gravity = Gravity.CENTER
            tabItem.setPadding(8f.dpi, 2f.dpi, 8f.dpi, 2f.dpi)
            tabItem.setOnFocusChangeListener { v, hasFocus ->
                "$tab focus $hasFocus".print("TvTabLayout")
                if (hasFocus) {
                    switchTab(index)
                } else {
                    v.setBackgroundResource(R.drawable.un_select_rectangle)
                }
            }
            tabItem.setTextColor(Color.WHITE)
            tabItem.setOnHoverListener { v, event ->
                if (v is TextView) {
                    when (event.action) {
                        MotionEvent.ACTION_HOVER_ENTER -> {
                            tabItem.requestFocus()
                        }

                        MotionEvent.ACTION_HOVER_EXIT -> {
                            tabItem.clearFocus()
                        }
                    }
                }
                true
            }
            tabItem.setOnClickListener {
                "点击了${tab}${index}".print("TvTabLayout")
                switchTab(index)
            }
            tabItem.isFocusable = true
            tabItem.text = tab
            addView(tabItem, layoutParams)
        }
        switchTab(0)
    }


    private fun switchTab(index: Int) {
        if (index < 0 || index >= childCount) {
            return
        }
        forEachIndexed { p, view ->
            if (view is TextView) {
                if (p == index) {
                    oldFocusChild?.setTextColor(Color.WHITE)
                    oldFocusChild = view
                    view.setBackgroundResource(R.drawable.rectangle)
                    view.setTextColor(Color.YELLOW)
                    onClickListener.invoke(index)
                } else {
                    view.setBackgroundResource(R.drawable.un_select_rectangle)
                }
            }
        }

    }


}