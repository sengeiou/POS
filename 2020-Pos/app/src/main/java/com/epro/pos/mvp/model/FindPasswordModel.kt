package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.FindPasswordBean
import com.epro.pos.mvp.model.bean.GetVfBean
import com.epro.pos.mvp.model.bean.LoginBean
import com.mike.baselib.net.exception.ExceptionHandle
import io.reactivex.Observable

class FindPasswordModel : BaseModel() {
    fun findPassword( account:String,password:String,passwordConfirm:String,code: String,authType:String): Observable<FindPasswordBean> {
        val send=FindPasswordBean.Send(account,password,passwordConfirm,code,authType)
        return getApiSevice().findPassword(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<FindPasswordBean>(ApiException(it.message, it.code))
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


    /**
     * 登录
     */
    fun login(account:String,password:String,type:String): Observable<LoginBean> {
        return getApiSevice().login(LoginBean.Send(account,password))
                .flatMap {
                    if(it.code== ErrorStatus.SUCCESS){
                        return@flatMap  Observable.just(it)
                    }else{
                        return@flatMap Observable.error<LoginBean>(ApiException(it.message,it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    //查询商户基本资料
    fun businessBaseInfo(): Observable<BusinessBaseInfoBean> {
        return getApiSevice().BusinessBaseInfo()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<BusinessBaseInfoBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}