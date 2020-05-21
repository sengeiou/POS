package com.epro.pos.mvp.model.bean

data class EditBaseInfoBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<EditBaseInfoBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
