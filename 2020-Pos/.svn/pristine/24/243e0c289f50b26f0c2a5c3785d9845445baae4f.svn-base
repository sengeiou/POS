package com.epro.pos.mvp.model.bean


data class ModifyPasswordBean(override val code:Int,
                              override val message: String,
                              override val result:Result):BaseBean<ModifyPasswordBean.Result>{
    class Result(val code:Int,val message: String)
    class Send(val oldPwd:String,val newPwd:String,val rePwd:String)
}