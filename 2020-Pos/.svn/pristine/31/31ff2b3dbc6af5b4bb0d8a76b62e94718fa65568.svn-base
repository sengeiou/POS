package com.epro.pos.mvp.model.bean

/*| 字段 | 类型    | 必须 | 说明   |
| ---- | ------- | ---- | ------ |
| id   | Integer | Y    | 消息ID |*/
class updateReadBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<updateReadBean.Result> {
    data class Result(val code:Int,val message:String,val result:Any)
    data class Send(val id:Int)
}