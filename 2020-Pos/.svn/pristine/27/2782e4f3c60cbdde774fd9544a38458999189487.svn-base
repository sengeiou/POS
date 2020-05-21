package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.HistoryDetailContract
import com.epro.pos.mvp.model.HistoryDetailModel
import com.mike.baselib.net.exception.ErrorStatus

class HistoryDetailPresenter : BasePresenter<HistoryDetailContract.View>(), HistoryDetailContract.Presenter {

    private val model by lazy { HistoryDetailModel() }

    override fun HistoryDetail(type: String,serialNo:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.HistoryDetail(serialNo)
                .subscribe({ bean ->
                    mRootView?.onHistoryDetailSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.handleException(throwable), ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

}