package com.epro.pos.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.CartProduct
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_formatDiscount
import com.mike.baselib.utils.ext_isPureNumber_orDecimal
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import kotlin.math.absoluteValue

class PendOrderCartProuctAdapter(mContext: Context, itemList: ArrayList<CartProduct>, layoutId: Int = R.layout.item_pendorder_cartproduct) :
        CommonAdapter<CartProduct>(mContext, itemList, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: CartProduct)
    }

    interface OnItemLongClickListener {
        fun onLongClick(item: CartProduct)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onItemLongClickListener: OnItemLongClickListener? = null
    override fun bindData(holder: ViewHolder, data: CartProduct, position: Int) {
        holder.setText(R.id.tvGoodsName, data.product.goodsName)
        holder.setText(R.id.tvSpec, data.product.specificationsValueNames)
        holder.setText(R.id.tvBarcode, data.product.productBarCode)
        holder.setText(R.id.tvPrice, data.product.retailPrice.ext_formatAmount())
        val originalTotal = data.product.retailPrice.toDouble() * data.num
        holder.setText(R.id.tvOriginalTotal, originalTotal.ext_formatAmount())
        val tvOriginalTotal = holder.getView<TextView>(R.id.tvOriginalTotal)
        tvOriginalTotal.visibility = if (data.discount == 1F) View.GONE else View.VISIBLE
        tvOriginalTotal.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG //中划线
        val total = (data.product.retailPrice.toDouble()*data.discount.ext_formatDiscount()).ext_formatAmount().toDouble() * data.num
        //购买数量
        val tvNum = holder.getView<TextView>(R.id.tvNum)
        holder.setText(R.id.tvSubtotal, total.ext_formatAmount())
        tvNum.text = data.num.toString()
        val tvDiscount = holder.getView<TextView>(R.id.tvDiscount)

        //折扣
        tvDiscount.text = (data.discount * 100).toInt().toString()

        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })

        holder.setOnItemLongClickListener(object : View.OnLongClickListener {
            override fun onLongClick(v: View?): Boolean {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener!!.onLongClick(data)
                }
                return true
            }
        })
    }

}