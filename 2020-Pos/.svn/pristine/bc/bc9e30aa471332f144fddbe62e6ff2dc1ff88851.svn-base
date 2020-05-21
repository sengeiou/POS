package com.epro.pos.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.PendOrder
import com.mike.baselib.utils.ext_setAllFalse
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

class PendOrderAdapter(mContext: Context, pendOrderList: ArrayList<PendOrder>, layoutId: Int = R.layout.item_pend_order) :
        CommonAdapter<PendOrder>(mContext, pendOrderList, layoutId) {

    interface OnItemClickListener {
        fun onClick(pendOrder: PendOrder)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun bindData(holder: ViewHolder, data: PendOrder, position: Int) {
        holder.setText(R.id.tvNo, (1 + position).toString())
        val tvNo = holder.getView<TextView>(R.id.tvNo)
        val tvOrderNo = holder.getView<TextView>(R.id.tvOrderNo)
        tvOrderNo.text = getOrderNo(1 + position)
        tvNo.textColor = mContext.resources.getColor(if (data.judge()) R.color.mainColor else R.color.mainTextColor)
        tvOrderNo.textColor = mContext.resources.getColor(if (data.judge()) R.color.mainColor else R.color.mainTextColor)
        holder.itemView.backgroundColor = mContext.resources.getColor(if (data.judge()) R.color.mainColor_6 else R.color.transparent)
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

    fun getOrderNo(position: Int): String {
        if (position.toString().length == 1) {
            return "000" + position.toString()
        } else if (position.toString().length == 2) {
            return "00" + position.toString()
        } else if (position.toString().length == 3) {
            return "0" + position.toString()
        } else {
            return position.toString()
        }
    }
}