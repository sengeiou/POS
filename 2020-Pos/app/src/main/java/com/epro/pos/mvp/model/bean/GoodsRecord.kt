package com.epro.pos.mvp.model.bean

/**
 * 商品记录
 */
data class GoodsRecord(val cashierId: String,
                       val cashierName: String,
                       val category: String,
                       val createTime: String,
                       val discountPrice: String,
                       val goodsName: String,
                       val goodsSpecifitionNameValue: String,
                       val id: String,
                       val orderId: String,
                       val orderSn: String,
                       val orderStatus: Int,
                       val orderType: Int,
                       val productCount: Int,
                       val productId: String,
                       val productSn: String,
                       val purchasePrice: String,
                       val puserId: String,
                       val salePrice: String,
                       val totalPrice: String,
                       val tradeType: Int)