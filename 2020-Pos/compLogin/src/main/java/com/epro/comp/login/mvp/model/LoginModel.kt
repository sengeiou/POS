package com.epro.comp.login.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.comp.login.mvp.model.bean.LoginBean
import io.reactivex.Observable

class LoginModel :BaseModel(){
    /**
     * 登录
     */
    fun login(code:String,loginname:String,password:String): Observable<LoginBean> {
        return getApiSevice().login(LoginBean.Send(loginname))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<LoginBean>(ApiException(it.message,it.code))
                    }
                }
                //.retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}
