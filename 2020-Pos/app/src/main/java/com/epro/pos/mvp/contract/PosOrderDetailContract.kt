package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.GetPosOrderDetailBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface PosOrderDetailContract {

    interface View : IBaseView {
        fun onGetPosOrderDetailSuccess(result:GetPosOrderDetailBean.Result)

        fun onRefundOrderSuccss(result: Any,type: String)

    }


    interface Presenter : IPresenter<View> {
        fun getPosOrderDetail(orderSn:String,type: String)
    }
}