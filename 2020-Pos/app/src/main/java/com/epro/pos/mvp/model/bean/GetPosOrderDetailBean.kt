package com.epro.pos.mvp.model.bean

data class GetPosOrderDetailBean(override val code: Int, override val message: String, override val result: Result) : BaseBean<GetPosOrderDetailBean.Result> {
    data class Result(val cashierId: String, val cashierName: String, val products: ArrayList<Product>)
    data class Product(val availableRefundCount: Int,
                       val discount: Float,
                       val discountPrice: String,
                       val productCount: Int,
                       val productDesc: String,
                       val productId: String,
                       val productSn: String,
                       val salePrice: String,
                       val totalPrice: String,
                       var preRefundCount: Int? = 0)
}
