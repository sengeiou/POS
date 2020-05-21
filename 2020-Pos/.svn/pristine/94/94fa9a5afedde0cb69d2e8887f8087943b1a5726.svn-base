package com.epro.pos.mvp.presenter

import com.mike.baselib.base.ListPresenter
import com.mike.baselib.net.exception.ExceptionHandle
import com.epro.pos.mvp.contract.ShowListContract
import com.epro.pos.mvp.model.ShowListModel
import com.epro.pos.mvp.model.bean.GoodsFile
import com.epro.pos.mvp.model.bean.UpperShelfGoods
import com.epro.pos.utils.PosConst
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.ext_FirstFalse

class ShowListPresenter<D> : ListPresenter<D, ShowListContract.View<D>>(), ShowListContract.Presenter<D> {

    override fun getGoodsCategoryList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getGoodsCategoryList()
                .subscribe({ bean ->
                    mRootView?.onGetGoodsCategoryListSuccess(bean.result.rows)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    private val model by lazy { ShowListModel() }

    override fun ShowList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.ShowList<D>()
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getOrderRecordList(page: Int, startTime: String? = null, endTime: String? = null, orderSn: String? = null, tradeType: String? = null, orderType: String? = null, payType: String? = null, cashierId: String? = null, orderStatus: String? = null, puserId: String? = null, productSn: String? = null, type: String = "") {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getOrderRecordList(page, startTime, endTime, orderSn, tradeType, orderType, payType, cashierId, orderStatus, puserId, productSn)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getGoodsFileList(page: Int, searchStr: String? = null, goodsTypeId: String? = null, goodsBinding: String? = null, type: String = "") {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getGoodsFileList(page, searchStr, goodsTypeId, goodsBinding)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getOrderGoodsRecordList(page: Int, startTime: String? = null, endTime: String? = null, orderSn: String? = null, tradeType: String? = null, orderType: String? = null, payType: String? = null,cashierId: String? = null, orderStatus: String? = null, puserId: String? = null, productSn: String? = null, categoryId: String? = null, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getOrderGoodsRecordList(page, startTime, endTime, orderSn, tradeType, orderType,payType, cashierId, orderStatus, puserId, productSn, categoryId)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getOrderSalesRankingGoodsList(page: Int, startTime: String? = null, endTime: String? = null, tradeType: String? = null, productSn: String? = null, categoryId: String? = null, orderSort:String?=null,type: String = "") {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getOrderSalesRankingGoodsList(page, startTime, endTime, tradeType, productSn, categoryId,orderSort)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getGrossProfitGatherList(page: Int, startTime: String? = null, endTime: String? = null, tradeType: String? = null, productSn: String? = null, categoryId: String? = null, type: String = "") {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getGrossProfitGatherList(page, startTime, endTime, tradeType, productSn, categoryId)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getCashierReconciliationList(page: Int,
                                     startTime: String? = null,
                                     endTime: String? = null, statementId: String? = null, cashierId: String? = null, type: String = "") {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getCashierReconciliationList(page, startTime, endTime, statementId, cashierId)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getStockQueryList(page: Int, searchStr: String? = null, goodsTypeId: String? = null, goodsBinding: String? = null, sStockEarly: Boolean = false, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getStockQueryList(page, searchStr, goodsTypeId, goodsBinding, sStockEarly)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)

    }

    fun getStockGoodsPutinList(page: Int,
                               stratDate: String? = null,
                               endDate: String? = null,
                               stockCode: String? = null,
                               stockStatus: String? = null, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getStockGoodsPutinList(page, stratDate, endDate, stockCode, stockStatus)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)

    }

    fun getStockTakingList(page: Int,
                           stratDate: String? = null,
                           endDate: String? = null,
                           inventoryStatus: String? = null,
                           inventoryCode: String? = null, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getStockTakingList(page, stratDate, endDate, inventoryStatus, inventoryCode)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getStockOutinDetailList(page: Int,
                                stratDate: String? = null,
                                endDate: String? = null,
                                searchStr: String? = null,
                                stockSource: String? = null, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getStockOutinDetailList(page, stratDate, endDate, searchStr, stockSource)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getPosTradeList(page: Int, startTime: String? = null, endTime: String? = null, orderSn: String? = null, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getPosTradeList(page, startTime, endTime, orderSn)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getFinancialBillDetail(billSn: String, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getFinancialBillDetail(billSn)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getUpperShelfGoodsList(page: Int, searchStr: String? = null, type: String, status: Int? = PosConst.GOODS_STATUS_UPPER_SHELF) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getUpperShelfGoodsList(page, searchStr, status)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }


    fun lowerShelfGoods(uGoods: ArrayList<UpperShelfGoods>, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.lowerShelfGoods(uGoods)
                .subscribe({ bean ->
                    mRootView?.onLowerShelfGoodsSuccess()
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

    fun getOnlineOrdersList(page: Int, orderStatus: Int, mobile: String?, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getOnlineOrdersList(page, orderStatus, mobile)
                .subscribe({ bean ->
                    mRootView?.getListDataSuccess(bean.result.rows as List<D>, type,bean.result.total)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }
    fun getCashierList(type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.getCashierList()
                .subscribe({ bean ->
                    mRootView?.onGetCashierListSuccsess(bean.result)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }
    fun deleteGoodsFile(goodsFiles:ArrayList<GoodsFile>, type: String) {
        checkViewAttached()
        mRootView?.showLoading(type)
        val disposable = model.deleteGoodsFile(goodsFiles)
                .subscribe({ bean ->
                    mRootView?.onDeleteGoodsFileSuccess(bean.result.failList,bean.result.successList)
                    mRootView?.dismissLoading(ErrorStatus.SUCCESS_MSG,ErrorStatus.SUCCESS,type)
                }, { throwable ->
                    //处理异常
                    ExceptionHandle.handleException(throwable)
                    mRootView?.showError(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                    mRootView?.dismissLoading(ExceptionHandle.errorMsg, ExceptionHandle.errorCode, type)
                })
        addSubscription(disposable)
    }

}
