package com.epro.pos.mvp.model.bean


data class DeleteStockTakingBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<DeleteStockTakingBean.Result> {
    data class Result(val id: String?)
    data class Send(val inventoryCode: String) {
    }

}
