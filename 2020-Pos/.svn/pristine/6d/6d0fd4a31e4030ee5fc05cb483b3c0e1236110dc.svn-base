package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.epro.pos.R
import com.epro.pos.mvp.contract.OrderDetailContract
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.model.bean.OrderRecord
import com.epro.pos.mvp.presenter.OrderDetailPresenter
import com.epro.pos.ui.adapter.DetailChildAdapter
import com.epro.pos.ui.adapter.OrderDetailParentAdapter
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.fragment_order_detail.*
import kotlinx.android.synthetic.main.fragment_pos_order_detail.*
import org.jetbrains.anko.textColor
import kotlin.math.absoluteValue


class OrderDetailFragment : BaseTitleBarCustomFragment<OrderDetailContract.View, OrderDetailPresenter>(), OrderDetailContract.View, View.OnClickListener {
    override fun onGetOrderRecordDetailSuccess(order: OrderRecord) {
        orderRecord = order
        tvTradeType.ext_setLeftImageResource(PosBusManager.getTradeTypeIcon(orderRecord!!.tradeType))
        tvTradeType.text = PosBusManager.getTradeTypeText(orderRecord!!.tradeType)
        tvOrderStatus.text = PosBusManager.getOrderStatusShowText(orderRecord!!.orderStatus)
        tvOrderStatus.textColor = resources.getColor(PosBusManager.getOrderStatusTextColor(orderRecord!!.orderStatus))
        rvGoodsItems.layoutManager = LinearLayoutManager(activity!!) as RecyclerView.LayoutManager?
        val parentAdapter = OrderDetailParentAdapter(activity!!, orderRecord!!.products!!)
        if (orderRecord!!.tradeType == PosConst.TRADE_TYPE_ONLINE) {
            llCustomer.visibility = View.VISIBLE
            parentAdapter.goodsTitles = arrayListOf(AppContext.getInstance().getString(R.string.product_name), AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.barcode),AppContext.getInstance().getString(R.string.unit_price),AppContext.getInstance(). getString(R.string.specification), AppContext.getInstance().getString(R.string.print_title_product_subtotal))
        } else {
            llCustomer.visibility = View.GONE
            parentAdapter.goodsTitles = arrayListOf(AppContext.getInstance().getString(R.string.product_name), AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.barcode),AppContext.getInstance().getString(R.string.unit_price), AppContext.getInstance().getString(R.string.specification), AppContext.getInstance().getString(R.string.discount), "", AppContext.getInstance().getString(R.string.print_title_product_subtotal))
        }
        parentAdapter.orderRecord=order
        rvGoodsItems.adapter = parentAdapter

        var customerInfo = arrayListOf(orderRecord!!.consignee.toString(), orderRecord!!.puserId, orderRecord!!.mobile.toString(), orderRecord!!.address.toString())
        if (order.deliveryType == PosConst.DISTRIBUTION_TYPE_SELFTAKE) {
            tvCustomerTitle.text = getString(R.string.self_report)
            titles1 = arrayListOf(getString(R.string.shop_adress),getString(R.string.customer_id), getString(R.string.contact_style), getString(R.string.pickup_time))
            customerInfo = arrayListOf(order.pickUpAdress.toString(), order.puserId, order.shopMobile.toString(), order.pickUpTime.toString())
        }
        var items = ArrayList<Item>()
        for (i in titles1.indices) {
            val item = Item(i, titles1[i]).valueContent2(customerInfo[i]).valueVisiblity(if (i == 1) View.INVISIBLE else View.VISIBLE)
            items.add(item)
        }
        rvCustomerItems.layoutManager = GridLayoutManager(activity!!, 2)
        rvCustomerItems.adapter = DetailChildAdapter(activity!!, items)
        var orderInfo = arrayListOf<String?>()
        if (PosConst.TRADE_TYPE_ONLINE == orderRecord!!.tradeType) {
            orderInfo = arrayListOf(orderRecord!!.channel.toString(), orderRecord!!.orderSn, orderRecord!!.puserId, PosBusManager.getPayModeText(orderRecord!!.payType)
                    , orderRecord!!.paySn.toString(), PosBusManager.getDistributionTypeText(orderRecord!!.deliveryType),
                    orderRecord!!.createTime, orderRecord!!.orderActualAmount, orderRecord!!.orderClosedTime, orderRecord!!.refundPaySn.toString(), PosBusManager.getRefundText(orderRecord!!.reimburseStatus!!))

            items = ArrayList()
            for (i in onlineTitles.indices) {
                val item = Item(i, onlineTitles[i]).valueContent2(orderInfo[i].toString())
                items.add(item)
            }
        } else {
            orderInfo = arrayListOf(orderRecord!!.channel.toString(), orderRecord!!.orderSn, orderRecord!!.paySn.toString(), PosBusManager.getPayModeText(orderRecord!!.payType)
                    , orderRecord!!.createTime, orderRecord!!.orderActualAmount, PosBusManager.getOrderTypeText(orderRecord!!.orderType), orderRecord!!.originalOrderSn)

            items = ArrayList()
            for (i in offlineTitles.indices) {
                val item = Item(i, offlineTitles[i]).valueContent2(orderInfo[i].toString())
                items.add(item)
            }
        }

        rvOrderItems.layoutManager = GridLayoutManager(activity!!, 2)
        rvOrderItems.adapter = DetailChildAdapter(activity!!, items)

        tvTotalNum.text = getString(R.string.total)+" ${orderRecord!!.productCount.absoluteValue} "+getString(R.string.pieces)
        tvAmount.text = orderRecord!!.orderActualAmount.ext_formatAmountWithUnit()

        tvRemark.text = orderRecord!!.remark

        if (orderRecord!!.orderStatus == PosConst.ONLINE_ORDER_STATUS_CANCEL) {
            llRefund.visibility = View.VISIBLE
            tvRefundStatus.text = PosBusManager.getRefundText(orderRecord!!.reimburseStatus!!)
            tvRefundStatus.setTextColor(resources.getColor(PosBusManager.getRefundTextColor(orderRecord!!.reimburseStatus!!)))
            tvRefundReason.text = orderRecord!!.orderCancelReason
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
        }
    }

    companion object {
        const val ORDERSN = "OrderSn"
        const val RECORD_TYPE = "record_type"
        fun newInstance(orderSn: String, type: String): OrderDetailFragment {
            val args = Bundle()
            args.putString(ORDERSN, orderSn)
            args.putString(RECORD_TYPE, type)
            val fragment = OrderDetailFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun getPresenter(): OrderDetailPresenter {
        return OrderDetailPresenter()
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_order_detail
    }

    override fun initData() {

    }

    val unit = AppBusManager.getAmountUnit()
    var titles1 = arrayListOf(AppContext.getInstance().getString(R.string.name), AppContext.getInstance().getString(R.string.customer_id),AppContext.getInstance().getString(R.string.contact_style), AppContext.getInstance().getString(R.string.shipping_address))
    val onlineTitles = arrayListOf(AppContext.getInstance().getString(R.string.trading_channels_no_point), AppContext.getInstance().getString(R.string.order_num), AppContext.getInstance().getString(R.string.customer_account_name), AppContext.getInstance().getString(R.string.pay_style), AppContext.getInstance().getString(R.string.transaction_serial_number), AppContext.getInstance().getString(R.string.delivery_method), AppContext.getInstance().getString(R.string.creation_time), AppContext.getInstance().getString(R.string.payment_amount)+"($unit)", AppContext.getInstance().getString(R.string.complete_time), AppContext.getInstance().getString(R.string.refund_serial_number), AppContext.getInstance().getString(R.string.refund_status))
    val offlineTitles = arrayListOf(AppContext.getInstance().getString(R.string.trading_channels_no_point), AppContext.getInstance().getString(R.string.order_num), AppContext.getInstance().getString(R.string.transaction_serial_number), AppContext.getInstance().getString(R.string.pay_style), AppContext.getInstance().getString(R.string.creation_time), AppContext.getInstance().getString(R.string.payment_amount)+"($unit)", AppContext.getInstance().getString(R.string.order_type), AppContext.getInstance().getString(R.string.refund_original_bill_number))
    var orderRecord: OrderRecord? = null
    override fun initView() {
        super.initView()
        setHaveBackView(true)
        getTitleView().text = getString(R.string.order_detail)
        val type = arguments!!.getString(RECORD_TYPE)
        when (type) {
            PosConst.GET_ORDER_RECORD_LIST -> {
                getLeftTitleView().text = getString(R.string.order_records)
            }
            PosConst.GET_ORDER_GOODS_RECORDS -> {
                getLeftTitleView().text = getString(R.string.goods_records)
            }
            PosConst.GET_FINANCIAL_BILL_LIST -> {
                getLeftTitleView().text = getString(R.string.financial_manager)
            }
        }
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
        val orderSn = arguments!!.getString(ORDERSN)
        mPresenter.getOrderRecordDetail(orderSn, PosConst.GET_ORDER_RECORD_DETAIL)
    }

}


