package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.*
import io.reactivex.Observable

class SelectGoodsModel : BaseModel() {
    fun getProductList(page: Int, searchStr: String? = null, goodsTypeId: String? = null, goodsBinding: String? = null, isStockEarly: Boolean = false): Observable<GetCommonListBean<Product>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.ProductSend(p, searchStr, goodsTypeId, goodsBinding, isStockEarly)
        return getApiSevice().getProductList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<Product>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())

    }

    fun getGoodsCategoryCountList(): Observable<GetResultListBean<GoodsCategoryCount>> {
        val send = GetCommonListBean.GoodCategorySend()
        return getApiSevice().getGoodsCategoryCountList()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetResultListBean<GoodsCategoryCount>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


}