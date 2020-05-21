package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.UpdateVersionContract
import com.epro.pos.mvp.model.UpdateVersionModel
import com.mike.baselib.net.exception.ErrorStatus

class UpdateVersionPresenter : BasePresenter<UpdateVersionContract.View>(), UpdateVersionContract.Presenter {

    private val model by lazy { UpdateVersionModel() }

    override fun UpdateVersion(type: String,typeApp:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.UpdateVersion(typeApp)
                .subscribe({ bean ->
                    mRootView?.onUpdateVersionSuccess(bean.result)
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