package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.HistoryRecordBean
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView
import com.umeng.commonsdk.debug.D

interface HistoryRecordContract {

    interface  View : ListView<HistoryRecordBean.HistoryRecordOneBean> {
        fun onHistoryRecordSuccess(result: HistoryRecordBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun HistoryRecord(type: String,page:Int)
    }
}