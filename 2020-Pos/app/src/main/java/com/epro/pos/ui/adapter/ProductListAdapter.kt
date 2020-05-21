package com.epro.pos.ui.adapter


import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Product
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * desc: 分类的 Adapter
 */

class ProductListAdapter(mContext: Context, list: ArrayList<Product>, layoutId: Int = R.layout.item_productlist) :
        CommonAdapter<Product>(mContext, list, layoutId) {

    interface OnItemClickListener {
        fun onClick(item: Product)
    }


    var onItemClickListener: OnItemClickListener? = null

    /**
     * 绑定数据
     */
    val columns=4
    var imageWidth=((DisplayManager.getScreenWidth()!!-DisplayManager.dip2px(124F)!!)*1.4F/2.4-DisplayManager.dip2px(50F)!!)/columns.toFloat()
    override fun bindData(holder: ViewHolder, data: Product, position: Int) {
        holder.setText(R.id.tvDesc, data.goodsName + " " + data.specificationsValueNames)
        holder.setText(R.id.tvCode, data.productBarCode)
        holder.setText(R.id.tvPrice, data.retailPrice.ext_formatAmount())
        holder.setText(R.id.tvUnit, AppBusManager.getAmountUnit())
        val ivImage = holder.getView<ImageView>(R.id.ivImage)
        ivImage.ext_loadConnersImageWithDomain(data.productPicUrl)
        var params = ivImage.layoutParams
        params.width = imageWidth.toInt()
        params.height = (params.width * (100F / 100)).toInt()
        ivImage.layoutParams = params
        val margin = DisplayManager.dip2px(10F)!!
        val ratio=(columns+1)/columns.toFloat()-1
        params = holder.itemView.layoutParams as ViewGroup.MarginLayoutParams
        for(i in 0 until columns){
            if((position-i) %columns==0) {
                params.leftMargin = (margin * (1-i*ratio)).toInt()
                params.rightMargin = (margin * ((i+1)*ratio)).toInt()
            }
        }
        holder.itemView.layoutParams = params
        //限时折扣活动
        val llActivity = holder.getView<LinearLayout>(R.id.llActivity)
        val tvOriginalPrice = holder.getView<TextView>(R.id.tvOriginalPrice)
        val a = PosBusManager.getProductActivity(data)
        if(a==null){
            llActivity.visibility=View.GONE
        }else{
            llActivity.visibility=View.VISIBLE
            holder.setText(R.id.tvPrice,a.discountPrice.ext_formatAmount())
            holder.setText(R.id.tvSymbol,AppBusManager.getAmountUnit())
            tvOriginalPrice.ext_setTextWithHorizontalLine(data.retailPrice.ext_formatAmount())
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
