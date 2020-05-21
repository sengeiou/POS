package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.GetPosOrderDetailBean
import com.epro.pos.mvp.model.bean.RefundOrderBean
import com.epro.pos.utils.PosConst
import io.reactivex.Observable

class PosOrderDetailModel : BaseModel() {
    fun getPosOrderDetail(orderSn: String): Observable<GetPosOrderDetailBean> {
        return getApiSevice().getPosOrderDetail(orderSn)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetPosOrderDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun refundOrder(orderSn: String, products: ArrayList<GetPosOrderDetailBean.Product>?, type: String): Observable<RefundOrderBean> {
        when (type) {
            PosConst.REFUND_PART_ORDER -> {
                val productList = ArrayList<RefundOrderBean.Product>()
                for (p in products!!) {
                    val product = RefundOrderBean.Product(p.productId, p.productSn, p.preRefundCount!!)
                    productList.add(product)
                }
                val send = RefundOrderBean.Send(orderSn, productList)
                return getApiSevice().refundPartOrder(send)
                        .flatMap {
                            if (it.code == ErrorStatus.SUCCESS) {
                                return@flatMap Observable.just(it)
                            } else {
                                return@flatMap Observable.error<RefundOrderBean>(ApiException(it.message, it.code))
                            }
                        }
                        .retryWhen(RetryWithDelay())
                        .compose(SchedulerUtils.ioToMain())

            }
            PosConst.REFUND_WHOLE_ORDER -> {
                return getApiSevice().refundWholeOrder(orderSn)
                        .flatMap {
                            if (it.code == ErrorStatus.SUCCESS) {
                                return@flatMap Observable.just(it)
                            } else {
                                return@flatMap Observable.error<RefundOrderBean>(ApiException(it.message, it.code))
                            }
                        }
                        .retryWhen(RetryWithDelay())
                        .compose(SchedulerUtils.ioToMain())
            }
        }
        return getApiSevice().refundWholeOrder(orderSn)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<RefundOrderBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())

    }
}