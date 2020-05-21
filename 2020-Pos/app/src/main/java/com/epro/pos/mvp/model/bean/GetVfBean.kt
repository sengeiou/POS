package com.epro.pos.mvp.model.bean

data class GetVfBean(override val code:Int, override val message: String, override val result:Result):BaseBean<GetVfBean.Result>{
    data class Result(val shiroCookie:String)

/*    | 字段     | 类型   | 必须 | 说明              |
    | -------- | ------ | ---- | ----------------- |
    | account  | string | Y    | 手机号码/邮箱账号 |
    | authType | string | Y    | ep:邮箱 mp:手机   |*/
    data class Send(val account:String,val type:Int)
}