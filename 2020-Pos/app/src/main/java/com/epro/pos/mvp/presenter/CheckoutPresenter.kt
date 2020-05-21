package com.epro.pos.mvp.presenter

import com.epro.pos.listener.RetryWithDelay
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.CheckoutContract
import com.epro.pos.mvp.model.CheckoutModel
import com.epro.pos.mvp.model.bean.CartProduct
import com.epro.pos.mvp.model.bean.CheckOrderPayBean
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class CheckoutPresenter : BasePresenter<CheckoutContract.View>(), CheckoutContract.Presenter {

    private val model by lazy { CheckoutModel() }

    fun createOrder(orderType: Int,
                    totalCount: Int,
                    totalAmount: String,
                    totalDiscount: Float,
                    discountPrice: String,
                    payType: Int,
                    products: ArrayList<CartProduct>,
                    id: String? = null,
                    remark: String? = null,
                    payBarCode: String? = null, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.createOrder(orderType, totalCount, totalAmount, totalDiscount, discountPrice, payType, products, id, remark, payBarCode)
                .subscribe({ bean ->
                    mRootView?.onCreateOrderSuccess(bean.payResult!!)
                    //mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }


    fun checkOrderPay(orderSn: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.checkOrderPay(orderSn)
                .subscribe({ bean ->
                    mRootView?.onCheckOrderPaySuccess(bean.checkResult!!.payStatus)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun cancelPosTrade(orderSn: String,type: String){
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.cancelPosTrade(orderSn)
                .subscribe({ bean ->
                    mRootView?.onCancelPosTradeSuccess()
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