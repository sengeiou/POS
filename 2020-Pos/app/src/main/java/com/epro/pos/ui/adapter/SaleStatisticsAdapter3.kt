package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.SaleItem
import com.mike.baselib.utils.ext_setLeftImageResource

/**
 * desc: 分类的 Adapter
 */

class SaleStatisticsAdapter3(mContext: Context, itemList: ArrayList<SaleItem>, layoutId: Int=R.layout.item_sale_statistics3) :
        CommonAdapter<SaleItem>(mContext, itemList, layoutId,false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:SaleItem)
    }

    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: SaleItem, position: Int) {
        if(position==0){
            holder.getView<View>(R.id.rlLine).visibility=View.GONE
        }else{
            holder.getView<View>(R.id.rlLine).visibility=View.VISIBLE
        }
        holder.setText(R.id.tvTitle,data.content)
        holder.setText(R.id.tvTitle2,data.content2)
        holder.setImageResource(R.id.ivIcon,data.icon)
        val tvPercent=holder.getView<TextView>(R.id.tvPercent)
        tvPercent.setBackgroundResource(data.tag)
        if(position==0){
            tvPercent.ext_setLeftImageResource(R.mipmap.downward_trend)
        }else{
            tvPercent.ext_setLeftImageResource(R.mipmap.upward_trend)
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
