package com.epro.pos.mvp.model

import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.GetOrderRecordDetailBean
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class OrderDetailModel : BaseModel() {
    fun getOrderRecordDetail(orderSn:String): Observable<GetOrderRecordDetailBean> {
        return getApiSevice().getOrderRecordDetail(orderSn)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetOrderRecordDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}