package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable


/**
 * 产品  sku的概念
 */
/*"buyingPrice": "5.83",
"compressPicUrl": "",
"goodsBinding": 1,
"goodsName": "德芙巧克力（奶香白）43G",
"goodsUnitName": "条",
"isOut": "0",
"offlineActivityInfo": "",
"onlineActivityInfo": "",
"onlineSalesPrice": "7.80",
"productBarCode": "6914973600164",
"productId": "K880001101585547404400000036",
"productNumber": 0,
"productPicUrl": "",
"retailPrice": "7.80",
"specificationsValueNames": "43g"*/
data class Product(val productId: String= "",
                   val offlineActivityInfo:String="",
                   val onlineActivityInfo:String="",
                   val productBarCode: String = "",
                   val goodsName: String = "",
                   val specificationsValueNames: String= "",
                   val retailPrice: String="",
                   val goodsBinding: Int?=1,
                   val onlineSalesPrice: String? = "",
                   val compressPicUrl: String? = "",
                   val goodsUnitName: String = "",
                   val goodsTypeName: String? = "",
                   val buyingPrice: String? = "",
                   val productPicUrl: String? = "",
                   val productNumber: Int?=0,//库存数量
                   val specificationsValueIds: String?=null, override var judgeValue: Boolean=false):Judgable {
    override fun judge(): Boolean {
       return judgeValue
    }
}