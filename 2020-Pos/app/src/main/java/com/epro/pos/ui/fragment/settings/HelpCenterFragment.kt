package com.epro.pos.ui.fragment.settings;

import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.HelpCenterContract
import com.epro.pos.mvp.presenter.HelpCenterPresenter
import com.epro.pos.ui.adapter.HelpCenterAdapter
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import kotlinx.android.synthetic.main.activity_help_center.*
import org.greenrobot.eventbus.EventBus


class HelpCenterFragment : BaseTitleBarCustomFragment<HelpCenterContract.View, HelpCenterPresenter>(), HelpCenterContract.View, View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            //返回键
            getLeftView()->{
                var msg = FragmentChanageEvent()
                msg.postion = 13
                EventBus.getDefault().postSticky(msg)
            }
        }
    }

    override fun lazyLoad() {

    }

    companion object {
        const val TAG = "HelpCenter"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, HelpCenterFragment::class.java).putExtra(TAG, str))
        }
    }

    override fun getPresenter(): HelpCenterPresenter {
        return HelpCenterPresenter()
    }

    override fun onHelpCenterSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_help_center
    }

    override fun initData() {

    }

    var mProblems = ArrayList<String>()
    override fun initView() {
        super.initView()
        initListData()
        setHaveBackView(true)
        getLeftTitleView().text = getString(R.string.system_settings)
        getTitleView().text =getString(R.string.help_center)
        val linearLayoutManager = LinearLayoutManager(activity!!)
        rcHelpCenter.layoutManager = linearLayoutManager
        logTools.t("YB").d("initView :"+mProblems)
        rcHelpCenter.adapter   =   HelpCenterAdapter(activity!!, mProblems)
    }

    private fun initListData() {
        mProblems.clear()
        var problems = resources.getTextArray(R.array.problem_type)
        for (index in problems.indices){
            mProblems!!.add(problems[index].toString())
        }
        logTools.t("YB").d("mProblems :"+mProblems)
    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
    }

}


