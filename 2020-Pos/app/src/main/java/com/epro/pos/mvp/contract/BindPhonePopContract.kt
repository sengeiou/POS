package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.GetUserVfBean
import com.epro.pos.mvp.model.bean.GetVfBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface BindPhonePopContract {

    interface View : IBaseView {
        fun onBindPhonePopSuccess()
        fun onGetUserCodeSuccess(result: GetUserVfBean.Result,type: String)
    }


    interface Presenter : IPresenter<View> {
        fun BindPhonePop(type: String,account:String,code:String,authType:String)
        fun getUserVfCode(authType:String,account:String,getType:Int,type:String)
    }
}