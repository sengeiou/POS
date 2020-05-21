package com.epro.pos.ui.adapter


import android.content.Context
import android.drm.DrmStore
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.LinearLayout
import android.widget.TextView
import com.classic.common.MultipleStatusView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.FinancialBill
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.ui.view.CustomSearchView
import com.epro.pos.ui.view.CustomTimerPicker
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.DateUtils
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.LogTools
import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import kotlinx.android.synthetic.main.layout_custom_searchview.view.*


/**
 * desc: 分类的 Adapter
 */


class FinancialBillListAdapter(mContext: Context, list: ArrayList<FinancialBill>, layoutId: MultipleType<FinancialBill>) :
        CommonAdapter<FinancialBill>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: FinancialBill)
    }
    interface OnSearchClickListener {
        fun onSearchClick()
    }

    var onItemClickListener: OnItemClickListener? = null
    var onSearchClickListener: OnSearchClickListener? = null


    val unit = AppBusManager.getAmountUnit()
   // val titleList = arrayListOf("序号", "账单编号", "账单来源", "账单金额($unit)", "收取佣金($unit)", "本期应结($unit)", "出账时间")
    val titleList = arrayListOf(mContext.getString(R.string.serial_number), mContext.getString(R.string.transaction_type), mContext.getString(R.string.order_number), mContext.getString(R.string.order_type), mContext.getString(R.string.payment_method), mContext.getString(R.string.transaction_completion_time),mContext.getString(R.string.payment_amount)+"($unit)",mContext.getString(R.string.handling_fee)+"($unit)",mContext.getString(R.string.amount_due)+"($unit)")
    val weights = arrayListOf(0.5F, 1F, 2F, 1F, 1F, 2F,1F,1F,1F)
    var customTimerPicker: CustomTimerPicker? = null
    var customSearchView: CustomSearchView? = null
    var multipleStatusView: MultipleStatusView? = null
    var llEmpty: LinearLayout? = null
    var startTime = DateUtils.getCurrentDay() + " 00:00:00"
    var endTime = DateUtils.getCurrentDay() + " 23:59:59"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = super.onCreateViewHolder(parent, viewType)
        if (viewType == R.layout.item_financialbill_showlist) {
            val container = holder.itemView.findViewById<LinearLayout>(R.id.llContainer)
            container.removeAllViews()
            for (i in weights.indices) {
                val tv = TextView(mContext)
                tv.id = i
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.toFloat())
                tv.setPadding(0, 0, DisplayManager.dip2px(3F)!!, 0)
                tv.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, weights[i])
                tv.layoutParams = params
                container.addView(tv)
            }
        }
        return holder
    }

    override fun setData(list: ArrayList<FinancialBill>){

        val oldSize=mData.size
        val t=mData[0]
        mData.clear()
        mData.add(t)
        mData.addAll(list)
        notifyDataSetChanged()
//        val range=list.size-oldSize
//        notifyItemRangeInserted(oldSize+1,range)
        logTools.d(mData.size)
    }

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: FinancialBill, position: Int) {
        if (position == 0) {
            if(data.financialScan!=null){
                holder.setText(R.id.tvAlert,mContext.getString(R.string.pls_the_actual_amount)+data.financialScan!!.dateTime)
            }
            customSearchView = holder.getView(R.id.customSearchView)
            customTimerPicker = holder.getView(R.id.customTimerPicker)
            customTimerPicker!!.timeType=CustomTimerPicker.DAY_TYPE
            customTimerPicker!!.setDefaultTime(startTime.split(" ")[0],endTime.split(" ")[0])
            customTimerPicker!!.showTimeLocation=CustomTimerPicker.TIME_FILTER_CENTER
           // customSearchView!!.etSearch.imeOptions=EditorInfo.IME_ACTION_NONE
            customSearchView!!.etSearch.setOnKeyListener(object :View.OnKeyListener{
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    logTools.d(keyCode)
                    if(keyCode==KeyEvent.KEYCODE_ENTER||keyCode==KeyEvent.KEYCODE_SEARCH){
                        onSearchClickListener?.onSearchClick()
                        return true
                    }
                  return false
                }
            })

            llEmpty=holder.getView(R.id.llEmpty)
            if(mData.size==1){
                llEmpty!!.visibility=View.VISIBLE
            }else{
                llEmpty!!.visibility=View.GONE
            }
            val llTitle = holder.getView<LinearLayout>(R.id.llTitle)
            llTitle.removeAllViews()
            //添加头标题
            for (i in titleList.indices) {
                val tv = TextView(mContext)
                tv.text = titleList[i]
                tv.paint.isFakeBoldText = true
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.toFloat())
                tv.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, if (weights.size == 0) 1.toFloat() else weights[i])
                tv.layoutParams = params
                llTitle.addView(tv)
            }

            if(data.financialScan==null){
                return
            }
            LogTools(this).d("bindData0")

            /*******************************************************************************/
            var itemList=ArrayList<Item>()
            val unit=AppBusManager.getAmountUnit()
//            val titles = arrayOf("累计销售总额($unit)", "待结算总额($unit)", "已结算总额($unit)", "已退款总额($unit)")
//            val icons = arrayOf(R.mipmap.icon_sales1, R.mipmap.icon_mybusiness3, R.mipmap.icon_financial_amount3,R.mipmap.icon_financial_amount4)
            val titles = arrayOf(mContext.getString(R.string.cumulative_total_sales)+"($unit)", mContext.getString(R.string.total_refunded)+"($unit)")
            val icons = arrayOf(R.mipmap.icon_sales1,R.mipmap.icon_financial_amount4)
           // val values = arrayOf(data.financialScan!!.sellTotalAmount,data.financialScan!!.openAmount, data.financialScan!!.closeAmount,data.financialScan!!.refundAmount)
            val values = arrayOf(data.financialScan!!.sellTotalAmount,data.financialScan!!.refundAmount)
            for (i in titles.indices) {
                itemList.add(Item(icons[i], titles[i]).valueContent2(values[i]))
            }
            val rvAmounts = holder.getView<RecyclerView>(R.id.rvAmount)
            rvAmounts.layoutManager = GridLayoutManager(mContext,titles.size)
            rvAmounts.adapter = FinancialAmountAdapter(mContext, itemList)

            /*******************************************************************************/
            val list=data.financialScan!!.payFinances
            if(list.isNotEmpty()){
                val rvPays = holder.getView<RecyclerView>(R.id.rvPays)
                rvPays.layoutManager = GridLayoutManager(mContext,list.size)
                rvPays.adapter = FinancialPayAdapter(mContext, list)
            }
        } else {
            holder.setText(0, position.toString())
            holder.setText(1, PosBusManager.getTradeTypeText(data.tradeType))
            holder.setText(2, data.orderSn)
            holder.setText(3, PosBusManager.getOrderTypeText(data.orderType))
            holder.setText(4, PosBusManager.getPayModeText(data.payType))
            holder.setText(5, data.orderCloseTime)
            holder.setText(6, data.amount)
            holder.setText(7, data.brokerage)
            holder.setText(8, data.left)
        }
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }

            }
        })
    }
}
