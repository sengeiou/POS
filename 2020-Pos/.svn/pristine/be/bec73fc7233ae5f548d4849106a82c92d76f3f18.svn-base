package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.CreateGoodsContract
import com.epro.pos.mvp.model.CreateGoodsModel
import com.epro.pos.mvp.model.bean.CreateGoodsBean
import com.epro.pos.mvp.model.bean.Product
import com.mike.baselib.net.exception.ErrorStatus

class CreateGoodsPresenter : BasePresenter<CreateGoodsContract.View>(), CreateGoodsContract.Presenter {

    private val model by lazy { CreateGoodsModel() }

    fun createGoods(product: CreateGoodsBean.Product, goodsName: String, goodsTypeId: String, goodsTypeName: String, buyingPrice: String, retailPrice: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.createGoods(product, goodsName, goodsTypeId,goodsTypeName,buyingPrice,retailPrice)
                .subscribe({ bean ->
                    mRootView?.onCreateGoodsSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    override fun getGoodsCategoryList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getGoodsCategoryList()
                .subscribe({ bean ->
                    mRootView?.onGetGoodsCategoryList(bean.result.rows)
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