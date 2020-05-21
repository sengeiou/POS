package com.epro.pos.ui.adapter

import android.content.Context
import android.view.View
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.GoodsCategory
import com.mike.baselib.utils.ext_setAllFalse
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.textColor

class GoodsCategoryAdapter(mContext: Context, categoryList: ArrayList<GoodsCategory>, layoutId: Int = R.layout.item_select_pop) :
        CommonAdapter<GoodsCategory>(mContext, categoryList, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: GoodsCategory)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun bindData(holder: ViewHolder, data: GoodsCategory, position: Int) {
        holder.setText(R.id.tvContent, "" + data.typeName)
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