package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.FeedbackContract
import com.epro.pos.mvp.model.FeedbackModel
import com.mike.baselib.net.exception.ErrorStatus

class FeedbackPresenter : BasePresenter<FeedbackContract.View>(), FeedbackContract.Presenter {

    private val model by lazy { FeedbackModel() }

    override fun Feedback(type: String,owner:String, mobile:String,email:String,problem:String,shopId:String,shopName:String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.Feedback(owner, mobile, email, problem,shopId, shopName)
                .subscribe({ bean ->
                    mRootView?.onFeedbackSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    override fun selectUserInfo(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.SelectUserInfo(type)
                .subscribe({ bean ->
                    mRootView?.selectUserInfoSuccess(bean.result)
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