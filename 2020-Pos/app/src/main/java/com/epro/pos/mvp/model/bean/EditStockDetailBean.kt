package com.epro.pos.mvp.model.bean

data class EditStockDetailBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<EditStockDetailBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
