package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.WaitdoItem
import org.jetbrains.anko.backgroundResource

/**
 * desc: 分类的 Adapter
 */

class WaitdoAdapter(mContext: Context, itemList: ArrayList<WaitdoItem>, layoutId: Int=R.layout.item_waitdo) :
        CommonAdapter<WaitdoItem>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:WaitdoItem)
    }

    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: WaitdoItem, position: Int) {
        holder.setText(R.id.tvContent,data.content2)
        holder.setText(R.id.tvTitle,data.content)
        holder.setBackgroundResource(R.id.vTag,data.icon)
        holder.setTextColor(R.id.tvContent,mContext.resources.getColor(data.textColor))
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
