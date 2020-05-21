package com.epro.pos.mvp.contract

import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

interface CashierReconcDetailContract {

    interface View : IBaseView {
        fun onCashierReconcDetailSuccess()

    }


    interface Presenter : IPresenter<View> {
        fun CashierReconcDetail(type: String)
    }
}