package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.UnBindPhoneContract
import com.epro.pos.mvp.model.UnBindPhoneModel
import com.mike.baselib.net.exception.ErrorStatus

class UnBindPhonePresenter : BasePresenter<UnBindPhoneContract.View>(), UnBindPhoneContract.Presenter {


    private val model by lazy { UnBindPhoneModel() }

    override fun UnBindPhone(type: String,account:String,code:String,authType:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.UnBindPhone(account,code,authType)
                .subscribe({ bean ->
                    mRootView?.onUnBindPhoneSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

        override fun getVfCode(account: String, getType: Int, type: String) {
            checkViewAttached()
            mRootView?.showLoading(type)
            val disposable = model.getVfCode(account, getType)
                    .subscribe({ bean ->
                        mRootView?.onGetVfCodeSuccess(bean.result)
                        mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
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
                    mRootView?.onGetUserCodeSuccess(bean.result)
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