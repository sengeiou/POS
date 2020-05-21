package com.epro.pos.mvp.model

import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.HistoryDetailBean
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class HistoryDetailModel : BaseModel() {
    fun HistoryDetail(serialNo:String): Observable<HistoryDetailBean> {
        return getApiSevice().HistoryDetail(serialNo = serialNo)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<HistoryDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}