package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.CashierRecon
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.ext_formatAmountWithUnit

/**
 * desc: 分类的 Adapter
 */

class CashierReconcDetailTitleAdapter(mContext: Context, itemList: ArrayList<CashierRecon.PayModeCashierRecon>, layoutId: Int=R.layout.item_cashier_reconc_detail_title) :
        CommonAdapter<CashierRecon.PayModeCashierRecon>(mContext, itemList, layoutId,false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:CashierRecon.PayModeCashierRecon)
    }


    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: CashierRecon.PayModeCashierRecon, position: Int) {
        holder.setText(R.id.tvBigTitle,data.payModeName)
        if(position==mData.size-1){
            holder.setViewVisible(R.id.tvTotal)
            holder.setViewVisible(R.id.tvAmount)
            holder.setText(R.id.tvAmount,data.totalAmount.ext_formatAmountWithUnit())
            holder.setText(R.id.tvTitle1,mContext.getString(R.string.number_of_income))
            holder.setText(R.id.tvTitle2,mContext.getString(R.string.number_of_expenditures))
        }
        holder.setText(R.id.tvContent1,data.saleCount)
        holder.setText(R.id.tvContent2,data.refundCount)
        holder.setText(R.id.tvContent3,data.saleAmount.ext_formatAmountWithUnit())
        holder.setText(R.id.tvContent4,data.refundAmount.ext_formatAmountWithUnit())

        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
