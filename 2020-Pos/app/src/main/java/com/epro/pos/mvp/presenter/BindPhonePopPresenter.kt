package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.BindPhonePopContract
import com.epro.pos.mvp.model.BindPhonePopModel
import com.mike.baselib.net.exception.ErrorStatus

class BindPhonePopPresenter : BasePresenter<BindPhonePopContract.View>(), BindPhonePopContract.Presenter {


    private val model by lazy { BindPhonePopModel() }

    override fun BindPhonePop(type: String,account:String,code:String,authType:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.BindPhonePop(account,code,authType)
                .subscribe({ bean ->
                    mRootView?.onBindPhonePopSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
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