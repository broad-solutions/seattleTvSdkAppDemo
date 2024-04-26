package com.example.webviewdemoforsdk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.example.webviewdemoforsdk.databinding.FragmentVideoBinding
import com.example.webviewdemoforsdk.view.attachFocus


class Section : FocusFragment() {

    private var mBinding: FragmentVideoBinding? = null
    override fun requestFocus() {
        "viewPaper give me focus".print("Section")
        mBinding?.tvSdkView?.requestFocus()
    }


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
        view.attachFocus("SectionFragment")
        mBinding?.apply {
            tvSdkView.startAd(
                TvAdSdk.AdType.SECTION,
                "https://storage.googleapis.com/gvabox/media/samples/stock.mp4"
            )
        }

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
            Section()
    }
}