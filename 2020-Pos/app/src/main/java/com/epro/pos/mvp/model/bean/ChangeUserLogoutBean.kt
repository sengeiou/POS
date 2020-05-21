package com.epro.pos.mvp.model.bean

/*| 字段      | 类型   | 必须 | 说明                          |
| --------- | ------ | ---- | ----------------------------- |
| startTime | string | Y    | 开始时间(yyyy-mm-dd hh:ss:mm) |
| endTime   | string | Y    | 结束时间                      |*/
data class ChangeUserLogoutBean(val code:Int,val message:String,val result:Any) {
    data class Send(val startTime:String,val endTime:String)
}