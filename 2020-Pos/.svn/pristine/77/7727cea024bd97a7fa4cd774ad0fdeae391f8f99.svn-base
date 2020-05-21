package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.*
import io.reactivex.Observable

class EditStockTakingDetailModel : BaseModel() {
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

    fun addStockTaking(inventoryStatus: Int, remarks: String?, stockPoductInfos: ArrayList<StockTaking.StockProductInfo>): Observable<AddStockTakingBean> {
        val infos = ArrayList<AddStockTakingBean.StockTakingInfo>()
        for (s in stockPoductInfos) {
            val info = AddStockTakingBean.StockTakingInfo(s.productId, s.actualStock,s.sysStock)
            infos.add(info)
        }
        val send = AddStockTakingBean.Send(inventoryStatus, remarks, infos)
        return getApiSevice().addStockTaking(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<AddStockTakingBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun updateStockTaking(inventoryStatus: Int, inventoryCode: String, remarks: String?, stockPoductInfos: ArrayList<StockTaking.StockProductInfo>): Observable<UpdateStockTakingBean> {
        val infos = ArrayList<UpdateStockTakingBean.StockTakingInfo>()
        for (s in stockPoductInfos) {
            val info = UpdateStockTakingBean.StockTakingInfo(s.productId, s.actualStock,s.sysStock)
            infos.add(info)
        }
        val send = UpdateStockTakingBean.Send(inventoryStatus, inventoryCode, remarks, infos)
        return getApiSevice().updateStockTaking(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<UpdateStockTakingBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun deleteStockTaking(inventoryCode: String): Observable<DeleteStockTakingBean> {
        val send = DeleteStockTakingBean.Send(inventoryCode)
        return getApiSevice().deleteStockTaking(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<DeleteStockTakingBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}