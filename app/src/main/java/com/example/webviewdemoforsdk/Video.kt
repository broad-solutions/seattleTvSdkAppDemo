package com.example.webviewdemoforsdk

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.seattle_tv_sdk.TvAdSdk

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Video.newInstance] factory method to
 * create an instance of this fragment.
 */
class Video : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var adTypeCounter = 0

    private val broadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val message = intent?.getStringExtra("message")
            // 处理接收到的消息
            println("收到消息:$message")
            if (message!=null&&message=="AdLoaded") {
                // 根据 adTypeCounter 设置不同的 TvAdSdk.AdType
//                val adType = if (adTypeCounter % 2 == 0) {
//                    TvAdSdk.AdType.SECTION
//                } else {
//                    TvAdSdk.AdType.BANNER
//                }
                val adType=TvAdSdk.AdType.BANNER
                TvAdSdk.setAdType(adType)
                //https://storage.googleapis.com/gvabox/media/samples/stock.mp4
                TvAdSdk.sdkStart(activity as AppCompatActivity, "https://storage.googleapis.com/gvabox/media/samples/stock.mp4", R.id.fragment_video_container,
                    GlobalVariables.token,(activity as AppCompatActivity).packageName)

                // 发送广播通知当前 Activity 结束
                val finishIntent = Intent("com.example.ACTION_AD_EVENT")
                LocalBroadcastManager.getInstance(context!!).sendBroadcast(finishIntent)

                // 更新 adTypeCounter，以便下次设置不同的 TvAdSdk.AdType
                adTypeCounter++
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post {
            // 在稍后的时间点执行 SDK.sdkStart() 方法
            TvAdSdk.setAdType(TvAdSdk.AdType.BANNER)
            //
            TvAdSdk.sdkStart(activity as AppCompatActivity, "https://storage.googleapis.com/gvabox/media/samples/stock.mp4", R.id.fragment_video_container,
                GlobalVariables.token,(activity as AppCompatActivity).packageName)
        }
    }
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val filter = IntentFilter("com.example.ACTION_AD_EVENT")
        context.registerReceiver(broadcastReceiver, filter, Context.RECEIVER_NOT_EXPORTED)
    }

    override fun onDetach() {
        super.onDetach()
        context?.unregisterReceiver(broadcastReceiver)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment video.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Video().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}