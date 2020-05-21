package com.epro.pos.ui.adapter


import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.OrderProduct
import com.epro.pos.mvp.model.bean.OrderRecord
import com.epro.pos.utils.PosConst
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.ext_formatAmountWithUnit
import com.mike.baselib.utils.ext_loadConnersImageWithDomain
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter

/**
 * desc: 分类的 Adapter
 */

class OrderDetailParentAdapter(mContext: Context, itemList: ArrayList<OrderProduct>, layoutId: Int=R.layout.item_order_detail_parent) :
        CommonAdapter<OrderProduct>(mContext, itemList, layoutId,false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item:OrderProduct)
    }

    var onItemClickListener:OnItemClickListener?=null
    var goodsTitles= arrayListOf(AppContext.getInstance().getString(R.string.product_name), AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.barcode), AppContext.getInstance().getString(R.string.unit_price),AppContext.getInstance().getString(R.string.specification), AppContext.getInstance().getString(R.string.print_title_product_subtotal))
    var orderRecord: OrderRecord?=null

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: OrderProduct, position: Int) {
        val rvItems=holder.getView<RecyclerView>(R.id.rvItems)
        var goodsInfo= arrayListOf(data.goodsName,data.productCount.toString(),data.productSn,data.salePrice.toString().ext_formatAmountWithUnit(),data.goodsSpecifitionNameValue,data.totalPrice.ext_formatAmountWithUnit())
        if (orderRecord!!.tradeType == PosConst.TRADE_TYPE_ONLINE) {

        }else{
             goodsInfo= arrayListOf(data.goodsName,data.productCount.toString(),data.productSn,data.salePrice.toString().ext_formatAmountWithUnit()
                    ,data.goodsSpecifitionNameValue,(data.discount*100).toString()+"%","",data.totalPrice.ext_formatAmountWithUnit())
        }
        val items=ArrayList<Item>()
        for(i in goodsTitles.indices){
            val item= Item(i,goodsTitles[i]).valueContent2(goodsInfo[i])
            items.add(item)
        }
        rvItems.layoutManager=GridLayoutManager(mContext,2)
        rvItems.adapter=DetailChildAdapter(mContext,items)
        val ivImage=holder.getView<ImageView>(R.id.ivImage)
        ivImage.ext_loadConnersImageWithDomain(data.listPicUrl,2F)
        holder.setOnItemClickListener(object :View.OnClickListener{
            override fun onClick(p0: View?) {
                if(onItemClickListener!=null){
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }
}
