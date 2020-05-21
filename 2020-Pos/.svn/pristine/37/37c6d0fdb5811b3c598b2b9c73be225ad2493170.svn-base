package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.FeedbackBean
import com.epro.pos.mvp.model.bean.InquireShopBean
import io.reactivex.Observable

class FeedbackModel : BaseModel() {

    fun Feedback(owner:String, mobile:String,email:String,problem:String,shopId:String,shopName:String): Observable<FeedbackBean> {
        return getApiSevice().Feedback(FeedbackBean.Send(owner, mobile, email, problem, shopId, shopName))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<FeedbackBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun SelectUserInfo(type:String): Observable<InquireShopBean> {
        return getApiSevice().SelectUserInfo()
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<InquireShopBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}