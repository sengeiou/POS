package com.epro.pos.mvp.presenter

import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.EditStockGoodsPutinDetailContract
import com.epro.pos.mvp.model.EditStockGoodsPutinDetailModel
import com.epro.pos.mvp.model.bean.StockGoodsPutin
import com.mike.baselib.net.exception.ErrorStatus

class EditStockGoodsPutinDetailPresenter : BasePresenter<EditStockGoodsPutinDetailContract.View>(), EditStockGoodsPutinDetailContract.Presenter {

    private val model by lazy { EditStockGoodsPutinDetailModel() }

    fun getProdutDetailByBarcode(productBarCode: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getProductDetailByBarcode(productBarCode)
                .subscribe({ bean ->
                    mRootView?.onGetProductDetailSuccess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }


    fun addStockPutin(stockStatus: Int, stockDesc: String?, stockPoductInfos: ArrayList<StockGoodsPutin.StockProductInfo>, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.addStockPutin(stockStatus, stockDesc, stockPoductInfos)
                .subscribe({ bean ->
                    mRootView?.onAddStockPutinSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun updateStockPutin(stockStatus: Int, stockCode: String, stockDesc: String?, stockPoductInfos: ArrayList<StockGoodsPutin.StockProductInfo>, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.updateStockPutin(stockStatus, stockCode, stockDesc, stockPoductInfos)
                .subscribe({ bean ->
                    mRootView?.onUpdateStockPutinSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun deleteStockPutin(stockCode: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.deleteStockPutin(stockCode)
                .subscribe({ bean ->
                    mRootView?.onDeleteStockPutinSuccess()
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