package com.epro.pos.mvp.model.bean


data class AddStockTakingBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<AddStockTakingBean.Result> {
    data class Result(val id: String?)
    data class Send(val inventoryStatus: Int,
                    val remarks: String?,
                    val infoRequestList: ArrayList<StockTakingInfo>) {
    }

    data class StockTakingInfo(val productId: String,
                               val actualStock: Long,
                               val productNumber:Int)


}
