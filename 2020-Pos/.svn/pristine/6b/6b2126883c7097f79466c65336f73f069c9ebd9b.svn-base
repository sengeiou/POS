package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.HistoryDetailBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface HistoryDetailContract {

    interface View : IBaseView {
        fun onHistoryDetailSuccess(result: HistoryDetailBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun HistoryDetail(type: String,serialNo:String)
    }
}