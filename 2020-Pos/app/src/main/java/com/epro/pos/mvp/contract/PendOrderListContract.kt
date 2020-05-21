package com.epro.pos.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface PendOrderListContract {

    interface View : IBaseView {
        fun onPendOrderListSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun PendOrderList(type: String)
    }
}