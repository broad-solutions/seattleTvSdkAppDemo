package com.example.webviewdemoforsdk.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.cloudinfinitegroup.seattle_tv_sdk.print
import com.example.webviewdemoforsdk.R
import com.example.webviewdemoforsdk.databinding.FragmentVideoBinding
import com.example.webviewdemoforsdk.view.attachFocus


class Image : FocusFragment() {

    private var mBinding: FragmentVideoBinding? = null
    override fun requestFocus() {
        "viewPaper give me focus".print("Image")
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
        view.attachFocus("ImageFragment")

        //近图片
        mBinding?.tvSdkView?.setPlaceHolder(R.drawable.splash)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            Image()
    }
}