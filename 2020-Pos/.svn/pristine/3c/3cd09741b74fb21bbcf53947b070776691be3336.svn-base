package com.epro.pos.mvp.model.bean

data class BindPhonePopBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<BindPhonePopBean.Result> {
    data class Result(val result: String?)
    data class Send(val account: String,val code:String,val authType:String)
}
