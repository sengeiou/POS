package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.CreateGoodsBean
import com.epro.pos.mvp.model.bean.GetCommonListBean
import com.epro.pos.mvp.model.bean.GoodsCategory
import com.epro.pos.mvp.model.bean.Product
import io.reactivex.Observable

class CreateGoodsModel : BaseModel() {

    fun createGoods(product: CreateGoodsBean.Product, goodsName: String, goodsTypeId: String,goodsTypeName: String, buyingPrice: String, retailPrice: String): Observable<CreateGoodsBean> {
        val productList=ArrayList<CreateGoodsBean.Product>()
        productList.add(product)
        val send = CreateGoodsBean.Send(productList, goodsName, goodsTypeId,goodsTypeName,buyingPrice,retailPrice)
        return getApiSevice().createGoods(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<CreateGoodsBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodsCategoryList(): Observable<GetCommonListBean<GoodsCategory>> {
        val send = GetCommonListBean.GoodCategorySend()
        return getApiSevice().getGoodsCategoryList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<GoodsCategory>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}