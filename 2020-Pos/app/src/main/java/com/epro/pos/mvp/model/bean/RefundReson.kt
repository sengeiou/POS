package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

/**
 * 配送员
 */
data class RefundReson(val id:String, val reason:String, var reason2:String="",override var judgeValue: Boolean=false) :Judgable{
    override fun judge(): Boolean {
       return judgeValue

    }
}