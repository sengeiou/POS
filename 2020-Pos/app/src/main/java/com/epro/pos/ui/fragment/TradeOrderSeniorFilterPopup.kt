package com.epro.pos.ui.fragment;

import android.graphics.Color
import android.os.Bundle
import android.view.*
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.utils.PosConst
import com.mike.baselib.base.BasePresenter
import com.mike.baselib.base.IBaseView
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.fragment.BaseSimpleFragment
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ShadowDrawable
import kotlinx.android.synthetic.main.popup_trade_senior_filter.*


class TradeOrderSeniorFilterPopup : BaseSimpleFragment(), View.OnClickListener {
    override fun layoutContentId(): Int {
        return R.layout.popup_trade_senior_filter
    }

    override fun lazyLoad() {

    }

    override fun getPresenter(): BasePresenter<IBaseView> {
        return BasePresenter()
    }

    interface OnConfirmClickLisener {
        fun onConfirmClick()
    }

    var onConfirmClickLisener: OnConfirmClickLisener? = null

    override fun onClick(p0: View?) {
        when (p0) {
            llContainer -> {
                //dismiss()
                childFragmentManager.beginTransaction().hide(this).commitAllowingStateLoss()
            }
            btnReset -> {
                csTradeType.reset()
                csOrderType.reset()
                csPayType.reset()
                csOrderStatus.reset()
                csCashier.reset()
                csGoodsCategorys.reset()
                etCustomer.setText("")
            }
            btnSure -> {
                //dismiss()
                childFragmentManager.beginTransaction().hide(this).commitAllowingStateLoss()
                onConfirmClickLisener?.onConfirmClick()

            }
        }
    }

//    override fun getContentLayoutId(): Int {
//        return R.layout.popup_trade_senior_filter
//    }

    companion object {
        const val SHOW_TYPE = "show_type"
        fun newInstance(showType: String): TradeOrderSeniorFilterPopup {
            val args = Bundle()
            args.putString(SHOW_TYPE, showType)
            val fragment = TradeOrderSeniorFilterPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): TradeOrderSeniorFilterPopup {
            return newInstance("")
        }
    }


    override fun initData() {

    }

    var categoryItems = ArrayList<Item>()
    var cashierItems = ArrayList<Item>()

    override fun initView() {
        super.initView()
        csTradeType.setStringListData(resources.getStringArray(R.array.filter_trade_type).toList() as ArrayList<String>)
        csOrderType.setStringListData(resources.getStringArray(R.array.filter_order_type).toList() as ArrayList<String>)
        csPayType.setStringListData(resources.getStringArray(R.array.filter_pay_type).toList() as ArrayList<String>)
        csOrderStatus.setStringListData(resources.getStringArray(R.array.filter_order_status_show).toList() as ArrayList<String>)
       // csCashier.isDefaultFirst=false
        csCashier.setData(cashierItems)
        val showType = arguments?.getString(SHOW_TYPE)
        if (showType == PosConst.GET_ORDER_RECORD_LIST) {
            csGoodsCategorys.visibility = View.GONE
            csPayType.visibility=View.VISIBLE
        } else if (showType == PosConst.GET_ORDER_GOODS_RECORDS) {
            csGoodsCategorys.setData(categoryItems)
            csGoodsCategorys.visibility = View.VISIBLE
            csPayType.visibility=View.GONE
        }
        ShadowDrawable.setShadowDrawable(llBg, Color.parseColor("#ffffff"),
                0, Color.parseColor("#25000000"), DisplayManager.dip2px(4F)!!, 0, 10);
    }

    override fun initListener() {
        llContainer.setOnClickListener(this)
        btnReset.setOnClickListener(this)
        btnSure.setOnClickListener(this)
    }

//    override fun getWidth(): Int {
//        return DisplayManager.getScreenWidth()!!
//    }
//
//    override fun onStart() {
//        super.onStart()
//        val window = dialog.window
//        val params = window!!.attributes
//        params.dimAmount = 0f
//        params.gravity = Gravity.TOP
//        window.attributes = params
//    }
}


