package com.epro.pos.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.AccountHomeBean
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

class AccountSetAdapter(mContext: Context, list: ArrayList<AccountHomeBean>, layoutId: Int = R.layout.set_account_adapter) :
        CommonAdapter<AccountHomeBean>(mContext, list, layoutId) {

    interface OnItemClickListener{
       fun onClick(item:AccountHomeBean,postion:Int)
    }

    var onItemClickListener:OnItemClickListener?=null

    override fun bindData(holder: ViewHolder, data: AccountHomeBean, position: Int) {
        holder.setText(R.id.tvTitle,data.content)
        holder.setBackgroundResource(R.id.rlBg,data.background!!)

        if(1 == position || 3 == position){
            val p = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            p.bottomMargin = DisplayManager.dip2px(12.toFloat())!!
            holder.itemView.layoutParams = p
            holder.getView<View>(R.id.vLine).visibility = View.GONE
        }
        holder.setOnItemClickListener(object : View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data,position)
                }
            }
        })
    }
}