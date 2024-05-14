package com.example.webviewdemoforsdk.view

import android.view.MotionEvent
import android.view.View
import androidx.core.view.setPadding
import com.cloudinfinitegroup.seattle_tv_sdk.dpi
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.example.webviewdemoforsdk.R

fun View.focus(tag: String = "adviewFocus"): View {
    val focusView = FocusFrameLayout(context)
    focusView.setPadding(1f.dpi)
    setOnHoverListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_HOVER_ENTER -> {
                focusView.requestFocus()
            }

            MotionEvent.ACTION_HOVER_EXIT -> {
                focusView.clearFocus()
            }
        }
        true
    }
    focusView.setOnClickListener {
        this.callOnClick()
    }
    focusView.setOnFocusChangeListener { v, hasFocus ->
        "$v $hasFocus".print(tag)
        if (hasFocus) {
            focusView.setBackgroundResource(R.drawable.rectangle)
        } else {
            focusView.setBackgroundResource(R.drawable.un_select_rectangle)
        }
    }
    focusView.addView(this)
    return focusView
}

fun View.attachFocus(tag: String) {
    setOnHoverListener { v, event ->
        when (event.action) {
            MotionEvent.ACTION_HOVER_ENTER -> {
                v.requestFocus()
            }

            MotionEvent.ACTION_HOVER_EXIT -> {
                v.clearFocus()
            }
        }
        true
    }
    setOnFocusChangeListener { v, hasFocus ->
        "$v $hasFocus".print(tag)
    }
}