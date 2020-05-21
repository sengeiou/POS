package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.ModifyPasswordBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface ModifyPasswordContract {

    interface View:IBaseView{
        fun modifyPasswordSuccess(result: String)
    }


    interface Presenter:IPresenter<View>{
        fun modifyPassword(oldPwd:String,newPwd:String,rePwd:String)
    }
}