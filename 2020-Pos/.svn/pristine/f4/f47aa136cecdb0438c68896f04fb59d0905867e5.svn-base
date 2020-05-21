package com.epro.pos.mvp.model.bean

data class UnBindPhoneBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<UnBindPhoneBean.Result> {
    data class Result(val id: String?)
    data class Send(val account: String,val code:String,val authType:String)
}
