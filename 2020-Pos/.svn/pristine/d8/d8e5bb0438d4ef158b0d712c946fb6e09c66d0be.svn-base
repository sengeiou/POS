package com.epro.pos.mvp.model

import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.CashierReconciliationBean
import com.epro.pos.mvp.model.bean.ChangeUserLogoutBean
import com.epro.pos.mvp.model.bean.LogoutBean
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class CashierReconciliationModel:BaseModel() {

    fun CashierReconc(startTime:String,endTime:String): Observable<CashierReconciliationBean> {
        return getApiSevice().CashierReconc(CashierReconciliationBean.Send(startTime, endTime))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<CashierReconciliationBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    //退出登录
    fun Logout():Observable<LogoutBean>{
        return getApiSevice().logoutSystem()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<LogoutBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    //交班推出系统
    fun ChangeLogout(startTime:String,endTime:String): Observable<ChangeUserLogoutBean> {
        return getApiSevice().changeLogout(ChangeUserLogoutBean.Send(startTime, endTime))
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<ChangeUserLogoutBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}