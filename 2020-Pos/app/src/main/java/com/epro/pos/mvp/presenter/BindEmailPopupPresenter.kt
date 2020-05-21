package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.BindEmailPopupContract
import com.epro.pos.mvp.model.BindEmailPopupModel
import com.mike.baselib.net.exception.ErrorStatus

class BindEmailPopupPresenter : BasePresenter<BindEmailPopupContract.View>(), BindEmailPopupContract.Presenter {

    private val model by lazy { BindEmailPopupModel() }

    override fun BindEmailPopup(type: String,account:String,code:String,authType:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.BindEmailPopup(account,code,authType)
                .subscribe({ bean ->
                    mRootView?.onBindEmailPopupSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    //解绑或者修改密码
    override fun getUserVfCode(authType:String,account: String, getType: Int, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getUserVfCode(authType,account,getType)
                .subscribe({
                    bean->
                    mRootView?.onGetUserCodeSuccess(bean.result,type)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}