package com.epro.pos.mvp.model.bean

data class OrderProduct(val discount: Float,
                        val discountPrice: String,
                        val salePrice: String?,
                        val goodsId: String,
                        val goodsName: String,
                        val goodsSpecifitionNameValue: String,
                        val listPicUrl: String,
                        val productCount: Int,
                        val productId: String,
                        val productSn: String,
                        val totalPrice: String)

