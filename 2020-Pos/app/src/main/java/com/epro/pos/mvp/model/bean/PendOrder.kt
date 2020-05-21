package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

/**
 * 挂单
 */
data class PendOrder(var id: String, val createAt:String, val cartProducts: ArrayList<CartProduct>, override var judgeValue: Boolean=false):Judgable {
    override fun judge(): Boolean {
        return judgeValue
    }
}