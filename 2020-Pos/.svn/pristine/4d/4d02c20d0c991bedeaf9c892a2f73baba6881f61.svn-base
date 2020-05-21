package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Product
import com.mike.baselib.utils.ext_formatAmount
import com.mike.baselib.utils.ext_formatAmountWithUnit

/**
 * desc: 分类的 Adapter
 */

class SearchProductListAdapter(mContext: Context, list: ArrayList<Product>, layoutId: Int = R.layout.item_search_result) :
        CommonAdapter<Product>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: Product)
    }


    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: Product, position: Int) {
        holder.setText(R.id.tvDesc,data.goodsName+" "+data.specificationsValueNames)
        holder.setText(R.id.tvCode,data.productBarCode)
        holder.setText(R.id.tvPrice,data.retailPrice.ext_formatAmount())
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }

            }
        })
    }
}
