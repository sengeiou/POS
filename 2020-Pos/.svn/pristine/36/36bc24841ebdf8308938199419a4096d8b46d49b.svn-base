package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter


/**
 * Created by xuhao on 2017/11/29.
 * desc: 分类的 Adapter
 */

class HorizontalTabAdapter(mContext: Context, itemList: ArrayList<Item>, layoutId: Int= R.layout.item_horizontal_tab,isRecyclable:Boolean=false) :
        CommonAdapter<Item>(mContext, itemList, layoutId,isRecyclable) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onItemClick(item:Item,position: Int)
    }
    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Item, position: Int) {
        holder.setText(R.id.tvTab,data.content)
      //  holder.itemView.setBackgroundColor(mContext.resources.getColor( if (data.judgeValue) R.color.white else android.R.color.transparent))
        holder.getView<View>(R.id.vLine).setBackgroundColor(mContext.resources.getColor( if (data.judgeValue) R.color.mainColor else R.color.transparent))
        val textView=holder.getView<TextView>(R.id.tvTab)
        textView.paint.isFakeBoldText=data.judgeValue
        textView.setTextColor(if (data.judgeValue) mContext.resources.getColor(R.color.mainColor) else mContext.resources.getColor(R.color.mainTextColor))
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
