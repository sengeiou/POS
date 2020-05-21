package com.epro.pos.mvp.model.bean

data class VerifyOnlineOrderBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<VerifyOnlineOrderBean.Result> {
    data class Result(val id: String?)

//    | 字段    | 类型   | 必须 | 说明     |
//    | ------- | ------ | ---- | -------- |
//    | orderSn | string | Y    | 订单编号 |
//    | dcode   | string | Y    | 提货码   |
    data class Send(val orderSn: String,val dcode :String)
}
