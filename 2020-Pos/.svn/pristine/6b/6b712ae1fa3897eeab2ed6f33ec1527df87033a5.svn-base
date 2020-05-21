package com.epro.pos.mvp.model.bean

/*| 字段     | 类型   | 必须 | 说明     |
| -------- | ------ | ---- | -------- |
| shopId   | String | Y    | 店铺ID   |
| shopName | String | Y    | 店铺名称 |
| owner    | String | Y    | 联系人   |
| mobile   | String | Y    | 手机     |
| email    | String | Y    | 邮箱     |*/
data class InquireShopBean(override val code:Int, override val message: String, override val result:Result):BaseBean<InquireShopBean.Result> {
    data class Result(val shopId:String,val shopName:String,val owner:String,val mobile:String,val email:String)
}