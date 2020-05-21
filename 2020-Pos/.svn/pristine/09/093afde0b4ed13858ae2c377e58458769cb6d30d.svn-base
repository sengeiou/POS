package com.epro.pos.mvp.presenter

import com.epro.pos.mvp.contract.MessageCenterContract
import com.epro.pos.mvp.model.MessageCenterModel
import com.epro.pos.mvp.model.bean.MessageCenterBean
import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class MessageCenterPresenter : ListPresenter<MessageCenterBean.MessageCenterOneBean,MessageCenterContract.View>(), MessageCenterContract.Presenter {

    override fun UpdateReadMessages(type: String, id: Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.updateMessage(id)
                .subscribe({ bean ->
                    mRootView?.onUpdateReadMessageSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { MessageCenterModel() }

    override fun MessageCenter(type: String,userid:Int,page:Int) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.MessageCenter(userid,page)
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