package com.epro.pos.ui.view

import android.content.Context
import android.text.format.DateFormat
import android.widget.TextView
import com.epro.pos.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF
import java.text.DecimalFormat

class OrderMarkerView(context: Context,layoutRes:Int= R.layout.layout_markerview) :MarkerView(context,layoutRes) {

    private var tvContent: TextView?=null
    private val format = DecimalFormat("0.00")

    init {

    }

    //显示的内容
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        super.refreshContent(e, highlight)
    }

    //标记相对于折线图的偏移量
    override fun getOffset(): MPPointF {
        return MPPointF((-(width / 2)).toFloat(), (-height).toFloat())
    }

    //时间格式化（显示今日往前30天的每一天日期）
    fun format(x: Float): String {
        val format = DateFormat.format("MM月dd日",
                System.currentTimeMillis() - (30 - x.toInt()).toLong() * 24 * 60 * 60 * 1000)
        return format.toString()
    }
}