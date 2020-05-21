package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.ArrangeDistributionSuccessEvent
import com.epro.pos.listener.CancelOnlineOrderEvent
import com.epro.pos.listener.RefreshShowListUIEvent
import com.epro.pos.mvp.contract.OnlineOrderDetailContract
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.OnlineOrder
import com.epro.pos.mvp.presenter.OnlineOrderDetailPresenter
import com.epro.pos.ui.adapter.DetailChildAdapter
import com.epro.pos.ui.adapter.OnlineOrderDetailParentAdapter
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.fragment_online_order_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class OnlineOrderDetailFragment : BaseTitleBarCustomFragment<OnlineOrderDetailContract.View, OnlineOrderDetailPresenter>(), OnlineOrderDetailContract.View, View.OnClickListener {
    override fun onRefundAgainOnlineOrderSuccess() {
        toast(getString(R.string.refund_successful_re))
        EventBus.getDefault().post(RefreshShowListUIEvent())
        removeThis()
    }

    override fun onVerifyOnlineOrderSuccess() {
        toast(getString(R.string.verification_succeeded))
        EventBus.getDefault().post(RefreshShowListUIEvent())
        removeThis()
    }

    override fun onClick(p0: View?) {
        when (p0) {
            btnLeft -> {
                when (onlineOrder!!.orderStatus) {
                    PosConst.ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION -> {
                        SelectCancelReasonPopup.newInstance().show(childFragmentManager, "select_refund_reason")
                    }
                    PosConst.ONLINE_ORDER_STATUS_WAIT_SELFTAKE -> {
                        SelectCancelReasonPopup.newInstance().show(childFragmentManager, "select_refund_reason")
                    }
                    PosConst.ONLINE_ORDER_STATUS_DISTRIBUTION -> {
                        SelectCancelReasonPopup.newInstance().show(childFragmentManager, "select_refund_reason")
                    }
                }
            }

            btnRight -> {
                when (onlineOrder!!.orderStatus) {
                    PosConst.ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION -> {
                        val orderSns = ArrayList<String>()
                        orderSns.add(onlineOrder!!.orderSn)
                        SelectShopperPopup.newInstance(orderSns).show(childFragmentManager, "select_shopper")
                    }
                    PosConst.ONLINE_ORDER_STATUS_CANCEL -> {//失败 重新退款
                        mPresenter.refundAgainOnlineOrder(onlineOrder!!.orderSn,PosConst.REFUND_AGAIN_ONLINE_ORDER)
                    }
                }
            }
            tvVerify -> {
                if (edtCode.text.toString().isEmpty()) {
                    toast(getString(R.string.please_enter_pickup_code))
                    return
                }
                mPresenter.verifyOnlineOrder(onlineOrder!!.orderSn, edtCode.text.toString(), PosConst.VERIFY_ONLINE_ORDER)
            }
        }
    }

    override fun onCancelOnlineOrderSuccess() {
        EventBus.getDefault().post(RefreshShowListUIEvent())
        toast(getString(R.string.order_cancelled_successfully))
        removeThis()
    }

    companion object {
        const val TAG = "cancelOnlineOrder"
        fun newInstance(onlineOrder: OnlineOrder): OnlineOrderDetailFragment {
            val args = Bundle()
            args.putString(ext_createJsonKey(OnlineOrder::class.java), AppBusManager.toJson(onlineOrder))
            val fragment = OnlineOrderDetailFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun getPresenter(): OnlineOrderDetailPresenter {
        return OnlineOrderDetailPresenter()
    }


    override fun layoutContentId(): Int {
        return R.layout.fragment_online_order_detail
    }

    override fun initData() {

    }

    var onlineOrder: OnlineOrder? = null
    override fun initView() {
        super.initView()
        onlineOrder = AppBusManager.fromJsonWithClassKey(arguments!!, OnlineOrder::class.java)
        setHaveBackView(true)
        getTitleView().text = getString(R.string.order_detail)
        getLeftTitleView().text = getString(R.string.order_records)

        val titles2 = arrayListOf(AppContext.getInstance().getString(R.string.trading_channels_no_point),AppContext.getInstance().getString(R.string.order_num),AppContext.getInstance().getString(R.string.creation_time), AppContext.getInstance().getString(R.string.pay_style), AppContext.getInstance().getString(R.string.customer_account_name), AppContext.getInstance().getString(R.string.order_status), AppContext.getInstance().getString(R.string.refund_status))

        rvGoodsItems.layoutManager = LinearLayoutManager(activity!!)
        val parentAdapter = OnlineOrderDetailParentAdapter(activity!!, onlineOrder!!.products)
        rvGoodsItems.adapter = parentAdapter

        var titles1 = arrayListOf(AppContext.getInstance().getString(R.string.name), AppContext.getInstance().getString(R.string.customer_id),AppContext.getInstance().getString(R.string.contact_style), AppContext.getInstance().getString(R.string.shipping_address))
        var customerInfo = arrayListOf(onlineOrder!!.consignee, onlineOrder!!.puserId, onlineOrder!!.mobile, onlineOrder!!.address)
        if(onlineOrder!!.deliveryType==PosConst.DISTRIBUTION_TYPE_SELFTAKE){
            tvCustomerTitle.text = getString(R.string.self_report)
             titles1= arrayListOf(AppContext.getInstance().getString(R.string.shop_adress),AppContext.getInstance().getString(R.string.customer_id), AppContext.getInstance().getString(R.string.contact_style), AppContext.getInstance().getString(R.string.pickup_time))
             customerInfo = arrayListOf(onlineOrder!!.pickUpAdress, onlineOrder!!.puserId, onlineOrder!!.shopMobile, onlineOrder!!.pickUpTime)
        }
        var items = ArrayList<Item>()
        for (i in titles1.indices) {
            val item = Item(i, titles1[i]).valueContent2(customerInfo[i]).valueVisiblity(if(i==1) View.INVISIBLE else View.VISIBLE)
            items.add(item)
        }
        rvCustomerItems.layoutManager = GridLayoutManager(activity!!, 2)
        rvCustomerItems.adapter = DetailChildAdapter(activity!!, items)
        val orderInfo = arrayListOf(onlineOrder!!.channel.toString(),onlineOrder!!.orderSn, onlineOrder!!.createTime, PosBusManager.getPayModeText(onlineOrder!!.payType)
                , onlineOrder!!.puserId, PosBusManager.getOrderStatusShowText(onlineOrder!!.orderStatus),
                if(onlineOrder!!.reimburseStatus==0) "--" else PosBusManager.getRefundText(onlineOrder!!.reimburseStatus))
        items = ArrayList()
        for (i in titles2.indices) {
            val item = Item(i, titles2[i]).valueContent2(orderInfo[i])
            if (i == titles2.size - 2) {
                item.valueColor(PosBusManager.getRefundTextColor(onlineOrder!!.reimburseStatus))
            }
            if (i == titles2.size - 3) {
                item.valueColor(PosBusManager.getOrderStatusTextColor(onlineOrder!!.orderStatus))
            }
            items.add(item)
        }
        rvOrderItems.layoutManager = GridLayoutManager(activity!!, 2)
        rvOrderItems.adapter = DetailChildAdapter(activity!!, items)

        tvTotalNum.text = getString(R.string.total)+" ${onlineOrder!!.productCount} "+getString(R.string.pieces)
        tvAmount.text = onlineOrder!!.orderActualAmount.ext_formatAmountWithUnit()

        when (onlineOrder!!.orderStatus) {
            PosConst.ONLINE_ORDER_STATUS_WAIT_DISTRIBUTION -> {
                btnRight.visibility = View.VISIBLE
            }
            PosConst.ONLINE_ORDER_STATUS_WAIT_SELFTAKE -> {
                llVfcode.visibility = View.VISIBLE

            }
            PosConst.ONLINE_ORDER_STATUS_DISTRIBUTION -> {
                tvShopperName.visibility = View.VISIBLE
                llVfcode.visibility = View.VISIBLE
                tvShopperName.text = getString(R.string.scheduled_delivery) + onlineOrder!!.courierName + "(" + onlineOrder!!.courierMobile + ")"
            }
            PosConst.ONLINE_ORDER_STATUS_SUCCESS -> {
                btnLeft.visibility = View.GONE
                llBottom.visibility=View.GONE
            }
            PosConst.ONLINE_ORDER_STATUS_CANCEL -> {//失败
                btnLeft.visibility = View.GONE
                llBottom.visibility=View.GONE
                if (onlineOrder!!.reimburseStatus == PosConst.REFUND_ORDER_STATUS_FAILED) {
                    btnRight.visibility = View.VISIBLE
                    llBottom.visibility=View.VISIBLE
                    btnRight.text = getString(R.string.refund_re)
                }
            }
        }
        tvRemark.text=onlineOrder!!.remark

    }

    override fun initListener() {
        btnLeft.setOnClickListener(this)
        btnRight.setOnClickListener(this)
        tvVerify.setOnClickListener(this)
    }

    override fun lazyLoad() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCancelOnlineOrderEvent(event: CancelOnlineOrderEvent) {
        mPresenter.cancelOnlineOrder(onlineOrder!!.orderSn, event.reason, PosConst.CANCEL_ONLINE_ORDER)
    }

    /**
     * 安排配送成功
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onArrangeDistributionSuccessEvent(event: ArrangeDistributionSuccessEvent) {
        toast(getString(R.string.arrange_delivery_successfully))
        removeThis()
    }
}


