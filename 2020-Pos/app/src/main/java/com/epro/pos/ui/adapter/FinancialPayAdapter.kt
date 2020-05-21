package com.epro.pos.ui.adapter


import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.GetFinancialScanBean
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.ext_formatAmountWithUnit
import com.mike.baselib.utils.ext_setLeftImageResource


/**
 * desc: 分类的 Adapter
 */

class FinancialPayAdapter(mContext: Context, itemList: ArrayList<GetFinancialScanBean.PayFinance>, layoutId: Int=R.layout.item_financial_pay) :
        CommonAdapter<GetFinancialScanBean.PayFinance>(mContext, itemList, layoutId,false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:GetFinancialScanBean.PayFinance)
    }

    val unit=AppBusManager.getAmountUnit()
    var onItemClickListener:OnItemClickListener?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: GetFinancialScanBean.PayFinance, position: Int) {
        holder.setText(R.id.tvPay,PosBusManager.getPayModeText(data.payType))
        val tvPay=holder.getView<TextView>(R.id.tvPay)
        tvPay.ext_setLeftImageResource(PosBusManager.getPayModeIcon(data.payType))
        holder.setText(R.id.tvTotal,(data.offlineSellTotalAmount.toDouble()+data.onlineSellTotalAmount.toDouble()).ext_formatAmountWithUnit())
        val rvOnLine=holder.getView<RecyclerView>(R.id.rvOnLine)
        val rvOutLine=holder.getView<RecyclerView>(R.id.rvOutLine)
//        val titles= arrayListOf("累计销售总额($unit)", "待结算金额$unit)", "已结算金额$unit)", "已退款金额$unit)")
//        val onlineValues= arrayListOf(data.onlineSellTotalAmount,data.onlineOpenAmount,data.onlineCloseAmount,data.onlineRefundAmount)
//        val outlineValues= arrayListOf(data.offlineSellTotalAmount,data.offlineOpenAmount,data.offlineCloseAmount,data.offlineRefundAmount)
        val titles= arrayListOf(AppContext.getInstance().getString(R.string.cumulative_total_sales)+"($unit)", AppContext.getInstance().getString(R.string.amount_refunded)+"($unit)")
        val onlineValues= arrayListOf(data.onlineSellTotalAmount,data.onlineRefundAmount)
        val outlineValues= arrayListOf(data.offlineSellTotalAmount,data.offlineRefundAmount)
        val onlineList=ArrayList<Item>()
         for(i in titles.indices){
             onlineList.add(Item(i,titles[i]).valueContent2(onlineValues[i]))
         }
        val outLineList=ArrayList<Item>()
         for(i in titles.indices){
             outLineList.add(Item(i,titles[i]).valueContent2(outlineValues[i]))
         }
        rvOnLine.layoutManager=LinearLayoutManager(mContext)
        rvOutLine.layoutManager=LinearLayoutManager(mContext)
        rvOnLine.adapter=FinancialPayChildAdapter(mContext,onlineList)
        rvOutLine.adapter=FinancialPayChildAdapter(mContext, outLineList)
        if(position==mData.size-1){
            val params=holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
            params.rightMargin=0
            holder.itemView.layoutParams=params
        }
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
