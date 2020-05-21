package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.OrderRecord
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface OrderDetailContract {

    interface View : IBaseView {
        fun onGetOrderRecordDetailSuccess(order:OrderRecord)

    }


    interface Presenter : IPresenter<View> {
    }
}