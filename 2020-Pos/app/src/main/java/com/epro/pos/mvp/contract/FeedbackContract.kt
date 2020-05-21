package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.FeedbackBean
import com.epro.pos.mvp.model.bean.InquireShopBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface FeedbackContract {

    interface View : IBaseView {
        fun onFeedbackSuccess(result: FeedbackBean.Result)
        fun selectUserInfoSuccess(result: InquireShopBean.Result)
    }


    interface Presenter : IPresenter<View> {
        fun Feedback(type: String,owner:String, mobile:String,email:String,problem:String,shopId:String,shopName:String)
        fun selectUserInfo(type:String)
    }
}