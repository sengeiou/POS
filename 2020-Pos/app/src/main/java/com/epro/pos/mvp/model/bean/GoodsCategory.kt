package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

/**
 * 商品分类
 */
//"id":"18",
//"typeCode":"A0001",
//"typeName":"医药品1"
data class GoodsCategory(val id: String,
                         val typeCode: String,
                         val typeName: String,
                         override var judgeValue: Boolean=false):Judgable {
    override fun judge(): Boolean {
       return judgeValue
    }
}
