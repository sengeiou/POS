package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.MessageCenterBean
import com.epro.pos.mvp.model.bean.PersonCenterBean
import com.epro.pos.mvp.model.bean.updateReadBean
import io.reactivex.Observable

class MessageCenterModel : BaseModel() {
    fun MessageCenter(userid:Int,page:Int): Observable<MessageCenterBean> {
        var mK= MessageCenterBean.mPage(page)
        return getApiSevice().MessageCenter(MessageCenterBean.Send(userid,mK))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<MessageCenterBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun updateMessage(id:Int): Observable<updateReadBean> {
        return getApiSevice().UpdateRead(updateReadBean.Send(id))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<updateReadBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}