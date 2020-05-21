package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.ModifyPasswordBean
import io.reactivex.Observable

class ModifyPasswordModel :BaseModel(){
    fun modifyPassword(oldPwd:String,newPwd:String,rePwd:String): Observable<ModifyPasswordBean> {
        return getApiSevice().modifyPassword(ModifyPasswordBean.Send(oldPwd, newPwd, rePwd))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<ModifyPasswordBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}
