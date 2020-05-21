package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.KeyItem
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_setLeftImageResource
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * desc: 分类的 Adapter
 */

class KeyboardAdapter(mContext: Context, itemList: ArrayList<KeyItem>, layoutId: Int = R.layout.item_keyboard,isRecyclable:Boolean=false) :
        CommonAdapter<KeyItem>(mContext, itemList, layoutId,isRecyclable) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item: KeyItem)
    }

    interface OnTextChangeListener {
        fun onTextChange(text: String)
    }
    interface OnConfirmClickListener {
        fun onConfirmClick(text: String)
    }
    interface OnCancelClickListener {
        fun onCancelClick(text: String)
    }

    init {
        val contents = arrayOf("7", "8", "9", mContext.getString(R.string.delete), "4", "5", "6", mContext.getString(R.string.cancel), "1", "2", "3", mContext.getString(R.string.confirm), "00", "0", ".")
        for (i in contents.indices) {
            itemList.add(KeyItem(i, contents[i]))
        }
    }

    var onItemClickListener: OnItemClickListener? = null
    var onTextChangeListener: OnTextChangeListener? = null
    var onConfirmClickListener:OnConfirmClickListener?=null
    var onCancelClickListener:OnCancelClickListener?=null

    /**
     * 绑定数据
     */
    var text = ""

    override fun bindData(holder: ViewHolder, data: KeyItem, position: Int) {
        holder.setText(R.id.tvKey,data.content)
        val tvKey = holder.getView<TextView>(R.id.tvKey)
        val params = tvKey.layoutParams as ViewGroup.MarginLayoutParams
        if (data.content == mContext.getString(R.string.confirm)) {
            params.height = (DisplayManager.dip2px(110.toFloat())!!).toInt()
            tvKey.layoutParams = params
            tvKey.setBackgroundResource(R.drawable.selector_bg_btn_keyboard_green)
            tvKey.setTextColor(mContext.resources.getColor(R.color.white))
        } else {
            params.height = (DisplayManager.dip2px(53.toFloat())!!).toInt()
            tvKey.layoutParams = params
        }

        if (data.content == mContext.getString(R.string.delete)) {
            holder.setViewVisible(R.id.ivIcon)
            holder.setImageResource(R.id.ivIcon,R.mipmap.pos_delete)
            tvKey.text=""
        }

        if((position+1)%4==0){
            params.rightMargin=0
            tvKey.layoutParams=params
        }
        if(position>=mData.size-3){
            params.bottomMargin=0
            tvKey.layoutParams=params
        }

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }

                if (data.content == ".") {
                    if (text.isNotEmpty() && !text.contains(".")) {
                        text += data.content
                    }
                } else if (data.content == "00") {
                    if (text.isNotEmpty()) {
                        text += data.content
                    }
                } else if (data.content == "0") {
                    if (text.isNotEmpty()) {
                        text += data.content
                    } else {
                        text += data.content + "."
                    }
                } else if (data.content == mContext.getString(R.string.delete)) {
                    if (text.isNotEmpty()) {
                        text = text.substring(0, text.length - 1)
                    }
                } else if (data.content == mContext.getString(R.string.cancel)) {
                    text = ""
                    onCancelClickListener?.onCancelClick(text)
                    return
                } else if (data.content == mContext.getString(R.string.confirm)) {
                    onConfirmClickListener?.onConfirmClick(text)
                    return
                } else {
                    text += data.content
                }
                if(text.length>11){
                    text=text.substring(0,12)
                }
                onTextChangeListener?.onTextChange(text)
            }
        })
    }
}
