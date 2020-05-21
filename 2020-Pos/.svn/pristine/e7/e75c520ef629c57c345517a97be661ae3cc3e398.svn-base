package com.epro.pos.mvp.model.bean

import android.view.View
import com.epro.pos.R
import com.mike.baselib.interface_.Judgable

data class Item(val icon:Int, var content:String, override var judgeValue: Boolean=false):Judgable{
    override fun judge(): Boolean {
       return judgeValue
    }

    var content2=""
    fun valueContent2(content: String):Item{
        this.content2=content
        return this
    }
    var bgRes=0
    fun valueBgRes(bgRes: Int):Item{
        this.bgRes=bgRes
        return this
    }
    var icon2=0
    fun valueIcon2(icon2: Int):Item{
        this.icon2=icon2
        return this
    }
    var color=0
    fun valueColor(color: Int):Item{
        this.color=color
        return this
    }

    var visiblity= View.VISIBLE
    fun valueVisiblity(visiblity: Int):Item{
        this.visiblity=visiblity
        return this
    }

}