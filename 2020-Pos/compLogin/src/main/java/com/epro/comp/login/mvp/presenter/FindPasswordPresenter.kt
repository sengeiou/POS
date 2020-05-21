package com.epro.comp.login.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.comp.login.mvp.contract.FindPasswordContract
import com.epro.comp.login.mvp.model.FindPasswordModel
import com.mike.baselib.net.exception.ErrorStatus

class FindPasswordPresenter : BasePresenter<FindPasswordContract.View>(), FindPasswordContract.Presenter {

    private val model by lazy { FindPasswordModel() }

    override fun FindPassword(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.FindPassword()
                .subscribe({ bean ->
                    mRootView?.onFindPasswordSuccess()
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS, ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}