package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class UpperShelfGoods(val approvalStatus: Int,
                           val deliveryDistance: String,
                           val deliveryMode: String,
                           val frozenDesc: String,
                           val goodsBinding: Int,
                           val goodsCompressPriUrl: String,
                           val goodsCompressPriUrl1: String,
                           val goodsCompressPriUrl2: String,
                           val goodsCompressPriUrl3: String,
                           val goodsCompressPriUrl4: String,
                           val goodsDesc: String,
                           val goodsID: String,
                           val goodsName: String,
                           val goodsPicUrl: String,
                           val goodsPicUrl1: String,
                           val goodsPicUrl2: String,
                           val goodsPicUrl3: String,
                           val goodsPicUrl4: String,
                           val goodsTypeName: String,
                           val productStatusResponseList: List<Product>,
                           val shoppingMallName: String,
                           val status: Int, override var judgeValue: Boolean=false) :Judgable{
    override fun judge(): Boolean {
      return judgeValue
    }

    data class Product(val buyingPrice: String,
                       val compressPicUrl: String,
                       val onlineSalesPrice: String,
                       val productId: String,
                       val productNumber: Int,
                       val productPicUrl: String,
                       val retailPrice: String,
                       val specificationsValueIds: String,
                       val specificationsValueNames: String)
}