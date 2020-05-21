package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.SelectGoodsContract
import com.epro.pos.mvp.model.SelectGoodsModel
import com.mike.baselib.net.exception.ErrorStatus

class SelectGoodsPresenter : BasePresenter<SelectGoodsContract.View>(), SelectGoodsContract.Presenter {
    override fun getGoodsCategoryCountList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getGoodsCategoryCountList()
                .subscribe({ bean ->
                    mRootView?.onGetGoodsCategoryCountList(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode,type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { SelectGoodsModel() }

     fun getProductList(page: Int, searchStr: String? = null, goodsTypeId: String? = null,type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getProductList(page,searchStr,goodsTypeId)
                .subscribe({ bean ->
                    mRootView?.onGetProductList(bean.result.rows)
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