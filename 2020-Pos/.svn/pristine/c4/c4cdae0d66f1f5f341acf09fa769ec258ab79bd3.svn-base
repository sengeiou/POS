package com.epro.pos.ui.view


import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.KeyItem
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

/**
 * desc: 分类的 Adapter
 */

class PayBtnView(mContext: Context,container:LinearLayout ) {
    // 点击弹窗取消按钮回调
//    interface OnItemClickListener {
//        fun onClick(item: KeyItem)
//    }
//
//    interface OnTextChangeListener {
//        fun onTextChange(text: String)
//    }
//
//    val mData=ArrayList<KeyItem>()
//    var mContext:Context?=null
//    var mContainer:LinearLayout?=null
//    init {
//        this.mContext=mContext
//        this.mContainer=container
//        val unit = AppBusManager.getAmountUnit()
//        val contents = arrayOf(mContext.getString(R.string.cash), mContext.getString(R.string.octopus), mContext.getString(R.string.bank_card), "VISA", "Apple Pay", mContext.getString(R.string.weixin), mContext.getString(R.string.zhifubao), mContext.getString(R.string.facepay), "$unit 100", "$unit 50", "$unit 20", "$unit 10")
//        for (i in contents.indices) {
//            mData.add(KeyItem(i, contents[i], i == 0))
//        }
//    }
//
//    var onItemClickListener: OnItemClickListener? = null
//    var onTextChangeListener: OnTextChangeListener? = null
//
//    /**
//     * 绑定数据
//     */
//    var text = ""
//
//    var lastTime = 0L
//    //var width= ((DisplayManager.getScreenWidth()!! * 9 / 10F-DisplayManager.dip2px(20F)!!)*(13/23)-DisplayManager.dip2px(12F)!!)/4F
//    override fun bindData(holder: ViewHolder, data: KeyItem, position: Int) {
//        holder.setText(R.id.tvKey, data.content+position)
//        val tvKey = holder.getView<TextView>(R.id.tvKey)
//        val params = tvKey.layoutParams as ViewGroup.MarginLayoutParams
//        if ((position + 1) % 4 == 0) {
//            params.rightMargin = 0
//            tvKey.layoutParams = params
//        }
//        if (text.contains("0")) {//金额键
//
//        } else { //付款方式键
//            tvKey.setBackgroundResource(if (data.judge()) R.drawable.selector_bg_btn_red_radius2 else R.drawable.selector_bg_btn_keyboard)
//            tvKey.setTextColor(mContext.resources.getColor(if (data.judge()) R.color.mainMatchColor else R.color.mainTextColor))
//        }
//
//        if (position == mData.size - 4) {
//            tvKey.textColor = mContext.resources.getColor(R.color.mainColor)
//            tvKey.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24.toFloat())
//        }
//        if (position == mData.size - 3) {
//            tvKey.textColor = mContext.resources.getColor(R.color.blue_3388ff)
//            tvKey.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24.toFloat())
//        }
//        if (position == mData.size - 2) {
//            tvKey.textColor = mContext.resources.getColor(R.color.yellow_ffbe00)
//            tvKey.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24.toFloat())
//        }
//        if (position == mData.size - 1) {
//            tvKey.textColor = mContext.resources.getColor(R.color.purple_be5ae9)
//            tvKey.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24.toFloat())
//        }
//        holder.setOnItemClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                if (data.content == mContext.getString(R.string.weixin) || data.content == mContext.getString(R.string.zhifubao)) {
//                    if (System.currentTimeMillis() - lastTime < 1000) {
//                        return
//                    }
//                    lastTime = System.currentTimeMillis()
//                }
//                if (onItemClickListener != null) {
//                    onItemClickListener!!.onClick(data)
//                }
//                text = data.content
//                if (text.contains("0")) {//金额键
//                    text = text.split(" ")[1]
//                } else { //付款方式键
//                    for (item in mData) {
//                        item.judgeValue = item.content.equals(data.content)
//                    }
//                    notifyDataSetChanged()
//                }
//                onTextChangeListener?.onTextChange(text)
//            }
//        })
//    }
}
