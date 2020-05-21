package com.epro.comp.login.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.epro.comp.login.mvp.model.bean.LoginBean

/**
 * Created by xuhao on 2017/11/30.
 * desc: 契约类
 */
interface LoginContract {

    interface View:IBaseView{
        /**
         * 登录成功
         */
        fun loginSuccess(user: LoginBean.LoginUser)

    }


    interface Presenter:IPresenter<View>{
        /**
         * 登录
         */
        fun login(code:String,loginname:String,password:String,type:String)
    }
}