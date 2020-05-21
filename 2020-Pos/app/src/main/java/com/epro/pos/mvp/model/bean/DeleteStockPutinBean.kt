package com.epro.pos.mvp.model.bean


data class DeleteStockPutinBean(override val code: Int, override val message: String, override val result:Result) :BaseBean<DeleteStockPutinBean.Result>{
    data class Result(val id:String?)
    data class Send(val stockCode: String) {
    }
}
