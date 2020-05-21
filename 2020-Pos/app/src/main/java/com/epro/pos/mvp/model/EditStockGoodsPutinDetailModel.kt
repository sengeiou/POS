package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.*
import io.reactivex.Observable

class EditStockGoodsPutinDetailModel : BaseModel() {
    fun getProductDetailByBarcode(productBarCode: String): Observable<GetProcuctDetailBean> {
        return getApiSevice().getProdutDetailByBarcode(productBarCode)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetProcuctDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun addStockPutin(stockStatus: Int, stockDesc: String?, stockPoductInfos: ArrayList<StockGoodsPutin.StockProductInfo>): Observable<AddStockPutinBean> {
        val infos = ArrayList<AddStockPutinBean.StockInfo>()
        for (s in stockPoductInfos) {
            val info = AddStockPutinBean.StockInfo(s.productId!!, s.stockNumber, s.stockType, s.buyingPrice)
            infos.add(info)
        }
        val send = AddStockPutinBean.Send(stockStatus, stockDesc, infos)
        return getApiSevice().addStockPutin(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<AddStockPutinBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun updateStockPutin(stockStatus: Int, stockCode: String, stockDesc: String?, stockPoductInfos: ArrayList<StockGoodsPutin.StockProductInfo>): Observable<UpdateStockPutinBean> {
        val infos = ArrayList<UpdateStockPutinBean.StockInfo>()
        for (s in stockPoductInfos) {
            val info = UpdateStockPutinBean.StockInfo(s.productId!!, s.stockNumber, s.stockType,s.buyingPrice)
            infos.add(info)
        }
        val send = UpdateStockPutinBean.Send(stockStatus, stockCode, stockDesc, infos)
        return getApiSevice().updateStockPutin(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<UpdateStockPutinBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun deleteStockPutin(stockCode: String): Observable<DeleteStockPutinBean> {
        val send = DeleteStockPutinBean.Send(stockCode)
        return getApiSevice().deleteStockPutin(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<DeleteStockPutinBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}