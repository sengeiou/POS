package com.epro.pos.ui.adapter


import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.Product
import com.epro.pos.mvp.model.bean.ShowListItem
import com.mike.baselib.interface_.Judgable
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_setAllValue
import com.mike.baselib.utils.ext_setLeftImageResource
import com.mike.baselib.view.recyclerview.MultipleType
import kotlinx.android.synthetic.main.layout_filter_titlebar.*
import org.jetbrains.anko.padding

/**
 * desc: 分类的 Adapter
 */


class SelectProductAdapter(mContext: Context, list: ArrayList<Product>, layoutId: Int = R.layout.item_select_product) :
        CommonAdapter<Product>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: Product)
    }

    interface OnItemSelectListener {
        fun onItemSelect(item: Product)
    }

    override fun setData(list: ArrayList<Product>) {
        if (list.isNotEmpty()) {
            ext_setAllValue(list, isAllSelect)
        }
        super.setData(list)
    }

    var onItemClickListener: OnItemClickListener? = null
    var onItemSelectListener: OnItemSelectListener? = null
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
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
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
    override fun bindData(holder: ViewHolder, data: Product, position: Int) {
        val tv = holder.getView<TextView>(0)
        tv.compoundDrawablePadding = DisplayManager.dip2px(16F)!!
        tv.ext_setLeftImageResource(if (data.judge()) R.mipmap.icon_item_checked else R.mipmap.icon_item_uncheck)
        tv.setOnClickListener(View.OnClickListener {
            data.judgeValue = !data.judgeValue
            notifyItemChanged(position)
            onItemSelectListener?.onItemSelect(data)
        })
        holder.setText(0, (position + 1).toString())
        holder.setText(1, data.goodsName)
        holder.setText(2, data.productBarCode)
        holder.setText(3, data.specificationsValueNames)
        holder.setText(4, data.buyingPrice.toString())
        holder.setText(5, data.retailPrice.ext_formatAmount())
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }

            }
        })
    }
}
