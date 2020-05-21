package com.epro.pos.ui.adapter


import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.StockTaking
import com.epro.pos.utils.PosConst
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_isPureNumber_orDecimal
import com.mike.baselib.utils.ext_setLeftImageResource
import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.hintTextColor

/**
 * desc: 分类的 Adapter
 */

class EditStockTakingDetailAdapter(mContext: Context, list: ArrayList<StockTaking.StockProductInfo>, layoutId: MultipleType<StockTaking.StockProductInfo>) :
        CommonAdapter<StockTaking.StockProductInfo>(mContext, list, layoutId, false) {

    interface OnItemClickListener {
        fun onClick(item: StockTaking.StockProductInfo)
    }

    interface OnAddClickListener {
        fun onAddClick()
    }

    interface OnNumChangeListener {
        fun onNumChange(totalNum: Long, sysTotalNum: Int, profitLossAmount: String)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onAddClickListener: OnAddClickListener? = null
    var onNumChangeListener: OnNumChangeListener? = null
    var textWeights = ArrayList<Float>()
    var stockTaking: StockTaking? = null
    var originalData = ArrayList<StockTaking.StockProductInfo>()

    init {
        for(d in mData){
            originalData.add(d.copy())
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = super.onCreateViewHolder(parent, viewType)
        if (viewType == R.layout.item_editstock_detail) {
            val container = holder.itemView.findViewById<LinearLayout>(R.id.llContainer)
            container.removeAllViews()
            for (i in textWeights.indices) {
                val tv = TextView(mContext)
                tv.id = i
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
                tv.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, textWeights[i])
                tv.layoutParams = params
                if (stockTaking != null && stockTaking!!.inventoryStatus == PosConst.STOCK_TAKING_YES) { //详情模式
                    container.addView(tv)
                } else {
                    if (i == 5) {
                        val linearLayout = LinearLayout(mContext)
                        linearLayout.id = i
                        val p = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayManager.dip2px(36F)!!)
                        val editText = EditText(mContext)
                        editText.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
                        editText.backgroundResource = R.drawable.shape_border_gray_radius2
                        editText.hint = mContext.getString(R.string.pls_input)
                        editText.hintTextColor = mContext.resources.getColor(R.color.thirdTextColor)
                        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
                        editText.id = R.id.etNum
                        editText.inputType = InputType.TYPE_CLASS_NUMBER
                        editText.filters=arrayOf(InputFilter.LengthFilter(6))
                        val padding = DisplayManager.dip2px(6F)!!
                        editText.setPadding(padding, padding, padding, padding)
                        p.marginEnd = DisplayManager.dip2px(5f)!!
                        editText.layoutParams = p
                        linearLayout.addView(editText)
                        linearLayout.layoutParams = params
                        container.addView(linearLayout)
                    } else {
                        container.addView(tv)
                    }
                }
            }
        }
        return holder
    }

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: StockTaking.StockProductInfo, position: Int) {
        if (stockTaking != null && stockTaking!!.inventoryStatus == PosConst.STOCK_TAKING_YES) { //详情模式
            //    titleList = arrayListOf("序号", "商品条码", "品名", "单位", "规格", "实际数量", "系统库存", "盈亏数量","商品进价($unit)","盈亏金额","操作")
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.productBarCode)
            holder.setText(2, data.goodsName)
            holder.setText(3, data.unitName)
            holder.setText(4, data.specificationsName)
            holder.setText(5, data.actualStock.toString()) //输入的
            holder.setText(6, data.sysStock.toString())
            holder.setText(7, (data.actualStock - data.sysStock).toString())
            holder.setText(8, data.buyingPrice)
            holder.setText(9, ((data.actualStock - data.sysStock) * data.buyingPrice.toDouble()).ext_formatAmount())
        } else {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.productBarCode)
            holder.setText(2, data.goodsName)
            holder.setText(3, data.unitName)
            holder.setText(4, data.specificationsName)

            val view = holder.getView<View>(5)
            val editText = view.findViewById<EditText>(R.id.etNum)
            editText.addTextChangedListener(NumChangerWatcher(holder, position))

            editText.setText(data.actualStock.toString())
            holder.setText(6, data.sysStock.toString())
            holder.setText(7, (data.actualStock - data.sysStock).toString())
            holder.setText(8, data.buyingPrice)
            holder.setText(9, ((data.actualStock - data.sysStock) * data.buyingPrice.toDouble()).ext_formatAmount())

            val tv = holder.getView<TextView>(10)
            tv.text = mContext.getString(R.string.delete)
            tv.setTextColor(mContext.resources.getColor(R.color.blue_4384e3))
            tv.ext_setLeftImageResource(R.mipmap.batch_deletion)
            tv.compoundDrawablePadding = DisplayManager.dip2px(5f)!!
            tv.setOnClickListener(View.OnClickListener {
                //删除
                mData.removeAt(position)
                notifyDataSetChanged()
                updateTotal()
            })
        }

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }

    inner class NumChangerWatcher(holder: ViewHolder, position: Int) : TextWatcher {
        var holder: ViewHolder? = null
        var info: StockTaking.StockProductInfo? = null

        init {
            this.holder = holder
            this.info = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            var num = 0.toLong()
            if (p0.toString().ext_isPureNumber_orDecimal()) {
                num = p0.toString().toLong()
            }
            if(num!=info!!.actualStock){
                info!!.actualStock = num
                holder!!.setText(7, (info!!.actualStock - info!!.sysStock).toString())
                holder!!.setText(9, ((info!!.actualStock - info!!.sysStock) * info!!.buyingPrice.toDouble()).ext_formatAmount())
                updateTotal()
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    fun updateTotal() {
        var totalNum = 0.toLong()
        var sysTotalNum = 0
        var profitLossAmount = 0.toDouble()
        for (d in mData) {
            totalNum += d!!.actualStock
            sysTotalNum += d!!.sysStock
            profitLossAmount += (d!!.actualStock - d!!.sysStock) * d!!.buyingPrice.toDouble()
        }
        onNumChangeListener?.onNumChange(totalNum, sysTotalNum, profitLossAmount.ext_formatAmount())
    }

    fun updateData(addList: ArrayList<StockTaking.StockProductInfo>) {
        val repeatList = ArrayList<StockTaking.StockProductInfo>()
        for (s in addList) { //去重
            for (d in mData) {
                if (s.productBarCode == d.productBarCode) {
                    repeatList.add(s)
                    break
                }
            }
        }
        addList.removeAll(repeatList)
        val last = ArrayList<StockTaking.StockProductInfo>()
        last.addAll(mData)
        mData.clear()
        mData.addAll(addList)
        mData.addAll(last)
        notifyDataSetChanged()
        updateTotal()
    }

    /**
     * 检查库存输入是否为空或0
     */
    fun checkStockNumZero(): Boolean {
        var flag = false
        for (s in mData) {
            if (s.actualStock == 0.toLong()) {
                flag = true
                break
            }
        }
        return flag
    }

    /**
     * 检查是否改变
     */
    fun checkChanged(): Boolean {
        if(originalData.size!=mData.size){
            return true
        }
        for (s in mData) {
            for (o in originalData) {
                if(s.productId==o.productId){
                    if (s.actualStock != o.actualStock) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
