package com.example.webviewdemoforsdk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.cloudinfinitegroup.seattle_tv_sdk.TvAdSdk
import com.example.webviewdemoforsdk.databinding.FragmentImageBinding


class Smaato : Fragment() {
    private var mBinding: FragmentImageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        mBinding = FragmentImageBinding.inflate(inflater, container, false);
        return mBinding!!.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.post {
            mBinding?.tvSdkView?.startAd(
                TvAdSdk.AdType.SECTION,
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
            Smaato()
    }
}