package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.ArrangeDistributionSuccessEvent
import com.epro.pos.listener.RefreshShowListUIEvent
import com.epro.pos.mvp.contract.SelectShopperContract
import com.epro.pos.mvp.model.bean.Shopper
import com.epro.pos.mvp.presenter.SelectShopperPresenter
import com.epro.pos.ui.adapter.SelectShopperAdapter
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.DisplayManager
import kotlinx.android.synthetic.main.popup_select_shopper.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast


class SelectShopperPopup : BaseDialogFragment<SelectShopperContract.View, SelectShopperPresenter>(), SelectShopperContract.View, View.OnClickListener {

    override fun onArrangeDistrubitionSuccess() {
        toast(getString(R.string.arrange_delivery_successfully))
        EventBus.getDefault().post(RefreshShowListUIEvent())
        EventBus.getDefault().post(ArrangeDistributionSuccessEvent())
        dismiss()
    }

    override fun getContentLayoutId(): Int {
        return R.layout.popup_select_shopper
    }

    override fun onGetShopperListSuccess(shoppers: ArrayList<Shopper>) {
        if (shoppers.isNotEmpty()) {
            shoppers[0].judgeValue = true
            shopperId=shoppers[0].id
            shopperName=shoppers[0].name
            selectShopperAdapter!!.setData(shoppers)
        } else {
            getMultipleStatusView().showEmpty()
        }
    }

    companion object {
        const val TAG = "getShopperList"
        fun newInstance(orderSns: ArrayList<String>): SelectShopperPopup {
            val args = Bundle()
            args.putStringArrayList(TAG, orderSns)
            val fragment = SelectShopperPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): SelectShopperPopup {
            return newInstance(ArrayList())
        }
    }

    override fun getPresenter(): SelectShopperPresenter {
        return SelectShopperPresenter()
    }


    override fun initView() {
    }

    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!! * 4 / 10
    }

    override fun getHeight(): Int {
        return DisplayManager.dip2px(350F)!!
    }

    override fun onClick(p0: View?) {
        when (p0) {
            tvBack -> {
                dismiss()
            }
            tvSure -> {
                if (shopperId.isEmpty()) {
                    toast(getString(R.string.pls_select_send_person))
                    return
                }
                val orderSns = arguments!!.getStringArrayList(TAG)
                mPresenter.arrangeDistribution(shopperId, shopperName, orderSns, PosConst.ARRANGE_DISTRIBUTION)
            }
        }

    }

    var selectShopperAdapter: SelectShopperAdapter? = null
    var shopperId = ""
    var shopperName = ""
    override fun initData() {

        rvList.layoutManager = LinearLayoutManager(activity!!)
        selectShopperAdapter = SelectShopperAdapter(activity!!, ArrayList())
        rvList.adapter = selectShopperAdapter
        selectShopperAdapter!!.onItemClickListener = object : SelectShopperAdapter.OnItemClickListener {
            override fun onClick(item: Shopper) {
                shopperId = item.id
                shopperName = item.name
            }
        }
        mPresenter.getShopperList(PosConst.GET_SHOPPER_LIST)

    }

    override fun initListener() {
        tvBack.setOnClickListener(this)
        tvSure.setOnClickListener(this)
    }

}


