package com.epro.comp.login.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
//import com.mike.comp.login.listener.RetryWithDelay
import com.epro.comp.login.mvp.model.bean.FindPasswordBean
import io.reactivex.Observable

class FindPasswordModel : BaseModel() {
    fun FindPassword(): Observable<FindPasswordBean> {
        return getApiSevice().FindPassword()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<FindPasswordBean>(ApiException(it.message, it.code))
                    }
                }
               // .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


}