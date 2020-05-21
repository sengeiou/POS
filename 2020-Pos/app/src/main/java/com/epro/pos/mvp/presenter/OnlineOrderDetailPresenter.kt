package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.OnlineOrderDetailContract
import com.epro.pos.mvp.model.OnlineOrderDetailModel
import com.mike.baselib.net.exception.ErrorStatus

class OnlineOrderDetailPresenter : BasePresenter<OnlineOrderDetailContract.View>(), OnlineOrderDetailContract.Presenter {

    private val model by lazy { OnlineOrderDetailModel() }

    fun cancelOnlineOrder(orderSn: String, reason: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.cancelOnlineOrder(orderSn, reason)
                .subscribe({ bean ->
                    mRootView?.onCancelOnlineOrderSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun verifyOnlineOrder(orderSn: String, dcode: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.verifyOnlineOrder(orderSn, dcode)
                .subscribe({ bean ->
                    mRootView?.onVerifyOnlineOrderSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun refundAgainOnlineOrder(orderSn: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.refundAgainOnlineOrder(orderSn)
                .subscribe({ bean ->
                    mRootView?.onRefundAgainOnlineOrderSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

}