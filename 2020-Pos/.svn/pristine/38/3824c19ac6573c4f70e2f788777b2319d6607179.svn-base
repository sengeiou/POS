package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.HelpCenterContract
import com.epro.pos.mvp.model.HelpCenterModel
import com.mike.baselib.net.exception.ErrorStatus

class HelpCenterPresenter : BasePresenter<HelpCenterContract.View>(), HelpCenterContract.Presenter {

    private val model by lazy { HelpCenterModel() }

    override fun HelpCenter(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.HelpCenter()
                .subscribe({ bean ->
                    mRootView?.onHelpCenterSuccess()
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