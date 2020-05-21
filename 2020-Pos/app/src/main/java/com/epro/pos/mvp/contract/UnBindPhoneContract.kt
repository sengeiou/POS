package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.GetUserVfBean
import com.epro.pos.mvp.model.bean.GetVfBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface UnBindPhoneContract {

    interface View : IBaseView {
        fun onUnBindPhoneSuccess()
        fun onGetVfCodeSuccess(result: GetVfBean.Result)
        fun onGetUserCodeSuccess(result: GetUserVfBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun UnBindPhone(type: String,account:String,code:String,authType:String)
        fun getVfCode(account:String,getType:Int,type:String)
        fun getUserVfCode(authType:String,account:String,getType:Int,type:String)
    }
}