package com.epro.pos.mvp.model.bean

import com.epro.pos.utils.PosConst

/**
 * 待结算订单
 */
data class CheckoutOrder(val totalCount: Int,
                         val totalAmount: String,
                         val products: ArrayList<CartProduct>,
                         val discountPrice: String="0",
                         val totalDiscount: Float=1F,
                         val orderType: Int=PosConst.ORDER_TYPE_SALE,
                         val payType: Int=PosConst.PAY_MODE_CASH,
                         val id: String?=null,
                         val remark: String?=null,
                         val payBarCode: String?=null){
}