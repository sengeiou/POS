package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.PendOrderListContract
import com.epro.pos.mvp.model.PendOrderListModel
import com.mike.baselib.net.exception.ErrorStatus

class PendOrderListPresenter : BasePresenter<PendOrderListContract.View>(), PendOrderListContract.Presenter {

    private val model by lazy { PendOrderListModel() }

    override fun PendOrderList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.PendOrderList()
                .subscribe({ bean ->
                    mRootView?.onPendOrderListSuccess()
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