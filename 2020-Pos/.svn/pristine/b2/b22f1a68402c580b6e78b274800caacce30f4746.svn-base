package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.CancelOnlineOrderEvent
import com.epro.pos.mvp.contract.SelectCancelReasonContract
import com.epro.pos.mvp.model.bean.RefundReson
import com.epro.pos.mvp.presenter.SelectCancelReasonPresenter
import com.epro.pos.ui.adapter.SelectRefundReasonAdapter
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.view.recyclerview.MultipleType
import kotlinx.android.synthetic.main.popup_select_cancel_reason.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast


class SelectCancelReasonPopup : BaseDialogFragment<SelectCancelReasonContract.View, SelectCancelReasonPresenter>(), SelectCancelReasonContract.View, View.OnClickListener {
    override fun getContentLayoutId(): Int {
        return R.layout.popup_select_cancel_reason
    }

    companion object {
        const val TAG = "SelectRefundReson"
        fun newInstance(str: String): SelectCancelReasonPopup {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = SelectCancelReasonPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): SelectCancelReasonPopup {
            return newInstance("")
        }
    }

    override fun getPresenter(): SelectCancelReasonPresenter {
        return SelectCancelReasonPresenter()
    }

    var selectReasonAdapter: SelectRefundReasonAdapter? = null
    var reason = ""
    val reasons = arrayListOf(AppContext.getInstance().getString(R.string.inventory_shortage), AppContext.getInstance().getString(R.string.unmanned_temporarily), AppContext.getInstance().getString(R.string.other))
    override fun initData() {
        val resonList = ArrayList<RefundReson>()
        for (i in reasons.indices) {
            val res = RefundReson(i.toString(), reasons[i])
            resonList.add(res)
        }
        rvList.layoutManager = LinearLayoutManager(activity!!)
        selectReasonAdapter = SelectRefundReasonAdapter(activity!!, resonList, object : MultipleType<RefundReson> {
            override fun getLayoutId(item: RefundReson, position: Int): Int {
                if (resonList.size - 1 == position) {
                    return R.layout.item_select_edit_pop
                } else {
                    return R.layout.item_select_pop
                }
            }

        })
        rvList.adapter = selectReasonAdapter
        selectReasonAdapter!!.onItemClickListener = object : SelectRefundReasonAdapter.OnItemClickListener {
            override fun onClick(item: RefundReson) {
                if (item.reason2.isNotEmpty()) {
                    reason = item.reason2
                } else {
                    reason = item.reason
                }
            }
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            tvBack -> {
                dismiss()
            }
            tvSure -> {
                if (reason.isEmpty()) {
                    toast(getString(R.string.please_select_a_reason_for_canceling_the_order))
                    return
                }
                val event=CancelOnlineOrderEvent()
                event.reason=reason
                EventBus.getDefault().post(event)
                dismiss()
            }
        }

    }

    override fun initView() {
    }

    override fun initListener() {
        tvBack.setOnClickListener(this)
        tvSure.setOnClickListener(this)
    }

    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!! * 4 / 10
    }

    override fun getHeight(): Int {
        return DisplayManager.dip2px(350F)!!
    }


}


