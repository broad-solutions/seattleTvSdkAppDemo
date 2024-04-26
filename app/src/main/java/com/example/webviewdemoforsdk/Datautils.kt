package com.example.webviewdemoforsdk

import android.app.UiModeManager
import android.content.Context
import android.content.res.Configuration
import com.cloudinfinitegroup.seattle_tv_sdk.print


data class Datautils(
    val access_token: String,
    val token_type: String,
    val expires_in: Int,
    val scope: String,
    val jti: String
)


fun isAndroidTV(context: Context): Boolean {
    val uiModeManager = context.getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
    val isTv=uiModeManager.currentModeType == Configuration.UI_MODE_TYPE_TELEVISION
    "当前是否是电视：$isTv".print("isAndroidTV")
    return isTv
}