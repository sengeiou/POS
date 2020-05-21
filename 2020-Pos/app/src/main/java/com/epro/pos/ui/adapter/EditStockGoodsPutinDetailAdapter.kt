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
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.StockGoodsPutin
import com.epro.pos.ui.view.CustomSpinner
import com.epro.pos.utils.PosBusManager
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
import com.mike.baselib.utils.toast

/**
 * desc: 分类的 Adapter
 */


class EditStockGoodsPutinDetailAdapter(mContext: Context, list: ArrayList<StockGoodsPutin.StockProductInfo>, layoutId: MultipleType<StockGoodsPutin.StockProductInfo>) :
        CommonAdapter<StockGoodsPutin.StockProductInfo>(mContext, list, layoutId, false) {

    interface OnItemClickListener {
        fun onClick(item: StockGoodsPutin.StockProductInfo)
    }

    interface OnAddClickListener {
        fun onAddClick()
    }

    interface OnNumChangeListener {
        fun onNumChange(totalNum: Long)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onAddClickListener: OnAddClickListener? = null
    var onNumChangeListener: OnNumChangeListener? = null
    var textWeights = ArrayList<Float>()
    var stockGoodsPutin: StockGoodsPutin? = null
    var originalData = ArrayList<StockGoodsPutin.StockProductInfo>()

    init {
        for (d in mData) {
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
                if (stockGoodsPutin != null && stockGoodsPutin!!.stockStatus == PosConst.STOCK_STATUS_IN) { //详情模式
                    container.addView(tv)
                } else {
                    if (i == 5) { //进货价
                        val linearLayout = LinearLayout(mContext)
                        linearLayout.id = i
                        val p = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayManager.dip2px(36F)!!)
                        val editText = EditText(mContext)
                        editText.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
                        editText.backgroundResource = R.drawable.shape_border_gray_radius2
                        editText.hint = mContext.getString(R.string.pls_input)
                        editText.hintTextColor = mContext.resources.getColor(R.color.thirdTextColor)
                        editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
                        editText.id = R.id.etInput
                        editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                        editText.filters = arrayOf(InputFilter.LengthFilter(10))
                        val padding = DisplayManager.dip2px(6F)!!
                        editText.setPadding(padding, padding, padding, padding)
                        p.marginEnd = DisplayManager.dip2px(5f)!!
                        editText.layoutParams = p
                        linearLayout.addView(editText)
                        linearLayout.layoutParams = params
                        container.addView(linearLayout)
                    } else if (i == 6) { //数量
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
                        editText.filters = arrayOf(InputFilter.LengthFilter(6))
                        val padding = DisplayManager.dip2px(6F)!!
                        editText.setPadding(padding, padding, padding, padding)
                        p.marginEnd = DisplayManager.dip2px(5f)!!
                        editText.layoutParams = p
                        linearLayout.addView(editText)
                        linearLayout.layoutParams = params
                        container.addView(linearLayout)
                    } else if (i == 7) {
                        val linearLayout = LinearLayout(mContext)
                        linearLayout.id = i
                        val p = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayManager.dip2px(36F)!!)
                        val customSpinner = CustomSpinner(mContext, null)
                        customSpinner.hint = mContext.getString(R.string.pls_choose_style)
                        customSpinner.id = R.id.customSpinner1
                        customSpinner.setStringListData(mContext.resources.getStringArray(R.array.stock_add_type).toList() as ArrayList<String>)
                        // val padding = DisplayManager.dip2px(6F)!!
                        p.marginEnd = DisplayManager.dip2px(5f)!!
                        customSpinner.layoutParams = p
                        linearLayout.addView(customSpinner)
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
    override fun bindData(holder: ViewHolder, data: StockGoodsPutin.StockProductInfo, position: Int) {
        if (stockGoodsPutin != null && stockGoodsPutin!!.stockStatus == PosConst.STOCK_STATUS_IN) { //详情模式
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.productBarCode)
            holder.setText(2, data.goodsName)
            holder.setText(3, data.goodsUnitName)
            holder.setText(4, data.specificationsValueNames)
            holder.setText(5, data.buyingPrice)
            holder.setText(6, data.stockNumber.toString())
            holder.setText(7, PosBusManager.getStockAddTypeText(data.stockType))
        } else {
            holder.setText(0, (position + 1).toString())
            holder.setText(1, data.productBarCode)
            holder.setText(2, data.goodsName)
            holder.setText(3, data.goodsUnitName)
            holder.setText(4, data.specificationsValueNames)

            val view5 = holder.getView<View>(5)
            val editText5 = view5.findViewById<EditText>(R.id.etInput)
            editText5.addTextChangedListener(PriceChangerWatcher(holder, position))
            editText5.setText(data.buyingPrice)

            var view = holder.getView<View>(6)
            val editText = view.findViewById<EditText>(R.id.etNum)
            editText.addTextChangedListener(NumChangerWatcher(holder, position))
            editText.setText(data.stockNumber.toString())

            view = holder.getView(7)
            val customSpinner = view.findViewById<CustomSpinner>(R.id.customSpinner1)
            val resStrs = mContext.resources.getStringArray(R.array.stock_add_type).toList() as ArrayList<String>
            if (data.goodsBinding == 1) {//普通商品
                resStrs.removeAt(0)
            } else { //捆绑商品
            }
            customSpinner.setStringListData(resStrs)
            if (resStrs.size > 1) {
                customSpinner.check(data.stockType)
            } else {
                customSpinner.check(0)
            }
            customSpinner.onSpinnerItemClickListner = object : CustomSpinner.OnSipnnerItemClickListner {
                override fun onSpinnerItemClick(item: Item, view: View?) {
                    if (resStrs.size > 1) {
                        data.stockType = item.icon
                    } else {
                        data.stockType = item.icon + 1
                    }
                }
            }

            val tv = holder.getView<TextView>(8)
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
        var info: StockGoodsPutin.StockProductInfo? = null

        init {
            this.holder = holder
            this.info = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            var num = 0.toLong()
            if (p0.toString().ext_isPureNumber_orDecimal()) {
                num = p0.toString().toLong()
            }
            if(num!=info!!.stockNumber){
                info!!.stockNumber=num
                updateTotal()
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    inner class PriceChangerWatcher(holder: ViewHolder, position: Int) : TextWatcher {
        var holder: ViewHolder? = null
        var info: StockGoodsPutin.StockProductInfo? = null

        init {
            this.holder = holder
            this.info = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            var price = 0.toDouble()
            if (p0.toString().ext_isPureNumber_orDecimal()) {
                price = p0.toString().toDouble()
            }
            info?.buyingPrice = price.ext_formatAmount()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }


    fun updateData(addList: ArrayList<StockGoodsPutin.StockProductInfo>) {
        val repeatList = ArrayList<StockGoodsPutin.StockProductInfo>()
        for (s in addList) { //去重
            for (d in mData) {
                if (s.productBarCode == d.productBarCode) {
                    repeatList.add(s)
                    break
                }
            }
        }
        addList.removeAll(repeatList)
        val last = ArrayList<StockGoodsPutin.StockProductInfo>()
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
        for (s in mData) {
            if (s.stockNumber == 0.toLong()) {
                mContext.toast(mContext.getString(R.string.input_stock_quantity_cannot_be_0))
                return true
            }
        }
        return false
    }

    /**
     * 检查输入进货价格
     */
    fun checkPrice(): Boolean {
        for (s in mData) {
            if (s.buyingPrice.toDouble() == 0.toDouble()) {
                mContext.toast(mContext.getString(R.string.money_not_0))
                return true
            }
            if (s.buyingPrice.toDouble() >= 10 * 1000 * 1000) {
                mContext.toast(mContext.getString(R.string.purchase_price))
                return true
            }
        }
        return false
    }

    fun updateTotal(): Long {
        var totalNum = 0.toLong()
        for (d in mData) {
            totalNum += d!!.stockNumber
        }
        onNumChangeListener?.onNumChange(totalNum)
        return totalNum
    }

    /**
     * 检查是否改变
     */
    fun checkChanged(): Boolean {
        if (originalData.size != mData.size) {
            return true
        }
        for (s in mData) {
            for (o in originalData) {
                if (s.productId == o.productId) {
                    if ((s.stockNumber != o.stockNumber) || (s.buyingPrice != o.buyingPrice)) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
