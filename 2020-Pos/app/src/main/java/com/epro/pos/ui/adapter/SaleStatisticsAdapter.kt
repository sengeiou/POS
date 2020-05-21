package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_setRightImageResource
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.textColor

/**
 * desc: 分类的 Adapter
 */

class SaleStatisticsAdapter(mContext: Context, itemList: ArrayList<Item>, layoutId: Int = R.layout.item_sale_statistics) :
        CommonAdapter<Item>(mContext, itemList, layoutId, false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item: Item)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        holder.setText(R.id.tvTitle, data.content)
        holder.setImageResource(R.id.ivFlag,data.icon)
        val params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        var tvTitle=holder.getView<TextView>(R.id.tvTitle)
        if ((position + 1) % 3 == 0) {
            params.rightMargin = 0
            holder.itemView.layoutParams = params
        }
        if(position>2){
            tvTitle.ext_setRightImageResource(R.drawable.selector_home_text_bg)
            tvTitle.compoundDrawablePadding=DisplayManager.dip2px(5F)!!
        }else{
            tvTitle.textColor = mContext.resources.getColor(R.color.secondaryTextColor)
            tvTitle.ext_setRightImageResource(null)
            holder.setText(R.id.saleSymbol,AppBusManager.getAmountUnit())
        }
        holder.setText(R.id.tvContent,data.content2)
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
