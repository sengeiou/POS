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
import kotlinx.android.synthetic.main.fragment_common_item.*
import kotlinx.android.synthetic.main.layout_title_bar.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class StockManageFragment: BaseSimpleFragment(){

    companion object {
        const val TAG = "StockManageFragment"
        const val EXTRA="extra"
        fun newInstance(str: String,extra:String=""): StockManageFragment {
            val args = Bundle()
            args.putString(TAG, str)
            args.putString(EXTRA, extra)
            val fragment = StockManageFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): StockManageFragment {
            return newInstance("")
        }
    }
    override fun layoutContentId(): Int {
        return R.layout.fragment_common_item
    }

    override fun lazyLoad() {
    }
    val mTexts = arrayOf(R.string.stock_query, R.string.goods_putin_storage, R.string.stock_taking, R.string.out_in_detail)
    val bgRess= arrayOf(R.drawable.shape_bg_white_half_radius4_top,R.color.white,R.color.white,R.drawable.shape_bg_white_half_radius4_bottom)

    override fun initView() {
        super.initView()
        setHaveBackView(false)
        rlLeft.visibility= View.GONE
        tvTitle.text=getString(R.string.stock_manager)
        rvItems.layoutManager = LinearLayoutManager(activity!!)
        val list = ArrayList<CommonItem>()
        for (index in mTexts.indices) {
            list.add(CommonItem(index, getString(mTexts[index])).valueBgRes(bgRess[index]))
        }
        val adapter = CommonItemAdapter(activity!!, list)
        rvItems.adapter = adapter
        adapter.onItemClickListener = object : CommonItemAdapter.OnItemClickListener {
            override fun onClick(item: CommonItem) {
                when (item.content) {
                    getString(R.string.stock_query) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_STOCK_QUERY_LIST)).commitAllowingStateLoss()
                    }
                    getString(R.string.goods_putin_storage) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_STOCK_GOODS_PUTIN_LIST)).commitAllowingStateLoss()
                    }
                    getString(R.string.stock_taking) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_STOCK_TAKING_LIST)).commitAllowingStateLoss()
                    }
                    getString(R.string.out_in_detail) -> {
                        childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                                BusShowListContainerFragment.newInstance(PosConst.GET_STOCK_OUTIN_DETAIL)).commitAllowingStateLoss()
                    }
                }
            }
        }
        showChildFragment()
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

    private fun showChildFragment() {
        val childTag = arguments?.getString(TAG)
        val extra = arguments?.getString(EXTRA)
        logTools.t("YB").d("child:" + childTag)
        if (!TextUtils.isEmpty(childTag)) {
            val fragment = childFragmentManager.findFragmentByTag(childTag)
            if (fragment == null) {
                childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
                        BusShowListContainerFragment.newInstance(childTag!!,extra!!), childTag).commitAllowingStateLoss()
                arguments?.putString(TAG,"")
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            showChildFragment()
        }
    }

}