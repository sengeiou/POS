package com.epro.pos.ui.fragment

import android.os.Bundle
import com.mike.baselib.fragment.BaseSimpleFragment
import com.epro.pos.R
import com.mike.baselib.fragment.BaseTitleBarSimpleFragment

class PosSetFragment: BaseTitleBarSimpleFragment(){

    companion object {
        const val TAG = "PosSetFragment"
        fun newInstance(str: String): PosSetFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = PosSetFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): PosSetFragment {
            return newInstance("")
        }
    }
    override fun layoutContentId(): Int {
        return R.layout.fragment_pos_set
    }

    override fun lazyLoad() {
    }

    override fun initView() {
        super.initView()
        getTitleView().text=getString(R.string.pos_setting)
    }
    override fun initData() {
    }

    override fun initListener() {
    }


}