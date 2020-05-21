package com.epro.pos.ui.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.OnDismissListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.epro.pos.R
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.DateUtils
import com.mike.baselib.utils.LogTools
import com.mike.baselib.utils.ToastUtil
import org.jetbrains.anko.appcompat.v7.Appcompat
import razerdp.basepopup.BasePopupWindow
import java.util.*
import kotlin.collections.ArrayList

class TimeFilterPopupWindow(context: Context) : BasePopupWindow(context), View.OnClickListener, RadioGroup.OnCheckedChangeListener, OnDismissListener {
    override fun onDismiss(o: Any?) {
        this@TimeFilterPopupWindow.setOutSideDismiss(true)
    }

    companion object {
        const val MONTH_TYPE = 1
        const val DAY_TYPE = 0
        private fun getTimeData(timeType: Int): ArrayList<Array<String>> {
            val timeList = ArrayList<Array<String>>()
            if (timeType == MONTH_TYPE) {
                timeList.add(DateUtils.getCurrentMonth())
                timeList.add(DateUtils.getLastMonth())
                timeList.add(DateUtils.getNearlyTwoMonth())
                timeList.add(DateUtils.getNearlyThreeMonth())
            } else {
                timeList.add(arrayOf(DateUtils.getCurrentDay(), DateUtils.getCurrentDay()))
                timeList.add(arrayOf(DateUtils.getNearlyThreeDay(), DateUtils.getYesterday()))
                timeList.add(arrayOf(DateUtils.getNearlySevenDay(), DateUtils.getYesterday()))
                timeList.add(arrayOf(DateUtils.getNearlyThirtyDay(), DateUtils.getYesterday()))
            }
            return timeList
        }
    }

    var timeListData = ArrayList<Array<String>>()

    var timeType = DAY_TYPE
        set(value) {
            field = value
            if (value == DAY_TYPE) {
                rbTime1?.text = AppContext.getInstance().getString(R.string.nowadays)
                rbTime2?.text = AppContext.getInstance().getString(R.string.nearly_days_3)
                rbTime3?.text = AppContext.getInstance().getString(R.string.nearly_days_7)
                rbTime4?.text = AppContext.getInstance().getString(R.string.nearly_month_1)
                timeListData.clear()
                timeListData.addAll(getTimeData(DAY_TYPE))
                setDefaultTime(defaultStartTime,defaultStartTime)
            } else {
                rbTime1?.text = AppContext.getInstance().getString(R.string.current_month)
                rbTime2?.text =  AppContext.getInstance().getString(R.string.last_month_win)
                rbTime3?.text = AppContext.getInstance().getString(R.string.nearly_two_months)
                rbTime4?.text = AppContext.getInstance().getString(R.string.nearly_three_months)
                timeListData.clear()
                timeListData.addAll(getTimeData(MONTH_TYPE))
                setDefaultTime(defaultStartTime,defaultStartTime)
            }
        }
    var defaultStartTime = DateUtils.getCurrentDay()
    var defaultEndTime = DateUtils.getCurrentDay()


    init {
        this.timeListData = getTimeData((MONTH_TYPE))
        setDefaultTime(defaultStartTime, defaultEndTime)
    }

    fun setDefaultTime(startTime: String, endTime: String) {
        this.defaultStartTime = startTime
        this.defaultEndTime = endTime
        var position = -1
        for (i in timeListData.indices) {
            val times = timeListData[i]
            if (times[0] == startTime && times[1] == endTime) {
                position = i
                break
            }
        }
        when (position) {
            0 -> rgTimes?.check(R.id.rbTime1)
            1 -> rgTimes?.check(R.id.rbTime2)
            2 -> rgTimes?.check(R.id.rbTime3)
            3 -> rgTimes?.check(R.id.rbTime4)
            else -> rgTimes?.clearCheck()
        }

        rbStartTime!!.text = startTime
        rbEndTime!!.text = endTime
    }


