package com.epro.pos.mvp.model.bean

data class SystemSettingsBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<SystemSettingsBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
