package com.epro.pos.mvp.model.bean

data class BindEmailPopupBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<BindEmailPopupBean.Result> {
/*    | 字段     | 类型   | 必须 | 说明             |
    | -------- | ------ | ---- | ---------------- |
    | account  | String | Y    | 手机/邮箱        |
    | code     | String | Y    | 验证码           |
    | authType | String | Y    | ep:邮箱  mp:手机 |*/
    data class Result(val result: String)
    data class Send(val account: String,val code:String,val authType:String)
}
