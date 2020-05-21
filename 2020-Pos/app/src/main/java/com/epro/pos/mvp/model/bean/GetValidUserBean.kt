package com.epro.pos.mvp.model.bean

data class GetValidUserBean(val code:Int,  val message: String,  val result:Any) {
    data class Send(val account:String)
}