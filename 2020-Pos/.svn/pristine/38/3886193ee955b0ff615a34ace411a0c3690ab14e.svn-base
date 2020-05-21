package com.epro.pos.ui.fragment.settings;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.AboutUsContract
import com.epro.pos.mvp.presenter.AboutUsPresenter
import com.epro.pos.ui.adapter.AboutUsAdapter
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import kotlinx.android.synthetic.main.activity_about_us.*
import org.greenrobot.eventbus.EventBus


class AboutUsFragment : BaseTitleBarCustomFragment<AboutUsContract.View, AboutUsPresenter>(), AboutUsContract.View, View.OnClickListener {
    override fun onClick(v: View?) {
        when(v){
            //返回键
            getLeftView()->{
                var msg = FragmentChanageEvent()
                msg.postion = 13
                msg.level = 1
                EventBus.getDefault().postSticky(msg)
            }
        }

    }

    override fun lazyLoad() {

    }

    companion object {
        const val TAG = "AboutUs"
        fun newInstance(str: String): AboutUsFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = AboutUsFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): AboutUsFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): AboutUsPresenter {
        return AboutUsPresenter()
    }

    override fun onAboutUsSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_about_us
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        getLeftTitleView().text = getString(R.string.system_settings)
        getTitleView().text =getString(R.string.about_us)
        val linearLayoutManager = LinearLayoutManager(activity!!)
        rcAboutList.layoutManager = linearLayoutManager
        rcAboutList.adapter = AboutUsAdapter(activity!!,ArrayList())
    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
    }

}


