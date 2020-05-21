package com.epro.pos.mvp.model

import com.mike.baselib.net.exception.ApiException
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.epro.pos.listener.RetryWithDelay
import com.epro.pos.mvp.model.bean.*
import com.mike.baselib.net.exception.ExceptionHandle
import io.reactivex.Observable

class ShowListModel : BaseModel() {
    fun <D> ShowList(): Observable<GetCommonListBean<D>> {
        return Observable.just(true).flatMap {
            val list = ArrayList<ShowListItem>()
            for (i in 0..20) {
                list.add(ShowListItem(i))
            }
            return@flatMap Observable.just(GetCommonListBean(0, "", GetCommonListBean.Result(0, list))) as Observable<GetCommonListBean<D>>
        }
    }


    fun getOrderRecordList(page: Int, startTime: String? = null, endTime: String? = null, orderSn: String? = null, tradeType: String? = null, orderType: String? = null, payType: String? = null, cashierId: String? = null, orderStatus: String? = null, puserId: String? = null, productSn: String? = null): Observable<GetCommonListBean<OrderRecord>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.OrderRecordSend(p, startTime, endTime, orderSn, tradeType, orderType, payType, cashierId, orderStatus, puserId, productSn)
        return getApiSevice().getOrderRecordList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<OrderRecord>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getGoodsFileList(page: Int, searchStr: String? = null, goodsTypeId: String? = null, goodsBinding: String? = null): Observable<GetCommonListBean<GoodsFile>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.GoodFilesSend(p, searchStr, goodsTypeId, goodsBinding)
        return getApiSevice().getGoodsFileList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<GoodsFile>>(ApiException(it.message, it.code))
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

