package com.epro.pos.mvp.model.bean

import com.mike.baselib.interface_.Judgable

data class SaleItem(val value:String, var content: String,val icon:Int,val tag:Int){
    var content2=""
    fun valueContent2(content: String):SaleItem{
        this.content2=content
        return this
    }
    var bgRes=0
    fun valueBgRes(bgRes: Int):SaleItem{
        this.bgRes=bgRes
        return this
    }
}