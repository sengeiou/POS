package com.epro.pos.mvp.model.bean

data class GetOrderRecordDetailBean(override val code: Int, override val message: String, override val result: OrderRecord) : BaseBean<OrderRecord> {
//    | 字段    | 类型   | 必须 | 说明     |
//    | ------- | ------ | ---- | -------- |
//    | orderSn | string | Y    | 订单编号 |
}
