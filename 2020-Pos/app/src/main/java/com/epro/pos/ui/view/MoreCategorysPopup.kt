package com.epro.pos.ui.view

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.CartProduct
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.ui.adapter.CartProductAdapter
import com.epro.pos.ui.adapter.MoreCategorysPopWindowAdapter
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.ShadowDrawable
import com.mike.baselib.utils.ToastUtil
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout
import razerdp.basepopup.BasePopupWindow

class MoreCategorysPopup(context: Context, list: ArrayList<String>) : BasePopupWindow(context) {
    var categorys = ArrayList<String>()
    set(value) {
        field=value
        adapter = object : TagAdapter<String>(value) {
            override fun getView(parent: FlowLayout, position: Int, s: String): View {
                val tv = LayoutInflater.from(context).inflate(R.layout.item_more_category, parent, false) as TextView
                tv.text = s
                return tv
            }

            override fun onSelected(position: Int, view: View?) {
                (view as TextView).setTextColor(context.resources.getColor(R.color.mainColor))
                onItemClickListener?.onClick(categorys[position], position)
            }

            override fun unSelected(position: Int, view: View?) {
                (view as TextView).setTextColor(context.resources.getColor(R.color.secondaryTextColor))
            }
        }
        flTags?.adapter=adapter
    }
    var adapter:TagAdapter<String>?=null

    var flTags:TagFlowLayout?=null
    override fun onCreateContentView(): View {
        categorys=ArrayList()
        LogTools(this).d(categorys)
        val view = LayoutInflater.from(context).inflate(R.layout.layout_popup_more_categorys, null)
        flTags = view.findViewById<TagFlowLayout>(R.id.flTags)
        val llBg = view.findViewById<View>(R.id.llBg)

        val tvClose = view.findViewById<TextView>(R.id.tvClose)
        tvClose.setOnClickListener(View.OnClickListener {
            dismiss()
        })
        ShadowDrawable.setShadowDrawable(llBg, Color.parseColor("#ffffff"),
                0, Color.parseColor("#25000000"), 10, 0, 10);
        return view
    }

    interface OnItemClickListener {
        fun onClick(item: String, position: Int)
    }
    interface OnDismissListener {
        fun onDismiss()
    }

    override fun onDismiss() {
        super.onDismiss()
        onDismissListener?.onDismiss()
    }

    var onItemClickListener: OnItemClickListener? = null
    var onDismissListener: OnDismissListener? = null

}