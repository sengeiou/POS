package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.BindEmailPopupBean
import com.epro.pos.mvp.model.bean.GetUserVfBean
import com.epro.pos.mvp.model.bean.GetValidUserBean
import com.epro.pos.mvp.model.bean.GetVfBean
import io.reactivex.Observable

class BindEmailPopupModel : BaseModel() {
    fun BindEmailPopup(account:String,code:String,authType:String): Observable<BindEmailPopupBean> {
        return getApiSevice().BindEmailPopup(BindEmailPopupBean.Send(account, code, authType))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<BindEmailPopupBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    /**
     * 获取验证码
     */
    fun getVfCode(account:String,type:Int): Observable<GetVfBean> {
        return getApiSevice().getVfcode(GetVfBean.Send(account,type))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<GetVfBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    //获取验证码2
    fun getUserVfCode(authType:String,account:String,type:Int): Observable<GetUserVfBean> {
        return getApiSevice().getUserVfcode(GetUserVfBean.Send(authType,account,type))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<GetUserVfBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getValidUser(account: String): Observable<GetValidUserBean> {
        return getApiSevice().getValidUser(GetValidUserBean.Send(account))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<GetValidUserBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}