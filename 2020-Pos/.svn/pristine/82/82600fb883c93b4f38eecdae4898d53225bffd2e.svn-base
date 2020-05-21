package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.AboutUsContract
import com.epro.pos.mvp.model.AboutUsModel
import com.mike.baselib.net.exception.ErrorStatus

class AboutUsPresenter : BasePresenter<AboutUsContract.View>(), AboutUsContract.Presenter {

    private val model by lazy { AboutUsModel() }

    override fun AboutUs(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.AboutUs()
                .subscribe({ bean ->
                    mRootView?.onAboutUsSuccess()
                    mRootView?.dismissLoading(type,ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}