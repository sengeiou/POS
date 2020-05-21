package com.epro.pos.mvp.model.bean

data class CancelOnlineOrderBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<CancelOnlineOrderBean.Result> {
    data class Result(val id: String?)

//    | 字段    | 类型   | 必须 | 说明     |
//    | ------- | ------ | ---- | -------- |
//    | orderSn | string | Y    | 订单编号 |
//    | reason  | string | Y    | 取消原因 |
    data class Send(val orderSn: String,val reason :String)
}
