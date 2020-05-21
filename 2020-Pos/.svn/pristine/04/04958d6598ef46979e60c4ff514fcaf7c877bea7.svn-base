package com.epro.pos.ui.fragment

import android.os.Bundle
import com.mike.baselib.fragment.BaseSimpleFragment
import com.epro.pos.R

class SplashFragment: BaseSimpleFragment(){

    companion object {
        const val TAG = "SplashFragment"
        fun newInstance(str: String): SplashFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = SplashFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): SplashFragment {
            return newInstance("")
        }
    }
    override fun layoutContentId(): Int {
        return R.layout.fragment_splash
    }

    override fun lazyLoad() {
    }

    override fun initView() {
        super.initView()
    }
    override fun initData() {
    }

    override fun initListener() {
    }


}