package com.epro.pos.ui.adapter


import android.content.Context
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.*
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.interface_.Judgable
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_setAllValue
import com.mike.baselib.utils.ext_setLeftImageResource
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.padding

/**
 * desc: 分类的 Adapter
 */

class ShowListAdapter<D>(mContext: Context, list: ArrayList<D>, layoutId: Int = R.layout.item_showlist) :
        CommonAdapter<D>(mContext, list, layoutId) {

    interface OnItemClickListener<D> {
        fun onClick(item: D)
    }

    interface OnItemSelectListener<D> {
        fun onItemSelect(item: D)
    }

    override fun setData(list: ArrayList<D>) {
        if (list.isNotEmpty()) {
            val d = list[0]
            if (d is Judgable) {
                ext_setAllValue(list as ArrayList<Judgable>, isAllSelect)
            }
        }
        super.setData(list)
    }

    var onItemClickListener: OnItemClickListener<D>? = null
    var onItemSelectListener: OnItemSelectListener<D>? = null
    var textWeights = ArrayList<Float>()
    var isAllSelect = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = super.onCreateViewHolder(parent, viewType)
        val container = holder.itemView.findViewById<LinearLayout>(R.id.llContainer)
        container.removeAllViews()
        for (i in textWeights.indices) {
            val tv = TextView(mContext)
            tv.id = i
            tv.padding = DisplayManager.dip2px(5f)!!
            tv.gravity = Gravity.CENTER_VERTICAL
            tv.maxEms=10
            tv.maxLines=2
            tv.ellipsize= TextUtils.TruncateAt.END
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.toFloat())
            tv.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, textWeights[i])
            tv.layoutParams = params
            container.addView(tv)
        }
        return holder
    }

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: D, position: Int) {

        val tv = holder.getView<TextView>(0)
        if (data is Judgable) {
            tv.compoundDrawablePadding = DisplayManager.dip2px(16F)!!
            tv.ext_setLeftImageResource(if (data.judge()) R.mipmap.icon_item_checked else R.mipmap.icon_item_uncheck)
            tv.setOnClickListener(View.OnClickListener {
                data.judgeValue = !data.judgeValue
                notifyItemChanged(position)
                onItemSelectListener?.onItemSelect(data)
            })
        } else {
            tv.ext_setLeftImageResource(null)
            tv.compoundDrawablePadding = 0
        }
        if (data is GoodsFile) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.goodsName)
            holder.setText(2, data.productBarCode)
            holder.setText(3, data.specificationsValueNames)
            holder.setText(4, data.goodsTypeName.toString())//商品类别
            holder.setText(5, PosBusManager.getGoodsTypeText(data.goodsBinding))
            holder.setText(6, data.buyingPrice)
            holder.setText(7, data.retailPrice)
            holder.setText(8, data.onlineSalesPrice)

            val tv1=holder.getView<TextView>(1)
            tv1.paint.isFakeBoldText=true
        } else if (data is OrderRecord) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, PosBusManager.getTradeTypeText(data.tradeType))
            holder.setText(2, data.orderSn)
            holder.setText(3, data.createTime)
            holder.setText(4, PosBusManager.getOrderTypeText(data.orderType))
            holder.setText(5, PosBusManager.getPayModeText(data.payType))
            holder.setText(6, PosBusManager.getOrderStatusShowText(data.orderStatus))
            holder.setText(7, data.orderActualAmount)
        } else if (data is GoodsRecord) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, PosBusManager.getTradeTypeText(data.tradeType))
            holder.setText(2, data.productSn)
            holder.setText(3, data.createTime)
            holder.setText(4, data.goodsName)
            holder.setText(5, data.goodsSpecifitionNameValue)
            holder.setText(6, data.category)
            holder.setText(7, data.purchasePrice)
            holder.setText(8, data.salePrice)
            holder.setText(9, data.productCount.toString())
            holder.setText(10, data.totalPrice)
        } else if (data is SalesRanking) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, PosBusManager.getTradeTypeText(data.tradeType))
            holder.setText(2, data.productSn)
            holder.setText(3, data.goodsName)
            holder.setText(4, data.goodsSpecifitionNameValue)
            holder.setText(5, data.category)
            holder.setText(6, data.sellingCount.toString())
        } else if (data is GrossProfitGather) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, PosBusManager.getTradeTypeText(data.tradeType))
            holder.setText(2, data.productSn)
            holder.setText(3, data.goodsName)
            holder.setText(4, data.goodsSpecifitionNameValue)
            holder.setText(5, data.category)
            holder.setText(6, data.sellingCount.toString())
            holder.setText(7, data.margin)
            holder.setText(8, (data.marginRate * 100).ext_formatAmount() + "%")
        } else if (data is CashierRecon) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.statementId)
            holder.setText(2, data.cashierId)
            holder.setText(3, data.cashierName)
            holder.setText(4, data.createTime)
            holder.setText(5, data.totalAmount)
        } else if (data is StockQueryProduct) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.productBarCode)
            holder.setText(2, data.goodsName)
            holder.setText(3, data.specificationsValueNames)
            holder.setText(4, data.goodsUnitName)
            holder.setText(5, data.goodsTypeName)
            holder.setText(6, data.productNumber.toString())
            holder.setText(7, data.buyingPrice)
            holder.setText(8, (data.buyingPrice.toDouble() * data.productNumber).ext_formatAmount())
            holder.setText(9, data.retailPrice)//零售价
            holder.setText(10, (data.retailPrice.toDouble() * data.productNumber).ext_formatAmount())//零售金额


            val tv6=holder.getView<TextView>(6)
            if(data.productNumber-data.stockEarlyNumr<=0){
                tv6.setTextColor(mContext.resources.getColor(R.color.mainColor))
            }else{
                tv6.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
            }
        } else if (data is StockGoodsPutin) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.stockCode)
            holder.setText(2, data.stockMoney)
            holder.setText(3, PosBusManager.getGoodsStatusText(data.stockStatus))
            holder.setTextColor(3,mContext.resources.getColor(if(data.stockStatus==PosConst.STOCK_STATUS_IN) R.color.mainTextColor else R.color.mainColor))
            holder.setText(4, data.createDate)
        } else if (data is StockTaking) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.inventoryCode)
            holder.setText(2, data.profitNumber)
            holder.setText(3, PosBusManager.getStockTakingText(data.inventoryStatus))
            holder.setText(4, data.createDate)
        } else if (data is StockOutinDetail) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.createDate)
            holder.setText(2, data.stockCode)
            holder.setText(3, PosBusManager.getStockSourceText(data.stockSource))
            holder.setText(4, data.productBarCode)
            holder.setText(5, data.goodsName)
            holder.setText(6, data.specificationsValueNames)
            holder.setText(7, data.stockNumber.toString())
            holder.setText(8, data.buyingPrice)
            holder.setText(9, (data.buyingPrice.toDouble() * data.stockNumber).ext_formatAmount())
        } else if (data is PosTradeOrder) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.orderSn)
            holder.setText(2, PosBusManager.getOrderTypeText(data.orderType))
            holder.setText(3, PosBusManager.getPayModeText(data.payType))
            holder.setText(4, data.orderActualAmount)
            holder.setText(5, data.createTime)
        } else if (data is UpperShelfGoods) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.goodsID)
            holder.setText(2, data.goodsName)
            holder.setText(3, data.goodsTypeName)
            holder.setText(4, data.shoppingMallName)
            holder.setText(5, PosBusManager.getUpperShelfGoodsMinMaxPrice(data).toString())
        } else if (data is OnlineOrder) {
            val tv0 = holder.getView<TextView>(0)
            if ((data as Judgable).judge()) {
                tv0.ext_setLeftImageResource(if (data.orderStatus == PosConst.ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION) R.mipmap.icon_item_checked else null)
            } else {
                tv0.ext_setLeftImageResource(if (data.orderStatus == PosConst.ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION) R.mipmap.icon_item_uncheck else null)
            }
            tv0.isEnabled=data.orderStatus == PosConst.ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION
            //序号、订单编号、付款方式、付款金额（金额符号）、订单创建时间
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.orderSn)
            holder.setText(2,PosBusManager.getPayModeText(data.payType))
            holder.setText(3, data.orderActualAmount)
            holder.setText(4, data.createTime)
        } else if (data is BillOrder) {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, PosBusManager.getTradeTypeText(data.tradeType))
            holder.setText(2, data.orderSn)
            holder.setText(3, data.orderCloseTime)
            holder.setText(4, data.paySn)
            holder.setText(5, data.amount)
            holder.setText(6, data.brokerage)
            holder.setText(7, data.left)
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
