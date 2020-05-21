package com.epro.pos.ui.adapter

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.RefundReson
import com.mike.baselib.utils.ext_setAllFalse
import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.textColor

class SelectRefundReasonAdapter(mContext: Context, categoryList: ArrayList<RefundReson>, layoutId: MultipleType<RefundReson>) :
        CommonAdapter<RefundReson>(mContext, categoryList, layoutId, false) {

    interface OnItemClickListener {
        fun onClick(item: RefundReson)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun bindData(holder: ViewHolder, data: RefundReson, position: Int) {
        holder.setText(R.id.tvContent, data.reason)
        val tvContent = holder.getView<TextView>(R.id.tvContent)
        tvContent.textColor = mContext.resources.getColor(if (data.judge()) R.color.mainColor else R.color.mainTextColor)
        holder.setImageResource(R.id.ivIcon, if (data.judge()) R.mipmap.select else R.color.transparent)
        if (position == mData.size - 1) {
            val edit = holder.getView<EditText>(R.id.edtReason)
            edit.addTextChangedListener(ReasonChangerWatcher(holder, position))
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    if (!data.judge()) {
                        for (i in mData) {
                            ext_setAllFalse(mData)
                        }
                        data.judgeValue = true
                        notifyDataSetChanged()
                    }
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }

    inner class ReasonChangerWatcher(holder: ViewHolder, position: Int) : TextWatcher {
        var holder: ViewHolder? = null
        var reson: RefundReson? = null

        init {
            this.holder = holder
            this.reson = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0.toString().isNotEmpty()) {
                reson!!.reason2 = p0.toString()
            } else {
                reson!!.reason2 = ""
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }
}