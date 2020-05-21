package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class KeyItem(val id:Int, var content:String, override var judgeValue: Boolean=false):Judgable{
    override fun judge(): Boolean {
        return judgeValue
    }

    var content2=""
    fun valueContent2(content: String):KeyItem{
        this.content2=content
        return this
    }
    var isTouch= false
}