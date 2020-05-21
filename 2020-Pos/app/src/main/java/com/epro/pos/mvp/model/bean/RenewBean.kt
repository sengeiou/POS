package com.epro.pos.mvp.model.bean

data class RenewBean(val code: Int, val message: String) {
    data class Send(val shopId:String)
}