package com.epro.pos.mvp.model.bean


data class UpdateStockPutinBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<UpdateStockPutinBean.Result> {
    data class Result(val id: String?)
    data class Send(val stockStatus: Int,
                    val stockCode: String,
                    val stockDesc: String?,
                    val stockInfoRequestList: ArrayList<StockInfo>) {
    }

    data class StockInfo(val productId: String,
                         val stockNumber: Long,
                         val stockType: Int,
                         val buyingPrice: String)
}
