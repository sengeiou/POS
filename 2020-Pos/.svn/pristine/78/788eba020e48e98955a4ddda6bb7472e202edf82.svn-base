package com.epro.pos.ui.adapter


import android.content.Context
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.*
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_loadImage
import com.mike.baselib.utils.ext_loadImageWithDomain

/**
 * desc: 分类的 Adapter
 */


class GoodsFileDetailProductAdapter(mContext: Context, list: ArrayList<GoodsFileDetail.Product>, layoutId: Int = R.layout.item_goodsfile_detail_product) :
        CommonAdapter<GoodsFileDetail.Product>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: GoodsFileDetail.Product)
    }

    var onItemClickListener: OnItemClickListener? = null
    var textWeights = ArrayList<Float>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val holder = super.onCreateViewHolder(parent, viewType)
        val container = holder.itemView.findViewById<LinearLayout>(R.id.llContainer)
        container.removeAllViews()
        for (i in textWeights.indices) {
            val tv = TextView(mContext)
            tv.id = i
            tv.gravity=Gravity.CENTER_VERTICAL
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.toFloat())
            val padding=DisplayManager.dip2px(5f)!!
            tv.setPadding(padding,padding,padding,padding)
            tv.setTextColor(mContext.resources.getColor(R.color.mainTextColor))
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, textWeights[i])
            tv.layoutParams = params
            if(i==0){
                val llContainer=LinearLayout(mContext)
                llContainer.layoutParams=params
                llContainer.setPadding(padding,padding,padding,padding)
                llContainer.id=i
                llContainer.gravity=Gravity.CENTER_VERTICAL
                val iv=ImageView(mContext)
                iv.id=R.id.ivImage
                val width=DisplayManager.dip2px(38f)!!
                val p=ViewGroup.LayoutParams(width,width)
                iv.layoutParams=p
                llContainer.addView(iv)
                container.addView(llContainer)
            }else{
                container.addView(tv)
            }
        }
        return holder
    }

    /**
     * 绑定数据
     */
 //   val titleList = arrayListOf("商品照片", "商品条码", "商品规格", "初始库存", "库存预警", "进货价($unit)", "零售价($unit)", "网店价($unit)")
    override fun bindData(holder: ViewHolder, data: GoodsFileDetail.Product, position: Int) {
        val ll=holder.getView<LinearLayout>(0)
        ll.findViewById<ImageView>(R.id.ivImage).ext_loadImageWithDomain(data.compressPicUrl)
        holder.setText(1,data.productBarCode)
        holder.setText(2,data.specificationsValueNames)
        holder.setText(3,data.productNumber.toString())
        holder.setText(4,data.stockEarlyNum.toString())
        holder.setText(5,data.buyingPrice)
        holder.setText(6,data.retailPrice)
        holder.setText(7,data.onlineSalesPrice)
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
