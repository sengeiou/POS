package com.epro.pos.ui.fragment;

import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.epro.pos.R
import com.epro.pos.listener.CartProductChangeEvent
import com.epro.pos.listener.PendOrderNumChangeEvent
import com.epro.pos.mvp.contract.PendOrderListContract
import com.epro.pos.mvp.model.bean.CartProduct
import com.epro.pos.mvp.model.bean.PendOrder
import com.epro.pos.mvp.presenter.PendOrderListPresenter
import com.epro.pos.ui.adapter.CartProductAdapter
import com.epro.pos.ui.adapter.PendOrderAdapter
import com.epro.pos.ui.adapter.PendOrderCartProuctAdapter
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_createJsonKey
import com.mike.baselib.view.CommonDialog
import kotlinx.android.synthetic.main.popup_pend_order_list.*
import org.greenrobot.eventbus.EventBus

/**
 * 挂单列表弹窗
 */
class PendOrderListPopup : BaseDialogFragment<PendOrderListContract.View, PendOrderListPresenter>(), PendOrderListContract.View, View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            tvBack -> {
                dismiss()
            }
            btnDelete -> {
               showDeleteDialog()
            }
            btnCheckout -> {
                if (pendOrder != null) {
                    PosBusManager.setCartProducts(pendOrder!!.cartProducts)
                    PosBusManager.deleteOnePendOrder(pendOrder!!)
                    EventBus.getDefault().post(CartProductChangeEvent())
                    dismiss()
                }
            }
        }
    }

    private fun showDeleteDialog() {
        CommonDialog.Builder(activity!!)
                .setContent(getString(R.string.confirm_deletion))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        if (pendOrder != null) {
                            PosBusManager.deleteOnePendOrder(pendOrder!!)
                            val pendOrders = PosBusManager.getPendOrders()
                            adapter?.setData(pendOrders)
                            if (pendOrders.size > 0) {
                                pendOrder = pendOrders[0]
                                pendOrder?.judgeValue=true
                                initCartProductList(pendOrders[0])
                            } else {
//                                val pendOrder = PendOrder("", "", ArrayList<CartProduct>())
//                                initCartProductList(pendOrder)
//                                this@PendOrderListPopup.pendOrder = null
                                dismiss()
                            }
                            EventBus.getDefault().post(PendOrderNumChangeEvent())
                        }
                    }
                })
                .create()
                .show()
    }

    override fun getContentLayoutId(): Int {
        return R.layout.popup_pend_order_list
    }


    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!! * 9 / 10
    }

    override fun getHeight(): Int {
        return DisplayManager.getScreenHeight()!! * 3 / 4
    }

    companion object {
        const val TAG = "PendOrderList"
        fun newInstance(pendOrder: PendOrder?): PendOrderListPopup {
            val args = Bundle()
            args.putString(ext_createJsonKey(PendOrder::class.java), AppBusManager.toJson(pendOrder))
            val fragment = PendOrderListPopup()
            fragment.setArguments(args)
            return fragment
        }
    }


    override fun getPresenter(): PendOrderListPresenter {
        return PendOrderListPresenter()
    }

    override fun onPendOrderListSuccess() {
    }


    var pendOrder: PendOrder? = null
    var adapter: PendOrderAdapter? = null
    override fun initData() {
        pendOrder = AppBusManager.fromJsonWithClassKey(arguments!!, PendOrder::class.java)
        rvPendOrderList.layoutManager = LinearLayoutManager(context)
        val pendOrders = PosBusManager.getPendOrders()
        if (pendOrders.size > 0) {
            pendOrder = pendOrders[0]
            pendOrder?.judgeValue=true
        }
        adapter = PendOrderAdapter(activity!!, pendOrders)
        rvPendOrderList.adapter = adapter
        adapter?.onItemClickListener = object : PendOrderAdapter.OnItemClickListener {
            override fun onClick(pendOrder: PendOrder) {
                this@PendOrderListPopup.pendOrder = pendOrder
                initCartProductList(pendOrder)
            }
        }
        if (pendOrder != null) {
            initCartProductList(pendOrder!!)
        }
    }

    private fun initCartProductList(pendOrder: PendOrder) {
        rvCartProductList.layoutManager = LinearLayoutManager(context)
        rvCartProductList.adapter = PendOrderCartProuctAdapter(context!!, pendOrder.cartProducts)
        (rvCartProductList.adapter as PendOrderCartProuctAdapter).onItemClickListener = object : PendOrderCartProuctAdapter.OnItemClickListener {
            override fun onClick(item: CartProduct) {

            }
        }
    }

    override fun initView() {
        val unit=AppBusManager.getAmountUnit()
        tvPriceTitle.text=getString(R.string.print_title_product_sale)+"($unit)"
        tvTotalTitle.text=getString(R.string.print_title_product_subtotal)+"($unit)"
    }

    override fun initListener() {
        tvBack.setOnClickListener(this)
        btnDelete.setOnClickListener(this)
        btnCheckout.setOnClickListener(this)
    }

    override fun onDismiss(dialog: DialogInterface?) {
        super.onDismiss(dialog)
        onPopupDismissListener?.onPopupDismiss(arguments)
    }

    interface OnPopupDismissListener {
        fun onPopupDismiss(bundle: Bundle?)
    }
    var onPopupDismissListener: OnPopupDismissListener? = null
}


