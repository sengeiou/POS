package com.epro.pos.mvp.model.bean

data class RefundOrderBean(override val code: Int, override val message: String, override val result: Any) : BaseBean<Any> {
    data class Send(val orderSn: String, val refundProducts: ArrayList<Product>)
    data class Product(val productId: String, val productSn: String, val refundCount: Int)


}