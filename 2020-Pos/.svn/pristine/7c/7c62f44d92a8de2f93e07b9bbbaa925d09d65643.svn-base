package com.epro.pos.mvp.model.bean


data class GetCommonListBean<T>(override val code: Int, override val message: String, override val result: Result<T>) : BaseBean<GetCommonListBean.Result<T>> {
    data class Result<T>(val total: Int, val rows: ArrayList<T>)
    data class OrderRecordSend(val page: Page,
                               var startTime: String? = null,
                               var endTime: String? = null,
                               var orderSn: String? = null,
                               var tradeType: String? = null,
                               var orderType: String? = null,
                               var payType: String? = null,
                               var cashierId: String? = null,
                               var orderStatus: String? = null,
                               var puserId: String? = null,
                               var productSn: String? = null)

    data class GoodFilesSend(val page: Page,
                             var searchStr: String? = null,
                             var goodsTypeId: String? = null,
                             var goodsBinding: String? = null)

    // {"searchStr":"","isQueryAll":false,"page":{"no":1,"size":10}}
    data class GoodCategorySend(var searchStr: String? = null,
                                val page: Page = Page(1, 1000),
                                var isQueryAll: Boolean = true)


    data class GoodsRecordSend(val page: Page,
                               var startTime: String? = null,
                               var endTime: String? = null,
                               var orderSn: String? = null,
                               var tradeType: String? = null,
                               var orderType: String? = null,
                               var payType: String? = null,
                               var cashierId: String? = null,
                               var orderStatus: String? = null,
                               var puserId: String? = null,
                               var productSn: String? = null,
                               var categoryId: String? = null) {

    }

    data class SalesRankingSend(val page: Page,
                                var startTime: String? = null,
                                var endTime: String? = null,
                                var tradeType: String? = null,
                                var productSn: String? = null,
                                var categoryId: String? = null) {
    }

    data class GrossProfitGatherSend(val page: Page,
                                     var startTime: String? = null,
                                     var endTime: String? = null,
                                     var tradeType: String? = null,
                                     var productSn: String? = null,
                                     var categoryId: String? = null) {
    }

    data class CashierReconSend(val page: Page,
                                var startTime: String? = null,
                                var endTime: String? = null,
                                var statementId: String? = null,
                                var cashierId: String? = null) {
    }

    data class StockQuerySend(val page: Page,
                              var searchStr: String? = null,
                              var goodsTypeId: String? = null,
                              var goodsBinding: String? = null,
                              var isStockEarly: Boolean = false)

    //{"stockSource":1,"page":{"no":1,"size":"15"},"stockStatus":"1","stockCode":"00","stratDate":"2019-09-16 00:00:00","endDate":"2019-09-17 23:59:59"}
    data class StockGoodsPutinSend(val page: Page,
                                   var stratDate: String? = null,
                                   var endDate: String? = null,
                                   var stockCode: String? = null,
                                   var stockStatus: String? = null) {
    }

    // {"page":{"no":1,"size":"15"},"stratDate":"2019-010-01 00:00:00","endDate":"2019-010-02 23:59:59","inventoryStatus":"1","inventoryCode":"55"}
    data class StockTakingSend(val page: Page,
                               var stratDate: String? = null,
                               var endDate: String? = null,
                               var inventoryStatus: String? = null,
                               var inventoryCode: String? = null)

    //{"page":{"no":1,"size":"15"},"stratDate":"2019-010-01 00:00:00","endDate":"2019-010-02 23:59:59","stockSource":"4","searchStr":"222"}
    data class StockOutinDetailSend(val page: Page,
                                    var stratDate: String? = null,
                                    var endDate: String? = null,
                                    var searchStr: String? = null,
                                    var stockSource: String? = null)

    //{"page":{"no":1,"size":"10"},"isStockEarly":false,"searchStr":"","goodsBinding":null,"goodsTypeId":"1"}
    data class ProductSend(val page: Page,
                           var searchStr: String? = null,
                           var goodsTypeId: String? = null,
                           var goodsBinding: String? = null,
                           var isStockEarly: Boolean = false)

    data class PosTradeSend(val page: Page,
                            var startTime: String? = null,
                            var endTime: String? = null,
                            var orderSn: String? = null)


    data class FinancialBillSend(val page: Page,
                                 var startTime: String? = null,
                                 var endTime: String? = null,
                                 var billSn: String? = null)

    //{"page":{"no":1,"size":15},"searchStr":"","status":"0"}
    data class UpperShelfGoodsSend(val page: Page,
                                   var searchStr: String? = null,
                                   var status: Int? = 0)

    data class OnlineGoodsSend(val page: Page,
                               var orderStatus: Int,
                               var mobile: String? = null)


//    | 字段  | 类型    | 必须 | 说明                         |
//    | ----- | ------- | ---- | ---------------------------- |
//    | no    | integer | N    | 页码                         |
//    | size  | integer | N    | 页面大小                     |
//    | order | string  | N    | 按销量降序或者升序(asc,desc) |
    data class Page(val no: Int,
                    val size: Int = 15,val order:String?=null
    )
}