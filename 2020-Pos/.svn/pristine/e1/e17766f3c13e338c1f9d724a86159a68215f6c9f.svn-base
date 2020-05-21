package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.CreateOrderBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface CheckoutContract {

    interface View : IBaseView {
        fun onCreateOrderSuccess(result: CreateOrderBean.Result)
        fun onCheckOrderPaySuccess(payStatus:Int)
        fun onCancelPosTradeSuccess()

    }


    interface Presenter : IPresenter<View> {
        //fun createOrder(type: String)
    }
}