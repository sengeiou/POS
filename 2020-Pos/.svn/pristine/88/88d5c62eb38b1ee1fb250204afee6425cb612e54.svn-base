package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.FindPasswordBean
import com.epro.pos.mvp.model.bean.GetVfBean
import com.epro.pos.mvp.model.bean.LoginBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface FindPasswordContract {

    interface View : IBaseView {
        fun onFindPasswordSuccess(result: FindPasswordBean.Result)

        fun onGetVfCodeSuccess(result: GetVfBean.Result)

        fun loginSuccessed(result: LoginBean.Result)
        fun onBusinessInfoSuccess(result: BusinessBaseInfoBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun findPassword(account:String,password:String,passwordConfirm:String,code: String,authType:String,type: String)

        fun getVfCode(account:String,getType:Int,type:String)

        /**
         * 登录
         */
        fun login(account:String,password:String,loginType:String,type:String)

        //查询商户基本信息
        fun BusinessBaseInfo(type: String)
    }
}