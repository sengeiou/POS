package com.epro.pos.mvp.model.bean

import java.sql.Timestamp


/*"timestamp": "2019-10-16 17:03:41",
"status": 500,
"error": "Internal Server Error",
"message": "验证码不正确",
"path": "/admin/passd/resetPassword"*/
data class FindPasswordBean(override val code:Int, override val message: String, override val result:Result):BaseBean<FindPasswordBean.Result>{
    data class Result(val timestamp: String,val status:Int,val error:String,val message: String,val path:String)

/*    | 字段            | 类型   | 必须 | 说明              |
    | --------------- | ------ | ---- | ----------------- |
    | account         | string | Y    | 手机号码/邮箱账号 |
    | password        | string | Y    | 密码              |
    | passwordConfirm | String | Y    | 确认密码          |
    | code            | string | Y    | 验证码            |
    | authType        | string | Y    | ep:邮箱 mp:手机   |*/
    data class Send(val account:String,val password:String,val passwordConfirm:String,val code:String,val authType:String)
}