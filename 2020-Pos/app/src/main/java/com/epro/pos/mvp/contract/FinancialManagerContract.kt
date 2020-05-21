package com.epro.pos.mvp.contract

import com.epro.pos.mvp.model.bean.FinancialBill
import com.epro.pos.mvp.model.bean.GetFinancialScanBean
import com.mike.baselib.base.IPresenter
import com.mike.baselib.base.ListView

interface FinancialManagerContract {

    interface View : ListView<FinancialBill> {
       fun onGetFinancialBillDetailSuccess(bill:FinancialBill)

       fun onGetFinancialScanSuccess(result: GetFinancialScanBean.Result)
    }


    interface Presenter : IPresenter<View> {
    }
}