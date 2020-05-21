package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.ProblemSolveContract
import com.epro.pos.mvp.model.ProblemSolveModel
import com.mike.baselib.net.exception.ErrorStatus

class ProblemSolvePresenter : BasePresenter<ProblemSolveContract.View>(), ProblemSolveContract.Presenter {

    private val model by lazy { ProblemSolveModel() }

    override fun ProblemSolve(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.ProblemSolve()
                .subscribe({ bean ->
                    mRootView?.onProblemSolveSuccess()
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