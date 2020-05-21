package com.epro.pos.mvp.model

import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.GetGoodsFileDetailBean
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class GoodsFileDetailModel : BaseModel() {
    fun getGoodsFileDetail(goodsId:String): Observable<GetGoodsFileDetailBean> {
        return getApiSevice().getGoodsFileDetail(goodsId)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetGoodsFileDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())

    }


}