package com.epro.pos.ui.adapter


import android.app.Dialog
import android.content.Context
import android.graphics.Paint
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.GetPosOrderDetailBean
import com.epro.pos.mvp.model.bean.PosTradeOrder
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.utils.*
import com.mike.baselib.view.CommonDialog
import com.mike.baselib.view.recyclerview.MultipleType
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.backgroundResource
import org.jetbrains.anko.hintTextColor
import kotlin.math.absoluteValue


/**
 * desc: 分类的 Adapter
 */


class PosOrderDetailGoodsAdapter(mContext: Context, list: ArrayList<GetPosOrderDetailBean.Product>, layoutId: MultipleType<GetPosOrderDetailBean.Product>) :
        CommonAdapter<GetPosOrderDetailBean.Product>(mContext, list, layoutId, false) {

    interface OnItemClickListener {
        fun onClick(item: GetPosOrderDetailBean.Product)
    }

    var onItemClickListener: OnItemClickListener? = null
    var textWeights = ArrayList<Float>()
    var order: PosTradeOrder? = null
    var orderDetail:GetPosOrderDetailBean.Result?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = super.onCreateViewHolder(parent, viewType)
        if (viewType == R.layout.item_pos_orderdetail_goods) {
            val container = holder.itemView.findViewById<LinearLayout>(R.id.llContainer)
            container.removeAllViews()
            for (i in textWeights.indices) {
                val tv = TextView(mContext)
                tv.id = i
                tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
                tv.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
                val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, textWeights[i])
                tv.layoutParams = params
                 if (i == 6) {
                    val linearLayout = LinearLayout(mContext)
                    linearLayout.orientation = LinearLayout.VERTICAL
                    linearLayout.id = i
                    val tvAmount = TextView(mContext)
                    tvAmount.id = R.id.tvAmount
                    tvAmount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
                    tvAmount.setTextColor(mContext.resources.getColor(R.color.mainColor))
                    val tvOriginalAmount = TextView(mContext)
                    tvOriginalAmount.id = R.id.tvTotalAmount
                    tvOriginalAmount.setTextColor(mContext.resources.getColor(R.color.thirdTextColor))
                    tvOriginalAmount.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11.toFloat())
                    tvOriginalAmount.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
                    linearLayout.addView(tvAmount)
                    linearLayout.addView(tvOriginalAmount)
                    linearLayout.layoutParams = params
                    container.addView(linearLayout)
                } else if (i == 8) {
                    val linearLayout = LinearLayout(mContext)
                    linearLayout.id = i
                    val p = ViewGroup.LayoutParams(DisplayManager.dip2px(86F)!!, DisplayManager.dip2px(44F)!!)
                    val editText = EditText(mContext)
                    editText.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
                    editText.backgroundResource = R.drawable.shape_border_gray_radius2
                    editText.hint = mContext.getString(R.string.pls_input)
                    editText.hintTextColor = mContext.resources.getColor(R.color.thirdTextColor)
                    editText.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
                    editText.id = R.id.etNum
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                    val padding = DisplayManager.dip2px(6F)!!
                    editText.setPadding(padding, padding, padding, padding)
                    editText.layoutParams = p
                    linearLayout.addView(editText)
                    linearLayout.layoutParams = params
                    container.addView(linearLayout)
                } else {
                     if(i==2){
                         tv.paint.isFakeBoldText=true
                     }
                    container.addView(tv)
                }
            }
            return holder
        }
        return holder
    }

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: GetPosOrderDetailBean.Product, position: Int) {
        if (position == mData.size - 1) {
            holder.setText(R.id.tvUnit, AppBusManager.getAmountUnit() + " ")
            var totalAmount = 0.toDouble()
            var totalCount = 0
            for (i in mData.indices) {
                if (i != mData.size - 1) {
                    totalAmount += mData[i].totalPrice.toDouble()
                    totalCount += mData[i].productCount
                }
            }

            holder.setText(R.id.tvTotalAmount, totalAmount.ext_formatAmount())
            holder.setText(R.id.tvProductCount, mContext.getString(R.string.trading_channels)+ order!!.channel+","+ mContext.getString(R.string.total)+"${totalCount.absoluteValue}"+mContext.getString(R.string.items))
            holder.setText(R.id.tvAmount,totalAmount.ext_formatAmountWithUnit())
            holder.setText(R.id.tvPayType," ("+PosBusManager.getPayModeText((order!!.payType))+")")
        } else {
            holder.setText(0, (1 + position).toString())
            holder.setText(1, data.productSn)
            holder.setText(2, data.productDesc)
            holder.setText(3, data.productCount.toString())
            holder.setText(4, (data.discount * 100).toString())
            holder.setText(5, data.salePrice)
            var view = holder.getView<LinearLayout>(6)
            val tvAmount = view.findViewById<TextView>(R.id.tvAmount)
            val tvOriginalAmount = view.findViewById<TextView>(R.id.tvTotalAmount)
            tvAmount.text = data.totalPrice
            if(data.discount==1f){
                tvOriginalAmount.visibility=View.GONE
            }
            tvOriginalAmount.text = (data.salePrice.toDouble()*data.productCount).ext_formatAmount()

            if(order!!.orderType==PosConst.ORDER_TYPE_SALE){
                holder.setText(7, data.availableRefundCount.toString())
                view = holder.getView(8)
                val editText = view.findViewById<EditText>(R.id.etNum)
                editText.addTextChangedListener(NumChangerWatcher(holder, position))
            }
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
        var product: GetPosOrderDetailBean.Product? = null

        init {
            this.holder = holder
            this.product = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            var refundNum = 0
            if (p0.toString().ext_isPureNumber_orDecimal()) {
                refundNum = p0.toString().toInt()
            }
            product?.preRefundCount = refundNum
            if (refundNum > product!!.availableRefundCount) {//输入数量大于可退数量
                showAlert(holder!!)
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    //提示弹窗
    private fun showAlert(holder: ViewHolder) {
        CommonDialog.Builder(mContext)
                .setContent(mContext.getString(R.string.the_number_of_refundable))
                .setCancelIsVisibility(false)
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        holder.setText(R.id.etNum, "")
                    }
                })
                .create()
                .show()
    }
}
