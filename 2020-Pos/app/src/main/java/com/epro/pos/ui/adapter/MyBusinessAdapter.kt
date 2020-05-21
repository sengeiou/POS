package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_setRightImageResource
import org.jetbrains.anko.textColor

/**
 * desc: 分类的 Adapter
 */

class MyBusinessAdapter(mContext: Context, itemList: ArrayList<Item>, layoutId: Int = R.layout.item_my_business) :
        CommonAdapter<Item>(mContext, itemList, layoutId, false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item: Item,position: Int)
    }

    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        holder.setText(R.id.tvTitle, data.content)
        holder.setImageResource(R.id.ivFlag,data.icon)
        var tvTitle = holder.getView<TextView>(R.id.tvTitle)
        if ((position + 1) % 3 == 0) {
           holder.setViewGone(R.id.vRight)
        }
        if (mData.size - 1 == position) {
            holder.setViewGone(R.id.vBottom)
        }

        if(position>2){
            tvTitle.ext_setRightImageResource(R.drawable.selector_home_text_bg)
            tvTitle.compoundDrawablePadding= DisplayManager.dip2px(5F)!!
        }else{
            tvTitle.textColor = mContext.resources.getColor(R.color.secondaryTextColor)
            tvTitle.ext_setRightImageResource(null)
        }
        holder.setText(R.id.tvContent,data.content2)
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data,position)
                }
            }
        })
    }
}
