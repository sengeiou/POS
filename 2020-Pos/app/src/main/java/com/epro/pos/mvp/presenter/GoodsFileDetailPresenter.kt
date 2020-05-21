package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.GoodsFileDetailContract
import com.epro.pos.mvp.model.GoodsFileDetailModel
import com.mike.baselib.net.exception.ErrorStatus

class GoodsFileDetailPresenter : BasePresenter<GoodsFileDetailContract.View>(), GoodsFileDetailContract.Presenter {

    private val model by lazy { GoodsFileDetailModel() }

    fun getGoodsFileDetail(goodsId: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getGoodsFileDetail(goodsId)
                .subscribe({ bean ->
                    mRootView?.onGetGoodsFileDetailSuccess(bean.result)
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