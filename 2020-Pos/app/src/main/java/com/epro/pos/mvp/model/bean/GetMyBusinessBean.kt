package com.epro.pos.mvp.model.bean

data class GetMyBusinessBean(override val code: Int, override val message: String, override val result: Any) : BaseBean<Any> {
    data class Business(val buyingPrice: String,
                         val productNumber: String,
                         val stockEarlyNum: String,
                         val stockNumber: String)
}
