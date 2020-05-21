package com.epro.pos.mvp.model.bean

data class DeleteTipsBean (val code:Int,val message:String,val result:Any){
    data class Send(val id:String)
}