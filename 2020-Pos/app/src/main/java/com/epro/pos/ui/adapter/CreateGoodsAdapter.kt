package com.epro.pos.ui.adapter


import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.InputDevice
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.CreateGoodsItem
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.ext_isPureNumber_orDecimal
import com.mike.baselib.view.recyclerview.ViewHolder
import com.mike.baselib.view.recyclerview.adapter.CommonAdapter
import org.jetbrains.anko.find
import com.mike.baselib.utils.toast

/**
 * desc: 分类的 Adapter
 */

class CreateGoodsAdapter(mContext: Context, itemList: ArrayList<CreateGoodsItem>, layoutId: Int = R.layout.item_create_goods) :
        CommonAdapter<CreateGoodsItem>(mContext, itemList, layoutId, false) {
    // 点击弹窗取消按钮回调
    interface OnItemClickListener {
        fun onClick(item: CreateGoodsItem)
    }


    var onItemClickListener: OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return super.onCreateViewHolder(parent, viewType)
    }

    /**
     * 绑定数据
     */
    override fun bindData(holder: ViewHolder, data: CreateGoodsItem, position: Int) {
        if (mData.size - 1 == position) {
            holder.setText(R.id.tvFlag, "")
            holder.setViewInvisible(R.id.vLine)
        } else {
            holder.setText(R.id.tvFlag, "*")
        }
        val etInput = holder.getView<EditText>(R.id.etInput)
        val tvSelect = holder.getView<TextView>(R.id.tvSelect)
        if (data.isInput) {
            etInput.visibility = View.VISIBLE
            tvSelect.visibility = View.GONE
            etInput.addTextChangedListener(InputTextWatcher(holder, position))
            etInput.setText(data.content2)
        } else {
            etInput.visibility = View.GONE
            tvSelect.visibility = View.VISIBLE
            tvSelect.text = data.content2
        }

        if (position == 4 || position == 5) {
            etInput.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
            etInput.filters = arrayOf(InputFilter.LengthFilter(10))
        } else if (position == mData.size - 1 || position == 0) {
            etInput.inputType = InputType.TYPE_CLASS_NUMBER
            if (position == 0) {
                etInput.filters = arrayOf(InputFilter.LengthFilter(13))
            }
            if (position == mData.size - 1) {
                etInput.filters = arrayOf(InputFilter.LengthFilter(6))
            }
        } else {
            etInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
            if (position == 1) {
                etInput.filters = arrayOf(InputFilter.LengthFilter(50))
            } else if (position == 3) {
                etInput.filters = arrayOf(InputFilter.LengthFilter(84))
            }
        }

        holder.setText(R.id.tvTitle, data.content)
        holder.setOnItemClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (onItemClickListener != null) {
                    onItemClickListener!!.onClick(data)
                }
            }
        })
    }


    inner class InputTextWatcher(holder: ViewHolder, position: Int) : TextWatcher {
        var holder: ViewHolder? = null
        var item: CreateGoodsItem? = null

        init {
            this.holder = holder
            this.item = mData[position]
        }

        override fun afterTextChanged(p0: Editable?) {
            if (p0.toString().isNotEmpty()) {
                item!!.valueContent2(p0.toString())
            }

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }
    }

    fun checkParams(): Boolean {
        for (i in mData.indices) {
            val c = mData[i]
            if (i < mData.size - 1) {
                if (c.content2.isEmpty()) {
                    mContext.toast(c.content + mContext.getString(R.string.not_to_null))
                    return true
                }
                if (i == 0) {
                    if (c.content2.length > 13 || !c.content2.ext_isPureNumber_orDecimal()) {
                        mContext.toast(mContext.getString(R.string.bar_not_number))
                        return true
                    }
                }
                if (i == 4) {
                    if (c.content2.toDouble() == 0.toDouble()) {
                        mContext.toast(mContext.getString(R.string.money_not_0))
                        return true
                    }
                    if (c.content2.toDouble() >= 10 * 1000 * 1000) {
                        mContext.toast(mContext.getString(R.string.retail_price_exceed))
                        return true
                    }
                }
                if (i == 5) {
                    if (c.content2.toDouble() == 0.toDouble()) {
                        mContext.toast(mContext.getString(R.string.money_not_0))
                        return true
                    }
                    if (c.content2.toDouble() >= 10 * 1000 * 1000) {
                        mContext.toast(mContext.getString(R.string.purchase_price))
                        return true
                    }
                }
            }
        }
        return false
    }

}