    fun setDefaultTime() {
        if (MONTH_TYPE == timeType) {
            setDefaultTime(DateUtils.getCurrentMonth()[0], DateUtils.getCurrentMonth()[1])
        } else {
            setDefaultTime(DateUtils.getCurrentDay(), DateUtils.getCurrentDay())
        }
        rgTimes?.check(R.id.rbTime1)
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btnReset -> {
                setDefaultTime()
                rgTimes?.check(R.id.rbTime1)
            }
            R.id.btnSure -> {
                val startTime = rbStartTime?.text.toString()
                val endTime = rbEndTime?.text.toString()
                if (!isDate(startTime)) {
                    ToastUtil.showToast(AppContext.getInstance().getString(R.string.start_time_not_selected))
                    return
                }
                if (!isDate(endTime)) {
                    ToastUtil.showToast(AppContext.getInstance().getString(R.string.end_time_not_selected))
                    return
                }
                if (isDate(startTime) && isDate(endTime)) {
                    val start = DateUtils.dateToStamp(startTime)
                    val end = DateUtils.dateToStamp(endTime)
                    if (start > end) {
                        ToastUtil.showToast(AppContext.getInstance().getString(R.string.start_time_cannot_be_greater_than_the_end_time))
                        return
                    }
                }
                onConfirmClickListener?.onComfirmClick(startTime, endTime)
                dismiss()
            }
            R.id.rbStartTime -> {
                isSartTime = true
                if (pvTime == null || !pvTime?.isShowing!!) {
                    showTimingPicker()
                }
                val startTime = rbStartTime?.text.toString()
                if (isDate(startTime)) {
                    pvTime?.setDate(DateUtils.dateToCalendar(startTime))
                } else {
                    pvTime?.setDate(Calendar.getInstance())
                }
            }
            R.id.rbEndTime -> {
                isSartTime = false
                if (pvTime == null || !pvTime?.isShowing!!) {
                    showTimingPicker()
                }
                val endTime = rbEndTime?.text.toString()
                if (isDate(endTime)) {
                    pvTime?.setDate(DateUtils.dateToCalendar(endTime))
                } else {
                    pvTime?.setDate(Calendar.getInstance())
                }
            }

            R.id.rbTime1 -> {
                rbStartTime?.text = timeListData[0][0]
                rbEndTime?.text = timeListData[0][1]
            }
            R.id.rbTime2 -> {
                rbStartTime?.text = timeListData[1][0]
                rbEndTime?.text = timeListData[1][1]
            }
            R.id.rbTime3 -> {
                rbStartTime?.text = timeListData[2][0]
                rbEndTime?.text = timeListData[2][1]
            }
            R.id.rbTime4 -> {
                rbStartTime?.text = timeListData[3][0]
                rbEndTime?.text = timeListData[3][1]
            }
        }
    }

    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
        when (p0) {
            rgStartEndTime -> {
//                when (p1) {
//                    R.id.rbStartTime -> {
//                        LogTools(this@TimeFilterPopupWindow).d("start")
//                        isSartTime = true
//                        if (pvTime == null || !pvTime?.isShowing!!) {
//                            showTimingPicker()
//                        }
//                        val startTime = rbStartTime?.text.toString()
//                        if (isDate(startTime)) {
//                            pvTime?.setDate(DateUtils.dateToCalendar(startTime))
//                        } else {
//                            pvTime?.setDate(Calendar.getInstance())
//                        }
//                    }
//                    R.id.rbEndTime -> {
//                        LogTools(this@TimeFilterPopupWindow).d("end")
//                        isSartTime = false
//                        if (pvTime == null || !pvTime?.isShowing!!) {
//                            showTimingPicker()
//                        }
//                        val endTime = rbEndTime?.text.toString()
//                        if (isDate(endTime)) {
//                            pvTime?.setDate(DateUtils.dateToCalendar(endTime))
//                        } else {
//                            pvTime?.setDate(Calendar.getInstance())
//                        }
//                    }
//                }
            }
            rgTimes -> {
                when (p1) {
//                    R.id.rbTime1 -> {
//                        rbStartTime?.text = timeListData[0][0]
//                        rbEndTime?.text = timeListData[0][1]
//                    }
//                    R.id.rbTime2 -> {
//                        rbStartTime?.text = timeListData[1][0]
//                        rbEndTime?.text = timeListData[1][1]
//                    }
//                    R.id.rbTime3 -> {
//                        rbStartTime?.text = timeListData[2][0]
//                        rbEndTime?.text = timeListData[2][1]
//                    }
//                    R.id.rbTime4 -> {
//                        rbStartTime?.text = timeListData[3][0]
//                        rbEndTime?.text = timeListData[3][1]
//                    }
//                    else -> {
//                    }
                }
            }
        }


    }


    var rbStartTime: RadioButton? = null
    var rbEndTime: RadioButton? = null
    var isSartTime = true
    var rbTime1: RadioButton? = null
    var rbTime2: RadioButton? = null
    var rbTime3: RadioButton? = null
    var rbTime4: RadioButton? = null
    var rgTimes: RadioGroup? = null
    var rgStartEndTime: RadioGroup? = null
    override fun onCreateContentView(): View {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_pop_time_filter, null)
        rgStartEndTime = view.findViewById(R.id.rgStartEndTime)
        rbStartTime = rgStartEndTime?.findViewById(R.id.rbStartTime)
        rbEndTime = rgStartEndTime?.findViewById(R.id.rbEndTime)
        val btnReset = view.findViewById<TextView>(R.id.btnReset)
        val btnSure = view.findViewById<TextView>(R.id.btnSure)
        rgTimes = view.findViewById(R.id.rgTimes)
        rbTime1 = rgTimes?.findViewById(R.id.rbTime1)
        rbTime2 = rgTimes?.findViewById(R.id.rbTime2)
        rbTime3 = rgTimes?.findViewById(R.id.rbTime3)
        rbTime4 = rgTimes?.findViewById(R.id.rbTime4)
        btnReset.setOnClickListener(this)
        btnSure.setOnClickListener(this)
