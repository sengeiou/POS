package com.epro.pos.mvp.presenter

import com.epro.pos.mvp.contract.HistoryRecordContract
import com.epro.pos.mvp.model.HistoryRecordModel
import com.epro.pos.mvp.model.bean.HistoryRecordBean
import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class HistoryRecordPresenter : ListPresenter<HistoryRecordBean.HistoryRecordOneBean,HistoryRecordContract.View>(), HistoryRecordContract.Presenter {

    private val model by lazy { HistoryRecordModel() }

    override fun HistoryRecord(type: String,page:Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.HistoryRecord(page)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows,type)
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