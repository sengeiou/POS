package com.epro.pos.ui.view

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Product
import com.epro.pos.ui.adapter.SearchProductListAdapter
import com.epro.pos.utils.PosBusManager
import razerdp.basepopup.BasePopupWindow

class SearchProductsResultPopup(context: Context) : BasePopupWindow(context) {
    var adapter: SearchProductListAdapter? = null
    override fun onCreateContentView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_popup_search_result, null)
        val rvProducts = view.findViewById<RecyclerView>(R.id.rvProducts)
        rvProducts.layoutManager = LinearLayoutManager(context)
        adapter = SearchProductListAdapter(context, ArrayList())
        rvProducts.adapter = adapter
        adapter?.onItemClickListener = object : SearchProductListAdapter.OnItemClickListener {
            override fun onClick(item: Product) {
                onItemClickListener?.onClick(item)
                dismiss()
            }
        }
        return view
    }

    interface OnItemClickListener {
        fun onClick(item: Product)
    }

    fun updateData(keyword:String) {
        val list = PosBusManager.getSearchProducts(keyword, PosBusManager.getShopProductCategorys())
        adapter?.setData(list)
    }

    var onItemClickListener: OnItemClickListener? = null

}