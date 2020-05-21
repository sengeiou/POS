package com.epro.pos.mvp.model.bean

import com.epro.pos.ui.fragment.TradeOrderSeniorFilterPopup

data class CreateOrderBean(override val code: Int, override val message: String, override var result: Any?,var payResult:Result?) : BaseBean<Any?> {
    //data class Result(val id: String?)


//    | 字段          | 类型       | 必须 | 说明                       |
//    | ------------- | ---------- | ---- | -------------------------- |
//    | id            | string     | Y    | 订单序列号(找回订单用)     |
//    | payType       | integer    | Y    | 支付类型(1.现金 2.支付宝)  |
//    | orderType     | integer    | Y    | 订单类型(1.销售 2.退货)    |
//    | totalCount    | integer    | Y    | 商品总数                   |
//    | totalDiscount | bigdecimal | Y    | 整单折扣                   |
//    | totalAmount   | bigdecimal | Y    | 整单应收金额(折后商品总和) |
//    | discountPrice | bigdecimal | Y    | 整单实收金额(整单折后)     |
//    | remark        | string     | N    | 备注                       |
//    | products      | list       | Y    | 商品                       |
//    | payBarCode    | string     | N    | 条形码                     |

    data class Send(val orderType: Int,
                    val totalCount: Int,
                    val totalAmount: String,
                    val totalDiscount: Float,
                    val discountPrice: String,
                    val payType: Int,
                    val products: ArrayList<Product>,
                    val id: String?=null,
                    val remark: String?=null,
                    val payBarCode: String?=null)

    data class Product(val productId: String,
                       val productSn: String,
                       val productCount: Int,
                       val discount: Float,
                       val salePrice: String)

    data class Result(val orderSn:String,val payStatus:Int,val message:String) //订单号,支付状态
}
