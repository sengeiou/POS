package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.epro.pos.mvp.contract.HomeContract
import com.epro.pos.mvp.model.HomeModel
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    private val model by lazy { HomeModel() }
    fun getMyBusiness(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getMybusiness()
                .subscribe({ bean ->
                    mRootView?.onGetMybusinessSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }
    fun getWaitdoList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getWaitdoList()
                .subscribe({ bean ->
                    mRootView?.onGetWaitdoListSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }
    fun getHomeSales(timeType:String,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getHomeSales(timeType)
                .subscribe({ bean ->
                    mRootView?.onGetHomeSalesSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }
}