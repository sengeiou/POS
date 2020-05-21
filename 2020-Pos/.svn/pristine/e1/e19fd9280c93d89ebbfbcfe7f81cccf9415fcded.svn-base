package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.CommonItem
import com.epro.pos.mvp.model.bean.Item

/**
 * desc: 分类的 Adapter
 */

class CommonItemAdapter(mContext: Context, itemList: ArrayList<CommonItem>, layoutId: Int=R.layout.item_commonitem) :
        CommonAdapter<CommonItem>(mContext, itemList, layoutId) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:CommonItem)
    }

    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: CommonItem, position: Int) {
        holder.setText(R.id.tvTitle,data.content)
        holder.itemView.setBackgroundResource(data.bgRes)
        val params=holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        params.bottomMargin=data.marginBottom
        holder.itemView.layoutParams=params
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
