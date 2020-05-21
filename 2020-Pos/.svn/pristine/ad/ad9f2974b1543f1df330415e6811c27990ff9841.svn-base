package com.epro.pos.mvp.model.bean

data class LowerShelfGoodsBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<LowerShelfGoodsBean.Result> {
    data class Result(val id: String?)
    data class Send(val goodsStatusRequestList: ArrayList<Goods>)
    data class Goods(val goodsID: String, val status: Int = 1)
}
