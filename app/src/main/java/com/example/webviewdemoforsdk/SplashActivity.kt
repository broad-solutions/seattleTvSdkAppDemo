package com.example.webviewdemoforsdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.seattle_tv_sdk.TvAdSdk
import com.google.gson.Gson
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request

class SplashActivity : AppCompatActivity(){
    private var client_id:String?=null
    private var client_secret:String?=null

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("message")
            // 处理接收到的消息
            if (message != null) {
                val mainIntent = Intent(context, MainActivity::class.java)
                mainIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                context?.startActivity(mainIntent)
                finish()
                // 发送广播通知当前 Activity 结束
                val finishIntent = Intent("com.example.ACTION_AD_EVENT")
                LocalBroadcastManager.getInstance(context!!).sendBroadcast(finishIntent)
            }
        }
    }
    @OptIn(DelicateCoroutinesApi::class)
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val _this=this
        //client_id和client_secret需要从sdk提供方获取
        client_id="S766494354"
        client_secret="17fadc2e6b084965ab5e690bf4bd8f6c"
        TvAdSdk.setAdType(TvAdSdk.AdType.SPLASH)
        GlobalScope.launch(Dispatchers.IO) {
            val client = OkHttpClient.Builder()
                .hostnameVerifier { _, _ -> true } // 禁用主机名验证
                .build()

            val request = Request.Builder()
                .url("http://clientapi.pubadding.com/oauth/token?client_id=$client_id&client_secret=$client_secret&grant_type=client_credentials")
                .build()

            client.newCall(request).execute().use { response ->
                if (response.isSuccessful) {
                    val json=response.body?.string()
                    println("result:$json")
                    val gson = Gson()
                    val accessTokenResponse = gson.fromJson(json, Datautils::class.java)
                    val tokenRequest=accessTokenResponse.access_token
                    GlobalVariables.token=tokenRequest
                    println("token:$tokenRequest")
                    val handler = Handler(Looper.getMainLooper())
                    handler.post {
                        // 在 UI 线程上更新 UI 元素的操作
                        TvAdSdk.sdkStart(_this,"",R.id.SplashAdContaniner,GlobalVariables.token,_this.packageName,R.drawable.splash)
                    }
                } else {
                    println("请求失败: ${response.message}")
                }
            }
        }
        val filter = IntentFilter("com.example.ACTION_AD_EVENT")
        this.registerReceiver(broadcastReceiver, filter, RECEIVER_NOT_EXPORTED)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(broadcastReceiver)
        finish()
    }
}