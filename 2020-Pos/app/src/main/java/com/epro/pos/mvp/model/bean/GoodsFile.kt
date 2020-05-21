package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class GoodsFile(val brandName: String,
                     val buyingPrice: String,
                     val goodsBinding: Int,
                     val goodsID: String,
                     val goodsName: String,
                     val goodsUnitName: String,
                     val goodsTypeName: String?,
                     val onlineSalesPrice: String,
                     val productBarCode: String,
                     val productId: String,
                     val retailPrice: String,
                     val specificationsValueNames: String, override var judgeValue: Boolean = false) : Judgable {
    override fun judge(): Boolean {
        return judgeValue
    }
}

