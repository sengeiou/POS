package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.CashierReconcDetailContract
import com.epro.pos.mvp.model.CashierReconcDetailModel
import com.mike.baselib.net.exception.ErrorStatus

class CashierReconcDetailPresenter : BasePresenter<CashierReconcDetailContract.View>(), CashierReconcDetailContract.Presenter {

    private val model by lazy { CashierReconcDetailModel() }

    override fun CashierReconcDetail(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.CashierReconcDetail()
                .subscribe({ bean ->
                    mRootView?.onCashierReconcDetailSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}