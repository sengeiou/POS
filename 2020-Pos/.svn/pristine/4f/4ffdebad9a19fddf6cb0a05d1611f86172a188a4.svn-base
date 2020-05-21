package com.epro.pos.ui.adapter

import android.content.Context
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.MessageCenterBean
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.backgroundResource

class MessageAdapter (mContext: Context, list:ArrayList<MessageCenterBean.MessageCenterOneBean>, layoutId:Int = R.layout.message_center_item): CommonAdapter<MessageCenterBean.MessageCenterOneBean>(mContext,list,layoutId) {

    var arrayList:MutableList<Int> = ArrayList()
    interface OnItemClickListener{
        fun onClick(item:MessageCenterBean.MessageCenterOneBean,position: Int)
    }

    var onItemClickListener:OnItemClickListener?=null

    fun updateListView(select: Int) {
        arrayList.add(select)
        notifyDataSetChanged()
    }

    override fun bindData(holder: ViewHolder, data: MessageCenterBean.MessageCenterOneBean, position: Int) {
            holder.setText(R.id.tvMessage,data.message)
            holder.setText(R.id.tvTimeMessage,data.createDate)
            var pointView =holder.getView<View>(R.id.pointMessage)
            var p = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            p.leftMargin = DisplayManager.dip2px(12.toFloat())!!
            p.rightMargin = DisplayManager.dip2px(12.toFloat())!!
            if (0 == position){
                p.topMargin = DisplayManager.dip2px(12.toFloat())!!
                holder.itemView.backgroundResource=R.drawable.shape_bg_white_half_radius4_top
            }else if(position==mData.size-1){
                p.bottomMargin = DisplayManager.dip2px(12.toFloat())!!
                holder.itemView.backgroundResource=R.drawable.shape_bg_white_half_radius4_bottom
            }else{
                p.bottomMargin = 0
                p.topMargin=0
                holder.itemView.backgroundResource=R.color.white
            }
            holder.itemView.layoutParams = p
            if (0 == data.isRead.toInt()){
                pointView.visibility = View.VISIBLE
            }else{
                pointView.visibility = View.GONE
            }

            for (index in arrayList){
                if (index == position){
                    pointView.visibility = View.GONE
                }
            }

            holder.setOnItemClickListener(View.OnClickListener {
                if (onItemClickListener!=null){
                    onItemClickListener!!.onClick(data,position)
                }
            })
    }

}