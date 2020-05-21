package com.epro.pos.ui.adapter


import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.LogTools
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter


/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class MainMenuAdapter(mContext: Context, itemList: ArrayList<Item>, layoutId: Int= R.layout.item_main_menu) :
        CommonAdapter<Item>(mContext, itemList, layoutId,false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onItemClick(item: Item,position: Int)
    }
    var onItemClickListener:OnItemClickListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        holder.setText(R.id.tvTab,data.content)
        holder.setImageResource(R.id.ivTab,if(data.judge()) data.icon2 else data.icon)
        holder.itemView.setBackgroundColor(mContext.resources.getColor( if (data.judgeValue) R.color.mainColor else android.R.color.transparent))
        val textView=holder.getView<TextView>(R.id.tvTab)
        textView.paint.isFakeBoldText=data.judgeValue
        if(data.content2.isNotEmpty()){
            val params=ViewGroup.LayoutParams(0,0)
            holder.itemView.layoutParams=params
        }

        if (0 == position){
            var p = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            p.topMargin = DisplayManager.dip2px(12.toFloat())!!
            holder.itemView.layoutParams = p
        }

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onItemClick(data,position)
                    for(item in mData){
                        item.judgeValue=item.content.equals(data.content)
                    }
                    notifyDataSetChanged()
                }

            }
        })
    }
}
