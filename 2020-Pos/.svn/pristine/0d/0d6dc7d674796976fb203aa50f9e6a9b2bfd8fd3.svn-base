package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.ModifyPasswordContract
import com.epro.pos.mvp.model.ModifyPasswordModel
import com.epro.pos.mvp.model.bean.ModifyPasswordBean
import com.mike.baselib.net.exception.ErrorStatus

class ModifyPasswordPresenter: BasePresenter<ModifyPasswordContract.View>(),ModifyPasswordContract.Presenter {

    private val model by lazy { ModifyPasswordModel() }

    override fun modifyPassword(oldPwd:String,newPwd:String,rePwd:String) {
        checkViewAttached()
        mRootView?.showLoading("")
        val disposable =model.modifyPassword(oldPwd,newPwd,rePwd)
                .subscribe({
                    bean->
                    mRootView?.modifyPasswordSuccess(bean.message)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,"")
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,"")
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,"")
                })
        addSubscription(disposable)
    }

}