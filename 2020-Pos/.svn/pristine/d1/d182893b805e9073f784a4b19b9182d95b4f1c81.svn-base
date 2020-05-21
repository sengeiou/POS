package com.epro.pos.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Shopper
import com.mike.baselib.utils.ext_setAllFalse
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.textColor

class SelectShopperAdapter(mContext: Context, categoryList: ArrayList<Shopper>, layoutId: Int = R.layout.item_select_pop) :
        CommonAdapter<Shopper>(mContext, categoryList, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: Shopper)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun bindData(holder: ViewHolder, data: Shopper, position: Int) {
        holder.setText(R.id.tvContent, data.name)
        val tvContent=holder.getView<TextView>(R.id.tvContent)
        tvContent.textColor=mContext.resources.getColor(if(data.judge()) R.color.mainColor else R.color.mainTextColor)
        holder.setImageResource(R.id.ivIcon,if(data.judge()) R.mipmap.select else R.color.transparent)
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    if(!data.judge()){
                        for(i in mData){
                            ext_setAllFalse(mData)
                        }
                        data.judgeValue=true
                        notifyDataSetChanged()
                    }
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}