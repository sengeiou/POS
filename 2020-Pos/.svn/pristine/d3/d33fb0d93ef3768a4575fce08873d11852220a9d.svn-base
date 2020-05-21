package com.epro.pos.mvp.model.bean

data class DeleteGoodsFileBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<DeleteGoodsFileBean.Result> {
    data class Result(val failList: ArrayList<String>, val successList: ArrayList<String>)
    data class Send(val productRequestList: ArrayList<Product>)
    data class Product(val productId: String)
}