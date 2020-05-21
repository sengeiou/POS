package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.HistoryRecordBean
import io.reactivex.Observable

class HistoryRecordModel : BaseModel() {
    fun HistoryRecord(page:Int): Observable<HistoryRecordBean> {
        var p = HistoryRecordBean.PageList(page)
        return getApiSevice().HistoryRecord(HistoryRecordBean.Send(p))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<HistoryRecordBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}