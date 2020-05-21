package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.MessageCenterBean
import com.epro.pos.mvp.model.bean.updateReadBean
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView
import com.umeng.commonsdk.debug.D

interface MessageCenterContract {

    interface View : ListView<MessageCenterBean.MessageCenterOneBean> {
        fun onUpdateReadMessageSuccess(result: updateReadBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun MessageCenter(type: String,userid:Int,page:Int)
        fun UpdateReadMessages(type:String,id:Int)
    }
}