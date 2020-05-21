package com.epro.pos.ui.fragment

import android.os.Bundle
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.DataSelectedChangeEvent
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseSimpleFragment
import com.mike.baselib.listener.ShopInfoChange
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppUtils
import kotlinx.android.synthetic.main.fragment_show_list.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import kotlinx.android.synthetic.main.layout_title_bar.tvTitle
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class BusShowListContainerFragment : BaseSimpleFragment(), View.OnClickListener {
    override fun layoutContentId(): Int {
        return R.layout.fragment_show_list
    }

    override fun lazyLoad() {

    }

    override fun onClick(p0: View?) {
        when (p0) {
            rlLeft -> {
                parentFragment?.childFragmentManager!!.beginTransaction().remove(this).commitAllowingStateLoss()
                AppUtils.closeKeyboard(activity!!)
            }
        }
    }

    companion object {
        const val SHOW_TYPE = "ShowType"
        const val EXTRA = "extra"
        fun newInstance(type: String, extra: String = ""): BusShowListContainerFragment {
            val args = Bundle()
            args.putString(SHOW_TYPE, type)
            args.putString(EXTRA, extra)
            val fragment = BusShowListContainerFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): BusShowListContainerFragment {
            return newInstance("")
        }
    }

    override fun initData() {

    }


    var showType = ""
    override fun initView() {
        val unit = AppBusManager.getAmountUnit()
        showType = arguments?.getString(SHOW_TYPE).toString()
        super.initView()
        setHaveBackView(true)
        when (showType) {

            //TODO 商品档案
            PosConst.GET_GOODS_FILE_LIST -> {
                setHaveBackView(false)
                tvTitle.text = getString(R.string.goods_files)
            }

            //TODO 订单管理
            PosConst.GET_ORDER_RECORD_LIST -> {
                tvLeftTitle.text = getString(R.string.order_manager)
                tvTitle.text = getString(R.string.order_records)
            }

            PosConst.GET_ORDER_GOODS_RECORDS -> {
                tvLeftTitle.text = getString(R.string.trade_manager)
                tvTitle.text = getString(R.string.goods_records)

            }
            PosConst.GET_ORDER_SALES_RANKING_GOODS_LIST -> {
                tvLeftTitle.text = getString(R.string.trade_manager)
                tvTitle.text = getString(R.string.sale_ranking)
            }
            PosConst.GET_GROSS_PROFIT_GATHER_LIST -> {
                tvLeftTitle.text = getString(R.string.trade_manager)
                tvTitle.text = getString(R.string.gross_profit_gather)
            }
            PosConst.GET_CASHIER_RECONCILIATION_LIST -> {
                tvLeftTitle.text = getString(R.string.trade_manager)
                tvTitle.text = getString(R.string.cashier_reconciliation_record)
            }

            //TODO 库存管理
            PosConst.GET_STOCK_QUERY_LIST -> {
                tvLeftTitle.text = getString(R.string.stock_manager)
                tvTitle.text = getString(R.string.stock_query)
            }
            PosConst.GET_STOCK_GOODS_PUTIN_LIST -> {
                tvLeftTitle.text = getString(R.string.stock_manager)
                tvTitle.text = getString(R.string.goods_putin_storage)
            }
            PosConst.GET_STOCK_TAKING_LIST -> {
                tvLeftTitle.text = getString(R.string.stock_manager)
                tvTitle.text = getString(R.string.stock_taking)
            }
            PosConst.GET_STOCK_OUTIN_DETAIL -> {
                tvLeftTitle.text = getString(R.string.stock_manager)
                tvTitle.text = getString(R.string.out_in_detail)
            }

            //TODO 收银交易查询
            PosConst.GET_POS_TRADE_LIST -> {
                setHaveBackView(false)
                tvTitle.text = getString(R.string.trade_query)
            }

            //TODO 网店管理
            PosConst.GET_NETSHOP_UPPER_SHELF_GOODS_LIST -> {
                tvLeftTitle.text = getString(R.string.netshop_manager)
                tvTitle.text = getString(R.string.upper_shelf_goods_manage)
            }
            PosConst.GET_NETSHOP_ONLINE_ORDER_LIST -> {
                tvLeftTitle.text = getString(R.string.netshop_manager)
                tvTitle.text = getString(R.string.online_order_manage)
            }
            PosConst.GET_FINANCIAL_BILL_DETAIL -> {
                tvLeftTitle.text = getString(R.string.financial_manager)
                tvTitle.text = getString(R.string.bill_detail)
            }

        }
        childFragmentManager.beginTransaction().add(R.id.flList, BusShowListFragment.newInstance<Any>(showType, arguments!!.getString(EXTRA)!!)).commitAllowingStateLoss()
    }


    override fun initListener() {
        rlLeft.setOnClickListener(this)
    }

    fun setHaveBackView(isHave: Boolean) {
        if (isHave) {
            rlLeft.visibility = View.VISIBLE
            rlLogo.visibility = View.VISIBLE
            llLogo.visibility = View.GONE
        } else {
            rlLeft.visibility = View.GONE
            rlLogo.visibility = View.GONE
            llLogo.visibility = View.VISIBLE
            tvShopName.text = AppBusManager.getShopName()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDataSelectedChangeEvent(event: DataSelectedChangeEvent) {
        if(showType==event.showType){
            if(event.selectedNum>0){
                llRight.visibility=View.VISIBLE
                tvTotalSelectedNum.text=event.selectedNum.toString().trim()
            }else{
                llRight.visibility=View.GONE
            }
            tvTotalNum.text=event.totalNum.toString().trim()
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun shopInfoChange(event: ShopInfoChange){
        tvShopName.text = AppBusManager.getShopName()
    }
}


