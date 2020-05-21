package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.UpdateVersionBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface UpdateVersionContract {

    interface View : IBaseView {
        fun onUpdateVersionSuccess(result: UpdateVersionBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun UpdateVersion(type: String,typeApp:String)
    }
}