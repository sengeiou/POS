package com.epro.pos.ui.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.DateUtils
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.LogTools
import kotlinx.android.synthetic.main.layout_custom_time_picker.view.*


class CustomTimerPicker(context: Context, attributeSet: AttributeSet) : LinearLayout(context, attributeSet), View.OnClickListener {

    var spiltSign = "~" //拼接符号

    override fun onClick(v: View?) {
        when (v) {
            tvTimePicker -> {
                if (filterPopupWindow == null || !filterPopupWindow?.isShowing!!) {
                    showTimePickerPop()
                } else {
                    dismissPop()
                }
                if (context is Activity) {
                    AppUtils.closeKeyboard(context as Activity)
                }
            }
        }
    }

    init {
        initView()
    }

    private fun initView() {
        LayoutInflater.from(context).inflate(R.layout.layout_custom_time_picker, this, true)
        tvTimePicker.setOnClickListener(this)
        if (timeType == MONTH_TYPE) {
            tvTimePicker.text = DateUtils.getCurrentMonth()[0] + spiltSign + DateUtils.getCurrentMonth()[1]
        } else {
            tvTimePicker.text = DateUtils.getCurrentDay() + spiltSign + DateUtils.getCurrentDay()
        }
    }

    companion object {
        const val MONTH_TYPE = 1
        const val DAY_TYPE = 0
        const val TIME_FILTER_DEFAULT = 1
        const val TIME_FILTER_CENTER = 2
    }

    fun setDefaultTime(startTime: String, endTime: String) {
        tvTimePicker.text = startTime + spiltSign + endTime
        this.startTime = startTime
        this.endTime = endTime
    }

    var startTime: String = DateUtils.getCurrentDay()
    var endTime: String = DateUtils.getCurrentDay()

    var timeType = DAY_TYPE
        set(value) {
            field = value
            if (value == MONTH_TYPE) {
                tvTimePicker.text = DateUtils.getCurrentMonth()[0] + spiltSign + DateUtils.getCurrentMonth()[1]
            } else {
                tvTimePicker.text = DateUtils.getCurrentDay() + spiltSign + DateUtils.getCurrentDay()
            }
        }
    var showTimeLocation = TIME_FILTER_DEFAULT
    private val items = ArrayList<Item>()
    var filterPopupWindow: TimeFilterPopupWindow? = null
    private fun showTimePickerPop() {
        if (filterPopupWindow == null) {
            filterPopupWindow = TimeFilterPopupWindow(context)
        }
        filterPopupWindow!!.defaultStartTime = startTime
        filterPopupWindow!!.defaultEndTime = endTime
        filterPopupWindow!!.timeType = timeType
        if (startTime.isNotEmpty() && endTime.isNotEmpty()) {
            filterPopupWindow!!.setDefaultTime(startTime, endTime)
        }
        filterPopupWindow!!.isOutSideTouchable = true
        filterPopupWindow!!.setBackground(ColorDrawable(resources.getColor(android.R.color.transparent)))
        filterPopupWindow!!.onConfirmClickListener = object : TimeFilterPopupWindow.OnConfirmClickListener {
            override fun onComfirmClick(startTime: String, endTime: String) {
                LogTools("czh").d(startTime + spiltSign + endTime)
                this@CustomTimerPicker.startTime = startTime
                this@CustomTimerPicker.endTime = endTime
                this@CustomTimerPicker.tvTimePicker.text = startTime + spiltSign + endTime
                onTimerConfirmClickLisener?.onTimerConfirmClick()

            }

        }
        if (showTimeLocation == TIME_FILTER_CENTER) {
            filterPopupWindow!!.popupGravity = Gravity.CENTER
            filterPopupWindow!!.showPopupWindow()
        } else {
            val location = IntArray(2)
            tvTimePicker.getLocationOnScreen(location)
            filterPopupWindow!!.offsetX = location[0] - DisplayManager.dip2px(10F)!!
            val y = location[1]
            if (y > DisplayManager.getScreenHeight()!! / 2) {
                filterPopupWindow!!.popupGravity = Gravity.TOP
            } else {
                filterPopupWindow!!.popupGravity = Gravity.BOTTOM
            }
            filterPopupWindow!!.showPopupWindow(tvTimePicker)
        }
    }

    fun dismissPop() {
        filterPopupWindow?.dismiss()
        filterPopupWindow = null
    }

    fun setData(data: ArrayList<Item>) {
        this.items.clear()
        this.items.addAll(data)
        if (data.size > 0) {
            tvTimePicker.text = data[0].content
        }
    }

    fun setStringListData(data: ArrayList<String>) {
        val items = ArrayList<Item>()
        for (i in data.indices) {
            val item = Item(i, data[i], i == 0)
            items.add(item)
        }
        setData(items)
    }

    fun getStartTimeText(): String {
        return tvTimePicker.text.toString().split(spiltSign)[0] + " 00:00:00"
    }

    fun getEndTimeText(): String {
        return tvTimePicker.text.toString().split(spiltSign)[1] + " 23:59:59"
    }

    interface OnTimerConfirmClickLisener {
        fun onTimerConfirmClick()
    }

    var onTimerConfirmClickLisener: OnTimerConfirmClickLisener? = null


}