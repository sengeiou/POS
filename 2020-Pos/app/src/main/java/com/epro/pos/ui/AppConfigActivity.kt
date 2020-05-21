package com.epro.pos.ui

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.RadioGroup
import com.epro.pos.R
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.activity.BaseTitleBarSimpleActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.Constants
import kotlinx.android.synthetic.main.activity_app_config.*

class AppConfigActivity:BaseTitleBarSimpleActivity(){
    companion object {
        const val TAG = "AppConfigActivity"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, AppConfigActivity::class.java).putExtra(TAG, str))
        }
    }
    override fun layoutContentId(): Int {
      return R.layout.activity_app_config
    }

    override fun initView() {
        super.initView()
        getTitleView().text = "配置"
        radioGroup.check(PosBusManager.getDevCheckId())
        setHaveBackView(true)
        getLeftBackView().setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    override fun initData() {
    }

    override fun initListener() {
        radioGroup.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.rbTest->  AppBusManager.setDev(Constants.DV_TEST)
                R.id.rbPreRelease-> AppBusManager.setDev(Constants.DV_PRE_RELEASE);
                R.id.rbRelease-> AppBusManager.setDev(Constants.DV_RELEASE)
            }
            AppBusManager.setToken("")
            AppBusManager.setShopId("")
            System.exit(0)
        })
    }

}