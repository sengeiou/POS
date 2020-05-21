package com.epro.pos.mvp.presenter

import com.epro.pos.mvp.contract.CartContract
import com.epro.pos.mvp.model.CartModel
import com.epro.pos.mvp.model.CashierModel
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class CartPresenter : BasePresenter<CartContract.View>(), CartContract.Presenter {

    fun searchProduct(searchStr: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.searchProduct(searchStr)
                .subscribe({ bean ->
                    mRootView?.onSearchProductSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { CartModel() }


}