//        rgTimes?.setOnCheckedChangeListener(this)
//        rgStartEndTime?.setOnCheckedChangeListener(this)
        rbStartTime?.setOnClickListener(this)
        rbEndTime?.setOnClickListener(this)
        rbTime1?.setOnClickListener(this)
        rbTime2?.setOnClickListener(this)
        rbTime3?.setOnClickListener(this)
        rbTime4?.setOnClickListener(this)
        return view
    }

    var pvTime: TimePickerView? = null
    private fun showTimingPicker() {
        this.setOutSideDismiss(false)
        //时间选择器
        if (pvTime == null) {
            pvTime = TimePickerBuilder(context, object : OnTimeSelectListener {
                override fun onTimeSelect(date: Date, v: View?) {
                    rgStartEndTime?.clearCheck()
                    var startTime = ""
                    var endTime = ""
                    if (isSartTime) {
                        startTime = DateUtils.formatDate(date)
                        endTime = rbEndTime?.text.toString()
                    } else {
                        endTime = DateUtils.formatDate(date)
                        startTime = rbStartTime?.text.toString()
                    }
                    var position = -1
                    for (i in timeListData.indices) {
                        val times = timeListData[i]
                        if (times[0] == startTime && times[1] == endTime) {
                            position = i
                            break
                        }
                    }
                    when (position) {
                        0 -> rgTimes?.check(R.id.rbTime1)
                        1 -> rgTimes?.check(R.id.rbTime2)
                        2 -> rgTimes?.check(R.id.rbTime3)
                        3 -> rgTimes?.check(R.id.rbTime4)
                        else -> rgTimes?.clearCheck()
                    }
                    if (isSartTime) {
                        rbStartTime?.text = DateUtils.formatDate(date)
                    } else {
                        rbEndTime?.text = DateUtils.formatDate(date)
                    }

                }
            }).setCancelColor(context.resources.getColor(R.color.mainTextColor))
                    .setSubmitColor(context.resources.getColor(R.color.mainColor))
                    .setSubmitText(AppContext.getInstance().getString(R.string.dialog_done))
                    .setTitleText(AppContext.getInstance().getString(R.string.date))
                    .setTitleSize(16)
                    .setTitleColor(context.resources.getColor(R.color.mainTextColor))
                    .setDividerColor(context.resources.getColor(R.color.gray_dddddd))
                    .setTitleBgColor(context.resources.getColor(R.color.white))
                    .setContentTextSize(14)
                    .setRangDate(DateUtils.getYearFirstDayCalendar(-24), Calendar.getInstance())
                    .build()
        }
        pvTime?.setOnDismissListener(this)
        pvTime?.show()

    }

    override fun onDismiss() {
        super.onDismiss()
        pvTime?.dismiss()
        pvTime = null
    }

    private fun isDate(text: String): Boolean {
        return text.contains("-")
    }

    interface OnConfirmClickListener {
        fun onComfirmClick(startTime: String, endTime: String)
    }

    var onConfirmClickListener: OnConfirmClickListener? = null

}