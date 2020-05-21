package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.ArrangeDistributionBean
import com.epro.pos.mvp.model.bean.GetResultListBean
import com.epro.pos.mvp.model.bean.OnlineOrder
import com.epro.pos.mvp.model.bean.Shopper
import io.reactivex.Observable
import retrofit2.http.Body

class SelectShopperModel : BaseModel() {
    fun getShopperList(): Observable<GetResultListBean<Shopper>> {
        return getApiSevice().getShopperList()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetResultListBean<Shopper>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun arrangeDistribution(courierId: String, courierName: String, orderSns: ArrayList<String>): Observable<ArrangeDistributionBean> {
        val send = ArrangeDistributionBean.Send(courierId, courierName, orderSns)
        return getApiSevice().arrangeDistribution(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<ArrangeDistributionBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}