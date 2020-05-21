package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.SystemSettingsContract
import com.epro.pos.mvp.model.SystemSettingsModel
import com.mike.baselib.net.exception.ErrorStatus

class SystemSettingsPresenter : BasePresenter<SystemSettingsContract.View>(), SystemSettingsContract.Presenter {

    private val model by lazy { SystemSettingsModel() }

    override fun SystemSettings(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.SystemSettings()
                .subscribe({ bean ->
                    mRootView?.onSystemSettingsSuccess()
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