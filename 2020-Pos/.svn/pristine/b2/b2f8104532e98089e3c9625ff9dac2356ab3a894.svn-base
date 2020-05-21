package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.CancelOnlineOrderBean
import com.epro.pos.mvp.model.bean.RefundAgainOnlineOrderBean
import com.epro.pos.mvp.model.bean.VerifyOnlineOrderBean
import io.reactivex.Observable

class OnlineOrderDetailModel : BaseModel() {
    fun cancelOnlineOrder(orderSn: String, reason: String): Observable<CancelOnlineOrderBean> {
        val send = CancelOnlineOrderBean.Send(orderSn, reason)
        return getApiSevice().cancelOnlineOrder(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<CancelOnlineOrderBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun verifyOnlineOrder(orderSn: String, dcode: String): Observable<VerifyOnlineOrderBean> {
        val send = VerifyOnlineOrderBean.Send(orderSn, dcode)
        return getApiSevice().verifyOnlineOrder(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<VerifyOnlineOrderBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
    fun refundAgainOnlineOrder(orderSn: String): Observable<RefundAgainOnlineOrderBean> {
        return getApiSevice().refundAgainOnlineOrder(orderSn)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<RefundAgainOnlineOrderBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}