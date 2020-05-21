package com.epro.pos.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import com.epro.pos.R
import com.epro.pos.mvp.model.bean.CommonItem
import com.epro.pos.ui.adapter.CommonItemAdapter
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseSimpleFragment
import com.mike.baselib.listener.ShopInfoChange
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.DisplayManager
import kotlinx.android.synthetic.main.fragment_common_item.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class TradeManageFragment : BaseSimpleFragment() {

    companion object {
        const val TAG = "TradeManageFragment"
        const val EXTRA="extra"
        fun newInstance(tag: String,extra:String=""): TradeManageFragment {
            val args = Bundle()
            args.putString(TAG, tag)
            args.putString(EXTRA, extra)
            val fragment = TradeManageFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): TradeManageFragment {
            return newInstance("")
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_common_item
    }

    override fun lazyLoad() {
    }

    val mTexts = arrayOf(R.string.order_records, R.string.goods_records, R.string.sale_ranking, R.string.gross_profit_gather,
            R.string.cashier_reconciliation_record)
    val bgRess= arrayOf(R.drawable.shape_bg_white_half_radius4_top,R.color.white,
            R.drawable.shape_bg_white_half_radius4_bottom,R.drawable.shape_bg_white_half_radius4_top,R.drawable.shape_bg_white_half_radius4_bottom)
    override fun initView() {
        super.initView()
        setHaveBackView(false)
        rlLeft.visibility= View.GONE
        tvTitle.text = getString(R.string.trade_manager)
        rvItems.layoutManager = LinearLayoutManager(activity!!)
        val list = ArrayList<CommonItem>()
        for (index in mTexts.indices) {
            list.add(CommonItem(index, getString(mTexts[index]))
                    .valueBgRes(bgRess[index])
                    .valueMarginBottom(if(index==2) DisplayManager.dip2px(12F)!! else 0))
        }
        val adapter = CommonItemAdapter(activity!!, list)
        rvItems.adapter = adapter
        adapter.onItemClickListener = object : CommonItemAdapter.OnItemClickListener {
            override fun onClick(item: CommonItem) {
                when (item.content) {
                    getString(R.string.order_records) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_ORDER_RECORD_LIST)).commitAllowingStateLoss()
                    }
                    getString(R.string.goods_records) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_ORDER_GOODS_RECORDS)).commitAllowingStateLoss()
                    }
                    getString(R.string.sale_ranking) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_ORDER_SALES_RANKING_GOODS_LIST)).commitAllowingStateLoss()
                    }
                    getString(R.string.gross_profit_gather) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_GROSS_PROFIT_GATHER_LIST)).commitAllowingStateLoss()
                    }
                    getString(R.string.cashier_reconciliation_record) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_CASHIER_RECONCILIATION_LIST)).commitAllowingStateLoss()
                    }
                }
            }
        }
        showChildFragment()
    }


    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            showChildFragment()
        }
    }

    private fun showChildFragment() {
        val childTag = arguments?.getString(TAG)
        val extra = arguments?.getString(EXTRA)
        logTools.d("child:" + childTag)
        if (!TextUtils.isEmpty(childTag)) {
            val fragment = childFragmentManager.findFragmentByTag(childTag)
            if (fragment == null) {
                childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                        BusShowListContainerFragment.newInstance(childTag!!,extra!!), childTag).commitAllowingStateLoss()
                arguments?.putString(TAG,"")
            }
        }
    }

    override fun initData() {

    }

    override fun initListener() {
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
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun shopInfoChange(event: ShopInfoChange){
        tvShopName.text = AppBusManager.getShopName()
    }
}