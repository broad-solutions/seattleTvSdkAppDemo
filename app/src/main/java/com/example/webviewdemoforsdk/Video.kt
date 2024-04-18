package com.example.webviewdemoforsdk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloudinfinitegroup.seattle_tv_sdk.RepeatMode
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.example.webviewdemoforsdk.databinding.FragmentVideoBinding


class Video : Fragment() {

    private var mBinding: FragmentVideoBinding? = null


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
        mBinding?.tvSdkView?.startAd(
            TvAdSdk.AdType.BANNER,
            repeatMode = RepeatMode.REPEAT_MODE_ON,
        )
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