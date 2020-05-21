package com.epro.pos.mvp.model.bean

data class QualificationInfoBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<QualificationInfoBean.Result> {
    data class Result(val id: String?)
    data class Send(val id: String)
}
