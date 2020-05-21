package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.OrderDetailContract
import com.epro.pos.mvp.model.OrderDetailModel
import com.mike.baselib.net.exception.ErrorStatus

class OrderDetailPresenter : BasePresenter<OrderDetailContract.View>(), OrderDetailContract.Presenter {

    private val model by lazy { OrderDetailModel() }

    fun getOrderRecordDetail(orderSn: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getOrderRecordDetail(orderSn)
                .subscribe({ bean ->
                    mRootView?.onGetOrderRecordDetailSuccess(bean.result)
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