package com.epro.pos.mvp.model.bean

import java.util.*

/*| 字段     | 类型   | 必须 | 说明     |
| -------- | ------ | ---- | -------- |
| shopId   | String | Y    | 店铺ID   |
| shopName | String | Y    | 店铺名称 |
| owner    | String | Y    | 联系人   |
| mobile   | String | Y    | 手机     |
| email    | String | Y    | 邮箱     |
| problem  | String | Y    | 问题     |*/

/*"email": "2715533714@qq.com",
"mobile": "+8618224442388",
"owner": "yinguotai",
"shopId": "88000001",
"shopName": "zll的店"*/
data class FeedbackBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<FeedbackBean.Result> {
    data class Result(val code: Int,val message:String,val result:Any)
    data class Send(val owner:String,val mobile:String?,val email:String?,val problem:String,val shopId:String,val shopName:String )
}
