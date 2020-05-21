package com.epro.pos.mvp.model.bean

data class GetWaitdoListBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetWaitdoListBean.Result> {
    data class Result(val frozenCount: String,
                         val renewCount: String,
                         val updateCount: String,
                         val waitDeliveryCount: String,
                         val waitPickUpCount: String)
}
