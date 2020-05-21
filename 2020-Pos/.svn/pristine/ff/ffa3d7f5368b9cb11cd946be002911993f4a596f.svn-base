package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.PosOrderDetailContract
import com.epro.pos.mvp.model.PosOrderDetailModel
import com.epro.pos.mvp.model.bean.GetPosOrderDetailBean
import com.epro.pos.utils.PosConst
import com.mike.baselib.net.exception.ErrorStatus

class PosOrderDetailPresenter : BasePresenter<PosOrderDetailContract.View>(), PosOrderDetailContract.Presenter {

    private val model by lazy { PosOrderDetailModel() }

    override fun getPosOrderDetail(orderSn: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getPosOrderDetail(orderSn)
                .subscribe({ bean ->
                    mRootView?.onGetPosOrderDetailSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun refundOrder(orderSn: String, products: ArrayList<GetPosOrderDetailBean.Product>?, type: String) {
        mRootView?.showLoading(type)
        val disposable = model.refundOrder(orderSn, products, type)
                .subscribe({ bean ->
                    mRootView?.onRefundOrderSuccss(bean.result, type)
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