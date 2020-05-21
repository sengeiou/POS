package com.epro.pos.mvp.model.bean

data class StockGoodsPutin(val createDate: String,
                           val id: String,
                           val shopId: String,
                           val stockCode: String,
                           val stockDesc: String,
                           val stockInfoList: ArrayList<StockProductInfo>,
                           val stockMoney: String,
                           val stockSource: Int,
                           val stockStatus: Int,
                           val updateDate: String){

    data class StockProductInfo(var buyingPrice: String="",
                                val goodsName: String="",
                                val goodsUnitName: String="",
                                val productBarCode: String="",
                                val specificationsValueNames: String="",
                                val productId: String?="",
                                var stockNumber: Long=0,
                                val stockSource: Int?=1,
                                var stockType: Int=0,
                                val goodsBinding: Int?=1)
}

