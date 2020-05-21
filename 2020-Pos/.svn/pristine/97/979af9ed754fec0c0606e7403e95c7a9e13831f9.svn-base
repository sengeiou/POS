package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.*
import com.epro.pos.utils.PosConst
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

class FinancialManagerModel : BaseModel() {
    fun getFinancialBillList(page: Int,
                             startTime: String? = null,
                             endTime: String? = null,
                             billSn: String? = null): Observable<GetCommonListBean<FinancialBill>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.FinancialBillSend(p, startTime, endTime, billSn)
        return getApiSevice().getFinancialBillList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<FinancialBill>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


    fun getFinancialScan(): Observable<GetFinancialScanBean> {
        return getApiSevice().getFinancialScan()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetFinancialScanBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}