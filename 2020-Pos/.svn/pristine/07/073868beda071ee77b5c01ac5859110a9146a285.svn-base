package com.epro.pos.mvp.model.bean

data class StockTaking(val createDate: String,
                       val infoResponseList: ArrayList<StockProductInfo>,
                       val inventoryCode: String,
                       val inventoryStatus: Int,
                       val profitNumber: String,
                       val remarks: String,
                       val updateDate: String) {

    data class StockProductInfo(var actualStock: Long=0,
                                    val buyingPrice: String="",
                                    val goodsName: String="",
                                    val productBarCode: String="",
                                    val productId: String="",
                                    val specificationsName: String="",
                                    val sysStock: Int=0,
                                    val typeName: String="",
                                    val unitName: String="")
}