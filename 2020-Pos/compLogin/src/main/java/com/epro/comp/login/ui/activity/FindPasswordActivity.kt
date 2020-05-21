package com.epro.comp.login.ui.activity;

import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.epro.comp.login.R
import com.epro.comp.login.mvp.contract.FindPasswordContract
import com.epro.comp.login.mvp.presenter.FindPasswordPresenter


class FindPasswordActivity : BaseTitleBarCustomActivity<FindPasswordContract.View, FindPasswordPresenter>(), FindPasswordContract.View {
    override fun getPresenter(): FindPasswordPresenter {
        return FindPasswordPresenter()
    }

    override fun onFindPasswordSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.comp_login_activity_find_password
    }

    override fun initData() {
        mPresenter.FindPassword("")
    }

    override fun initView() {
        super.initView()
    }

    override fun initListener() {
    }

}


