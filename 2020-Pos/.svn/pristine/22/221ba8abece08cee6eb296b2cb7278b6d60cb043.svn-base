package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item

/**
 * desc: 分类的 Adapter
 */

class SaleStatisticsAdapter1(mContext: Context, itemList: ArrayList<Item>, layoutId: Int=R.layout.item_sale_statistics1) :
        CommonAdapter<Item>(mContext, itemList, layoutId,false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:Item)
    }

    init {
        val titles = arrayOf(mContext.getString(R.string.today_order), mContext.getString(R.string.today_sales), mContext.getString(R.string.total_sales_yesterday), mContext.getString(R.string.total_sales_in_the_last_7_days))
        val bgRes = arrayOf(R.drawable.shape_bg_sale1, R.drawable.shape_bg_sale2,R.drawable.shape_bg_sale3, R.drawable.shape_bg_sale4)
        val icons = arrayOf(R.mipmap.icon_order_sales1, R.mipmap.icon_order_sales2, R.mipmap.icon_order_sales3,R.mipmap.icon_order_sales4)
        for (i in titles.indices) {
            itemList.add(Item(icons[i], titles[i]).valueBgRes(bgRes[i]))
        }
    }

    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        holder.setText(R.id.tvTitle,data.content)
        holder.itemView.setBackgroundResource(data.bgRes)
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
