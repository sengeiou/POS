package com.epro.pos.mvp.model.bean

data class CreateGoodsItem(val icon:Int, var content:String,val isInput:Boolean=true){
    var content2=""
    fun valueContent2(content: String):CreateGoodsItem{
        this.content2=content
        return this
    }
    var bgRes=0
    fun valueBgRes(bgRes: Int):CreateGoodsItem{
        this.bgRes=bgRes
        return this
    }
}