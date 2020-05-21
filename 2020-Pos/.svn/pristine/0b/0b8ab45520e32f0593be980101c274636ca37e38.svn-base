package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.ext_setLeftImageResource
import org.jetbrains.anko.textColor

/**
 * desc: 分类的 Adapter
 */

class SaleStatisticsAdapter2(mContext: Context, itemList: ArrayList<Item>, layoutId: Int=R.layout.item_sale_statistics2) :
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
        val tvTitle=holder.getView<TextView>(R.id.tvTitle)
        tvTitle.ext_setLeftImageResource(data.icon)
        if(position==3){
            tvTitle.textColor=mContext.resources.getColor(R.color.pink_e75c5e)
            holder.setTextColor(R.id.tvContent,mContext.resources.getColor(R.color.pink_fb604b))
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
