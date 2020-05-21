package com.epro.pos.mvp.model.bean


data class UpdateStockTakingBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<UpdateStockTakingBean.Result> {
    data class Result(val id: String?)
    data class Send(val inventoryStatus: Int,
                    val inventoryCode: String,
                    val remarks: String?,
                    val infoRequestList: ArrayList<StockTakingInfo>) {
    }

    data class StockTakingInfo(val productId: String,
                               val actualStock: Long,
                               val productNumber:Int)


}
