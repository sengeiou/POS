package com.epro.pos.ui.fragment.settings

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.model.bean.AccountHomeBean
import com.epro.pos.ui.AppConfigActivity
import com.epro.pos.ui.adapter.AccountSetAdapter
import com.mike.baselib.fragment.BaseTitleBarSimpleFragment
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppConfig
import kotlinx.android.synthetic.main.fragment_account_set.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast
import org.jetbrains.anko.support.v4.startActivity


class AccountSetFragment: BaseTitleBarSimpleFragment(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v){
            getLeftView()->{

            }
        }

    }

    companion object {
        const val TAG = "AccountSetFragment"
        fun newInstance(str: String): AccountSetFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = AccountSetFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): AccountSetFragment {
            return newInstance("")
        }
    }
    override fun layoutContentId(): Int {
        return R.layout.fragment_account_set
    }

    override fun lazyLoad() {
    }

    val mTexts = arrayOf(R.string.personal_center,R.string.business_information,R.string.feed_back,R.string.help_center,
                        R.string.system_language,R.string.about_us,R.string.check_for_updates)
    val mBackgrounds = arrayOf(R.drawable.shape_bg_white_half_radius4_top,R.drawable.shape_bg_white_half_radius4_bottom,R.drawable.shape_bg_white_half_radius4_top,
                                        R.drawable.shape_bg_white_half_radius4_bottom, R.drawable.shape_bg_white_half_radius4_top,R.color.mainMatchColor,R.drawable.shape_bg_white_half_radius4_bottom)

    override fun initView() {
        super.initView()
        getTitleView().text=getString(R.string.system_settings)
        getLeftBackView().visibility = View.GONE
        val linearLayoutManager = LinearLayoutManager(activity!!)
        rvSetList.layoutManager = linearLayoutManager
        val list = ArrayList<AccountHomeBean>()
        for (index in mTexts.indices) {
            list.add(AccountHomeBean(getString(mTexts[index]),mBackgrounds[index]))
        }
        if (!AppConfig.isPublish) {
            list.add(AccountHomeBean(getString(R.string.dev_config)+ "(" + AppBusManager.getDevName() + ")",R.drawable.shape_bg_white_half_radius4_bottom))
        }
        val adapter = AccountSetAdapter(activity!!, list)
        rvSetList.adapter = adapter
        adapter.onItemClickListener = object :AccountSetAdapter.OnItemClickListener{
            override fun onClick(item: AccountHomeBean,postion:Int) {
                if(postion==7){
                    startActivity<AppConfigActivity>()
                    return
                }
                var eventMessage = FragmentChanageEvent()
                when(postion){
                   0->{
                       eventMessage.postion = 0
                   }
                    1->{
                        eventMessage.postion = 1
                    }
                    2->{
                        eventMessage.postion = 2
                    }
                    3->{
                        eventMessage.postion = 3
                    }
                    4->{
                       eventMessage.postion = 4
                      // eventMessage.postion = 20
                    }
                    5->{
                        eventMessage.postion = 5

                    }
                    6->{
                        eventMessage.postion = 6
                    }
                }
                EventBus.getDefault().postSticky(eventMessage)
            }
        }
    }

    override fun initData() {

    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
    }

}