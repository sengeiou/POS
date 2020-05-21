package com.epro.pos.mvp.model.bean

data class CreateGoodsBean(override val code: Int, override val message: String, override val result: com.epro.pos.mvp.model.bean.Product) : BaseBean<Product> {
    data class Result(val id: String?)
    data class Send(val productList: ArrayList<Product>, val goodsName: String, val goodsTypeId: String, val goodsTypeName: String, val buyingPrice: String, val retailPrice: String)
    data class Product(val productBarCode: String, val productNumber: Long, val specificationsValueNames: String)
}