    fun getOrderGoodsRecordList(page: Int,
                                startTime: String? = null,
                                endTime: String? = null,
                                orderSn: String? = null,
                                tradeType: String? = null,
                                orderType: String? = null,
                                payType: String? = null,
                                cashierId: String? = null,
                                orderStatus: String? = null,
                                puserId: String? = null,
                                productSn: String? = null,
                                categoryId: String? = null): Observable<GetCommonListBean<GoodsRecord>> {

        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.GoodsRecordSend(p, startTime, endTime, orderSn, tradeType, orderType,payType, cashierId, orderStatus, puserId, productSn, categoryId)
        return getApiSevice().getOrderGoodsRecordList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<GoodsRecord>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())


    }

    fun getOrderSalesRankingGoodsList(page: Int,
                                      startTime: String? = null,
                                      endTime: String? = null,
                                      tradeType: String? = null,
                                      productSn: String? = null,
                                      categoryId: String? = null,orderSort:String?=null): Observable<GetCommonListBean<SalesRanking>> {

        val p = GetCommonListBean.Page(page,15,orderSort)
        val send = GetCommonListBean.SalesRankingSend(p, startTime, endTime, tradeType, productSn, categoryId)
        return getApiSevice().getOrderSalesRankingGoodsList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<SalesRanking>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())


    }

    fun getGrossProfitGatherList(page: Int,
                                 startTime: String? = null,
                                 endTime: String? = null,
                                 tradeType: String? = null,
                                 productSn: String? = null,
                                 categoryId: String? = null): Observable<GetCommonListBean<GrossProfitGather>> {

        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.GrossProfitGatherSend(p, startTime, endTime, tradeType, productSn, categoryId)
        return getApiSevice().getGrossProfitGatherList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<GrossProfitGather>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())


    }

    fun getCashierReconciliationList(page: Int,
                                     startTime: String? = null,
                                     endTime: String? = null, statementId: String? = null, cashierId: String? = null): Observable<GetCommonListBean<CashierRecon>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.CashierReconSend(p, startTime, endTime, statementId, cashierId)
        return getApiSevice().getCashierReconciliationList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<CashierRecon>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


    fun getStockQueryList(page: Int, searchStr: String? = null, goodsTypeId: String? = null, goodsBinding: String? = null, sStockEarly: Boolean = false): Observable<GetCommonListBean<StockQueryProduct>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.StockQuerySend(p, searchStr, goodsTypeId, goodsBinding, sStockEarly)
        return getApiSevice().getStockQueryList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<StockQueryProduct>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getStockGoodsPutinList(page: Int,
                               stratDate: String? = null,
                               endDate: String? = null,
                               stockCode: String? = null,
                               stockStatus: String? = null): Observable<GetCommonListBean<StockGoodsPutin>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.StockGoodsPutinSend(p, stratDate, endDate, stockCode, stockStatus)
        return getApiSevice().getStockGoodsPutinList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<StockGoodsPutin>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getStockTakingList(page: Int,
                           stratDate: String? = null,
                           endDate: String? = null,
                           inventoryStatus: String? = null,
                           inventoryCode: String? = null): Observable<GetCommonListBean<StockTaking>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.StockTakingSend(p, stratDate, endDate, inventoryStatus, inventoryCode)
        return getApiSevice().getStockTakingList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<StockTaking>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getStockOutinDetailList(page: Int,
                                stratDate: String? = null,
                                endDate: String? = null,
                                searchStr: String? = null,
                                stockSource: String? = null): Observable<GetCommonListBean<StockOutinDetail>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.StockOutinDetailSend(p, stratDate, endDate, searchStr, stockSource)
        return getApiSevice().getStockOutinDetailList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<StockOutinDetail>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }

    fun getPosTradeList(page: Int, startTime: String? = null, endTime: String? = null, orderSn: String? = null): Observable<GetCommonListBean<PosTradeOrder>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.PosTradeSend(p, startTime, endTime, orderSn)
        return getApiSevice().getPosTradeList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<PosTradeOrder>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())
    }


    fun getFinancialBillDetail(billSn: String): Observable<GetFinancialBillDetailBean> {
        return getApiSevice().getFinancialBillDetail(billSn)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetFinancialBillDetailBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())


    }

    fun getUpperShelfGoodsList(page: Int, searchStr: String? = null, status: Int? = 0): Observable<GetCommonListBean<UpperShelfGoods>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.UpperShelfGoodsSend(p, searchStr, status)
        return getApiSevice().getUpperShelfGoodsList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<UpperShelfGoods>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())


    }

    fun lowerShelfGoods(uGoods: ArrayList<UpperShelfGoods>): Observable<LowerShelfGoodsBean> {
        val goodsList = ArrayList<LowerShelfGoodsBean.Goods>()
        for (g in uGoods) {
            val goods = LowerShelfGoodsBean.Goods(g.goodsID, g.status)
            goodsList.add(goods)
        }
        val send = LowerShelfGoodsBean.Send(goodsList)
        return getApiSevice().lowerShelfGoods(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<LowerShelfGoodsBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())


    }

    fun getOnlineOrdersList(page: Int, orderStatus: Int, mobile: String?): Observable<GetCommonListBean<OnlineOrder>> {
        val p = GetCommonListBean.Page(page)
        val send = GetCommonListBean.OnlineGoodsSend(p, orderStatus, mobile)
        return getApiSevice().getOnlineOrdersList(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetCommonListBean<OnlineOrder>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())

    }
    fun getCashierList(): Observable<GetResultListBean<Cashier>> {
        return getApiSevice().getCashierList()
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<GetResultListBean<Cashier>>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())

    }
    fun deleteGoodsFile(goodsFiles:ArrayList<GoodsFile>): Observable<DeleteGoodsFileBean> {
        val products=ArrayList<DeleteGoodsFileBean.Product>()
        for(file in goodsFiles){
            val product=DeleteGoodsFileBean.Product(file.productId)
            products.add(product)
        }
        val send=DeleteGoodsFileBean.Send(products)
        return getApiSevice().delteGoodsFile(send)
                .flatMap {
                    if (it.code == ErrorStatus.SUCCESS) {
                        return@flatMap Observable.just(it)
                    } else {
                        return@flatMap Observable.error<DeleteGoodsFileBean>(ApiException(it.message, it.code))
                    }
                }
                .retryWhen(RetryWithDelay())
                .compose(SchedulerUtils.ioToMain())

    }

}