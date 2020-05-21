package com.epro.pos.mvp.model.bean

data class EditStockTakingDetailBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<EditStockTakingDetailBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
