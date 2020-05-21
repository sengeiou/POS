package com.epro.pos.mvp.model.bean

data class ArrangeDistributionBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<ArrangeDistributionBean.Result> {
    data class Result(val id: String?)

//    | 字段        | 类型         | 必须 | 说明         |
//    | ----------- | ------------ | ---- | ------------ |
//    | courierId   | string       | Y    | 快递员id     |
//    | courierName | string       | Y    | 快递员名字   |
//    | orderSns    | List<String> | Y    | 订单编号列表 |
    data class Send(val courierId: String,val courierName:String,val orderSns:ArrayList<String> )

}
