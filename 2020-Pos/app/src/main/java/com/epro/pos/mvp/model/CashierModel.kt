package com.epro.pos.mvp.model

import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.GetCommonListBean
import com.epro.pos.mvp.model.bean.ProductCategory
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class CashierModel : BaseModel() {
    fun getShopProductCategorys(): Observable<GetCommonListBean<ProductCategory>> {
        //val send=GetProcuctCategoryListBean.Send(shopId)
        return getApiSevice().getShopProductCategorys()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<ProductCategory>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
}