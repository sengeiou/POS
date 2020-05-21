package com.epro.pos.mvp.presenter

import com.epro.pos.mvp.contract.CashierReconciliationContract
import com.epro.pos.mvp.model.CashierReconciliationModel
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.umeng.commonsdk.debug.D

class CashierReconciliationPresenter: BasePresenter<CashierReconciliationContract.View>() , CashierReconciliationContract.Presenter {


    private val model by lazy { CashierReconciliationModel() }
    override fun startCashier(type: String, startTime: String, endTime: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.CashierReconc(startTime, endTime)
                .subscribe({ bean ->
                    mRootView?.onCashierReconciliationSucess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    //退出登录
    override fun ExitLogout(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.Logout()
                .subscribe({ bean ->
                    mRootView?.onExitLogoutSuccess()
                    mRootView?.dismissLoading(type, ErrorStatus.SUCCESS,ErrorStatus.SUCCESS_MSG)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    //交班推出系统
    override fun changeLogout(type: String, startTime: String, endTime: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.ChangeLogout(startTime, endTime)
                .subscribe({ bean ->
                    mRootView?.changeLogout()
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