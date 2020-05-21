package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.AppBusManager

/**
 * desc: 分类的 Adapter
 */

class FinancialAmountAdapter(mContext: Context, itemList: ArrayList<Item>, layoutId: Int=R.layout.item_financial_amount) :
        CommonAdapter<Item>(mContext, itemList, layoutId,false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:Item)
    }


    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        holder.setText(R.id.tvTitle,data.content)
        holder.setText(R.id.tvContent,data.content2)
        holder.setImageResource(R.id.ivFlag,data.icon)
        if(position==mData.size-1){
            val params=holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            params.rightMargin=0
            holder.itemView.layoutParams=params
        }

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
