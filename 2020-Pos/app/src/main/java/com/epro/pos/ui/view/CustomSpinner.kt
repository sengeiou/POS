package com.epro.pos.ui.view

import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.*
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import kotlinx.android.synthetic.main.layout_custom_spinner.view.*
import kotlinx.android.synthetic.main.layout_custom_spinner_drop_list.view.*
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow
import kotlin.math.log

class CustomSpinner(context: Context, attributeSet: AttributeSet?) : LinearLayout(context, attributeSet), View.OnClickListener {

    override fun onClick(v: View?) {
        when (v) {
            tvContent -> {
                showDropPop()
                if (context is Activity) {
                    AppUtils.closeKeyboard(context as Activity)
                }
            }
        }
    }

    interface OnSipnnerItemClickListner {
        fun onSpinnerItemClick(item: Item, view: View?)
    }

    var onSpinnerItemClickListner: OnSipnnerItemClickListner? = null

    var isDefaultFirst = true
    var csHint = ""
    var hint = ""
        set(value) {
            field = value
            tvContent.hint = value
        }
    private var items = ArrayList<Item>()

    init {
        if (attributeSet != null) {
            val t = getContext().obtainStyledAttributes(attributeSet, R.styleable.CustomSpinner)
            csHint = t.getString(R.styleable.CustomSpinner_custom_sp_hint)
            t.recycle()
        }
        items = ArrayList()
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_spinner, this, true)
        tvContent.setOnClickListener(this)
        tvContent.hint = csHint
    }

    var listPopupWindow: BasePopupWindow? = null
    fun showDropPop() {
        if (listPopupWindow == null) {
            listPopupWindow = object : BasePopupWindow(context) {
                override fun onCreateContentView(): View {
                    val rvEntry = RecyclerView(context)
                    rvEntry.layoutManager = LinearLayoutManager(context)
                    rvEntry.setBackgroundResource(R.drawable.shape_border_gray_radius2)
                    rvEntry.adapter = CustomSpinnerAdapter(context, items)
                    (rvEntry.adapter as CustomSpinnerAdapter).onItemClickListener = object : CustomSpinnerAdapter.OnItemClickListener {
                        override fun onClick(item: Item, position: Int) {
                            items[position].judgeValue = true
                            tvContent.text = item.content
                            onSpinnerItemClickListner?.onSpinnerItemClick(item, this@CustomSpinner)
                            dismiss()
                        }
                    }
                    return rvEntry
                }

            }
            listPopupWindow?.popupGravity = Gravity.BOTTOM
            //listPopupWindow?.setOutSideDismiss(false)
            listPopupWindow?.width = this.measuredWidth
            if (items.size <= 6) {
                listPopupWindow?.height = DisplayManager.dip2px(44F * items.size)!!
            } else {
                listPopupWindow?.height = DisplayManager.dip2px(44F * 6)!!
            }
            listPopupWindow?.setBackground(ColorDrawable(resources.getColor(android.R.color.transparent)))
        }
        listPopupWindow?.showPopupWindow(tvContent)
    }

    class CustomSpinnerAdapter(mContext: Context, itemList: ArrayList<Item>, layoutId: Int = R.layout.item_custom_spinner) :
            CommonAdapter<Item>(mContext, itemList, layoutId) {

        interface OnItemClickListener {
            fun onClick(item: Item, position: Int)
        }

        var onItemClickListener: OnItemClickListener? = null

        override fun bindData(holder: ViewHolder, data: Item, position: Int) {
            holder.setText(R.id.tvContent, "" + data.content)
            val tvContent = holder.getView<TextView>(R.id.tvContent)
            tvContent.textColor = mContext.resources.getColor(if (data.judge()) R.color.mainColor else R.color.mainTextColor)
            holder.itemView.backgroundColor = mContext.resources.getColor(if (data.judge()) R.color.mainColor_6 else R.color.transparent)
            holder.setOnItemClickListener(object : View.OnClickListener {
                override fun onClick(p0: View?) {
                    if (onItemClickListener != null) {
                        if (!data.judge()) {
                            for (i in mData) {
                                ext_setAllFalse(mData)
                            }
                            data.judgeValue = true
                            notifyDataSetChanged()
                        }
                        onItemClickListener!!.onClick(data, position)

                    }
                }
            })
        }
    }

    fun setData(data: ArrayList<Item>) {
        this.items.clear()
        this.items.addAll(data)
        if (isDefaultFirst) {
            if (data.size > 0) {
                data[0].judgeValue=true
                tvContent.text = data[0].content
            }
        }
    }

    fun setStringListData(data: ArrayList<String>) {
        val items = ArrayList<Item>()
        for (i in data.indices) {
            val item = Item(i, data[i])
            items.add(item)
        }
        setData(items)
    }


    fun reset() {
        if (items.size > 0) {
            ext_setAllFalse(items)
            if (isDefaultFirst) {
                items[0].judgeValue = true
                tvContent.text = items[0].content
            } else {
                tvContent.text = ""
            }
        }
    }

    fun check(position: Int) {
        ext_setAllFalse(items)
        items[position].judgeValue = true
        tvContent.text = items[position].content
    }

    fun getCheckPosition(): Int {
        if (items.isEmpty()) {
            return -1
        }
        val position = ext_FirstTrue(items)
        return position
    }


    fun getCheckId(): String? {
        if (items.isEmpty()) {
            return null
        }
        val position = ext_FirstTrue(items)
        if (position < 0) {
            return null
        }
        return items[position].content2
    }

    fun getCheckItem(): Item? {
        if (items.isEmpty()) {
            return null
        }
        if (getCheckPosition() < 0) {
            return null
        }
        return items[getCheckPosition()]
    }
}