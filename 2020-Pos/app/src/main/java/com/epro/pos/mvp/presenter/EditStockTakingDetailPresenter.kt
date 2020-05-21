package com.epro.pos.mvp.presenter

import com.epro.pos.mvp.contract.EditStockTakingDetailContract
import com.epro.pos.mvp.model.EditStockTakingDetailModel
import com.epro.pos.mvp.model.bean.StockTaking
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle

class EditStockTakingDetailPresenter : BasePresenter<EditStockTakingDetailContract.View>(), EditStockTakingDetailContract.Presenter {

    private val model by lazy { EditStockTakingDetailModel() }

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


    fun addStockTaking(inventoryStatus: Int, remarks: String?, stockPoductInfos: ArrayList<StockTaking.StockProductInfo>, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.addStockTaking(inventoryStatus, remarks, stockPoductInfos)
                .subscribe({ bean ->
                    mRootView?.onAddStockTakingSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun updateStockTaking(inventoryStatus: Int, inventoryCode: String, remarks: String?, stockPoductInfos: ArrayList<StockTaking.StockProductInfo>, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.updateStockTaking(inventoryStatus, inventoryCode, remarks, stockPoductInfos)
                .subscribe({ bean ->
                    mRootView?.onUpdateStockTakingSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG, ErrorStatus.SUCCESS, type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun deleteStockTaking(stockCode: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.deleteStockTaking(stockCode)
                .subscribe({ bean ->
                    mRootView?.onDeleteStockTakingSuccess()
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