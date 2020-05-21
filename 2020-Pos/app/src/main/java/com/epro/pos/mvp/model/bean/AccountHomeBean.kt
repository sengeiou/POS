package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

class AccountHomeBean(var content:String,var background:Int, override var judgeValue: Boolean=false): Judgable {
    override fun judge(): Boolean {
        return judgeValue
    }

    var content2=""
    fun valueContent2(content: String):AccountHomeBean{
        this.content2=content
        return this
    }

    var background2:Int?=null
    fun valueBackgroud2(background:Int):AccountHomeBean{
        this.background2 = background
        return this
    }
}