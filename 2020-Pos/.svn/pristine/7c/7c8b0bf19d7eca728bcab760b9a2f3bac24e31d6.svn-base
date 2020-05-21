package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.InquireShopBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.epro.pos.mvp.model.bean.LoginBean
import com.epro.pos.mvp.model.bean.PersonCenterBean

/**
 * Created by xuhao on 2017/11/30.
 * desc: 契约类
 */
interface LoginContract {

    interface View:IBaseView{
        /**
         * 登录成功
         */
        fun loginSuccess(result: LoginBean.Result)
        fun onPersonCenterSuccess(result: PersonCenterBean.Result)
    }


    interface Presenter:IPresenter<View>{
        /**
         * 登录
         */
        fun login(account:String,password:String,loginType:String,type:String)
        fun PersonCenter(type: String)
    }
}