package com.epro.pos.mvp.presenter

import com.epro.pos.mvp.contract.FinancialManagerContract
import com.epro.pos.mvp.model.FinancialManagerModel
import com.epro.pos.mvp.model.bean.FinancialBill
import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class FinancialManagerPresenter : ListPresenter<FinancialBill, FinancialManagerContract.View>(), FinancialManagerContract.Presenter {

    private val model by lazy { FinancialManagerModel() }

    fun getFinancialBillList(page: Int,
                             startTime: String? = null,
                             endTime: String? = null,
                             billSn: String? = null, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getFinancialBillList(page, startTime, endTime, billSn)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows, type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }


    fun getFinancialScan(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getFinancialScan()
                .subscribe({ bean ->
                    mRootView?.onGetFinancialScanSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }



}
