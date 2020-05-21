package com.epro.pos.mvp.model.bean


data class AddStockPutinBean(override val code: Int, override val message: String, override val result:Result) :BaseBean<AddStockPutinBean.Result>{
    data class Result(val id:String?)
    data class Send(val stockStatus: Int,
                    val stockDesc: String?,
                    val stockInfoRequestList: ArrayList<StockInfo>) {
    }

    data class StockInfo(val productId: String,
                         val stockNumber: Long,
                         val stockType: Int,
                          val buyingPrice:String)
}
