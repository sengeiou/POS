package com.epro.pos.ui.adapter

import android.content.Context
import android.view.View
import com.epro.pos.R
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class MoreCategorysPopWindowAdapter(mContext: Context, itemList: ArrayList<String>, layoutId: Int= R.layout.item_more_category) :
        CommonAdapter<String>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:String,position: Int)
    }

    var onItemClickListener:OnItemClickListener?=null

    override fun bindData(holder: ViewHolder, data: String, position: Int) {
        holder.setText(R.id.tvName,data)
        holder.setOnItemClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data,position)
                }
            }
        })
    }
}