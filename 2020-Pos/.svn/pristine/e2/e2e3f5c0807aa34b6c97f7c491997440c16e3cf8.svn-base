package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

/**
 * 商品分类
 */
//{
//    "productTypeCount":4,
//    "typeId":"1",
//    "typeName":"零食"
//}

data class GoodsCategoryCount(val productTypeCount: Int,
                              val typeId: String,
                              val typeName: String,
                              override var judgeValue: Boolean=false):Judgable {
    override fun judge(): Boolean {
       return judgeValue
    }
}
