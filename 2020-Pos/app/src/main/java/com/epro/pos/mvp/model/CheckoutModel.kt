package com.epro.pos.mvp.model

import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.*
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import io.reactivex.Observable

class CheckoutModel : BaseModel() {
    fun createOrder(orderType: Int,
                    totalCount: Int,
                    totalAmount: String,
                    totalDiscount: Float,
                    discountPrice: String,
                    payType: Int,
                    products: ArrayList<CartProduct>,
                    id: String? = null,
                    remark: String? = null,
                    payBarCode: String? = null): Observable<CreateOrderBean> {

        val pList = ArrayList<CreateOrderBean.Product>()
        for (p in products) {
            val product = CreateOrderBean.Product(p.product.productId, p.product.productBarCode, p.num, p.discount, PosBusManager.getSalePrice(p))
            pList.add(product)
        }

        val send = CreateOrderBean.Send(orderType, totalCount, totalAmount, totalDiscount, discountPrice, payType, pList, id, remark, payBarCode)

        return getApiSevice().createOrder(send)
                .flatMap {
                    var code = it.code
                    if (it.code==PosConst.PAY_POLLING_CODE1) {//创建订单成功,支付失败
                        it.payResult=CreateOrderBean.Result(it.result!!.toString(),it.code,it.message)
                        code = ErrorStatus.SUCCESS
                    }
                    if(it.code==ErrorStatus.SUCCESS){ //返回200 创建订单成功,支付成功
                        it.payResult=CreateOrderBean.Result(it.result!!.toString(),PosConst.PAY_SUCCESS,ErrorStatus.SUCCESS_MSG)
                    }
                    if (code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<CreateOrderBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun checkOrderPay(orderSn: String): Observable<CheckOrderPayBean> {
        return getApiSevice().checkOrderPay(orderSn)
                .flatMap {
                    var code = it.code
                    if (it.code==PosConst.PAY_POLLING_CODE1) { //支付各种状态
                        it.result=it.code.toString()
                        code = ErrorStatus.SUCCESS
                        it.checkResult=CheckOrderPayBean.Result(it.code,it.message)
                    }
                    if(it.code==ErrorStatus.SUCCESS){
                        it.checkResult=CheckOrderPayBean.Result(PosConst.PAY_SUCCESS,it.message)
                    }
                    if (code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<CheckOrderPayBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }
    fun cancelPosTrade(orderSn: String): Observable<CancelPosTradeBean> {
        return getApiSevice().cancelPosTrade(orderSn)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<CancelPosTradeBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

}