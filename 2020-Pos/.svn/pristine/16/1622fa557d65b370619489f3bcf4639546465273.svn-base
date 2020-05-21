package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.CashierReconciliationBean
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter

class CashierReconciliationContract {
    interface View : IBaseView {
        fun onCashierReconciliationSucess(result:CashierReconciliationBean.Result)
        fun onExitLogoutSuccess()
        fun changeLogout()
    }

    interface Presenter : IPresenter<View> {
         fun startCashier(type:String,startTime:String,endTime:String)
         fun ExitLogout(type: String)
         fun changeLogout(type:String,startTime:String,endTime:String)
    }
}