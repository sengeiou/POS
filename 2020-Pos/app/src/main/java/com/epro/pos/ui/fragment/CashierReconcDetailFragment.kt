package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.epro.pos.R
import com.epro.pos.mvp.contract.CashierReconcDetailContract
import com.epro.pos.mvp.model.bean.CashierRecon
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.mvp.presenter.CashierReconcDetailPresenter
import com.epro.pos.ui.adapter.CashierReconcDetailChildAdapter
import com.epro.pos.ui.adapter.CashierReconcDetailTitleAdapter
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.ext_createJsonKey
import kotlinx.android.synthetic.main.fragment_cashier_reconc_detail.*


class CashierReconcDetailFragment : BaseTitleBarCustomFragment<CashierReconcDetailContract.View, CashierReconcDetailPresenter>(), CashierReconcDetailContract.View {

    companion object {
        const val TAG = "CashierReconcDetail"
        fun newInstance(cashierRecon: CashierRecon): CashierReconcDetailFragment {
            val args = Bundle()
            args.putString(ext_createJsonKey(CashierRecon::class.java), AppBusManager.toJson(cashierRecon))
            val fragment = CashierReconcDetailFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun getPresenter(): CashierReconcDetailPresenter {
        return CashierReconcDetailPresenter()
    }

    override fun onCashierReconcDetailSuccess() {
    }


    override fun layoutContentId(): Int {
        return R.layout.fragment_cashier_reconc_detail
    }

    var cashierRecon:CashierRecon?=null
    val titles1 = arrayListOf(AppContext.getInstance().getString(R.string.store_id_no_point), AppContext.getInstance().getString(R.string.cashier_no_point),AppContext.getInstance().getString(R.string.reconciliation_time_no_point), AppContext.getInstance().getString(R.string.first_sale_time), AppContext.getInstance().getString(R.string.last_sale_time),AppContext.getInstance().getString(R.string.number_of_sales))
    val titles2 = arrayListOf(AppContext.getInstance().getString(R.string.cash), AppContext.getInstance().getString(R.string.credit_card) ,AppContext.getInstance().getString(R.string.bank_card),AppContext.getInstance().getString(R.string.wx_sale_count),AppContext.getInstance().getString(R.string.zfb_sale_count),AppContext.getInstance().getString(R.string.facepay), AppContext.getInstance().getString(R.string.total_revenue_and_expenditure))
    val titles3 = arrayListOf(AppContext.getInstance().getString(R.string.original_cash_amount_no_point), AppContext.getInstance().getString(R.string.cash_income_from_current_shifts), AppContext.getInstance().getString(R.string.cash_balance))
    override fun initData() {
        cashierRecon=AppBusManager.fromJsonWithClassKey(arguments!!,CashierRecon::class.java)
        val saleInfo= arrayListOf<String?>("["+cashierRecon!!.shopId+"]"+cashierRecon!!.shopName,"["+cashierRecon!!.cashierId+"]"+cashierRecon!!.cashierName,cashierRecon!!.createTime,cashierRecon!!.firstSaleTime,cashierRecon!!.lastSaleTime,cashierRecon!!.totalCount)

        tvReconcId.text=cashierRecon!!.statementId

        val items=ArrayList<Item>()
        for(i in titles1.indices){
            val item= Item(i,titles1[i]).valueContent2(saleInfo[i].toString())
            items.add(item)
        }
        rvItems1.layoutManager= LinearLayoutManager(activity!!)
        rvItems1.adapter= CashierReconcDetailChildAdapter(activity!!, items)

        val payItems=ArrayList<CashierRecon.PayModeCashierRecon>()
        val payItem1=CashierRecon.PayModeCashierRecon(titles2[0],cashierRecon!!.cashSaleCount,cashierRecon!!.cashSaleAmount,cashierRecon!!.cashBackCount,cashierRecon!!.cashBackAmount)
        val payItem2=CashierRecon.PayModeCashierRecon(titles2[1],cashierRecon!!.creditCardSaleCount,cashierRecon!!.creditCardSaleAmount,cashierRecon!!.creditCardBackCount,cashierRecon!!.creditCardBackAmount)
        val payItem3=CashierRecon.PayModeCashierRecon(titles2[2],cashierRecon!!.bankCardSaleCount,cashierRecon!!.bankCardSaleAmount,cashierRecon!!.bankCardBackCount,cashierRecon!!.bankCardBackAmount)
        val payItem4=CashierRecon.PayModeCashierRecon(titles2[3],cashierRecon!!.wxSaleCount,cashierRecon!!.wxSaleAmount,cashierRecon!!.wxBackCount,cashierRecon!!.wxBackAmount)
        val payItem5=CashierRecon.PayModeCashierRecon(titles2[4],cashierRecon!!.zfbSaleCount,cashierRecon!!.zfbSaleAmount,cashierRecon!!.zfbBackCount,cashierRecon!!.zfbBackAmount)
        val payItem6=CashierRecon.PayModeCashierRecon(titles2[5],cashierRecon!!.faceSaleCount,cashierRecon!!.faceSaleAmount,cashierRecon!!.faceBackCount,cashierRecon!!.faceBackAmount)
        val payItem7=CashierRecon.PayModeCashierRecon(titles2[6],cashierRecon!!.saleTotalCount.toString(),cashierRecon!!.saleTotalAmount.toString(),cashierRecon!!.backTotalCount.toString(),cashierRecon!!.backTotalAmount.toString(),cashierRecon!!.totalAmount)
         payItems.add(payItem1)
         payItems.add(payItem2)
         payItems.add(payItem3)
         payItems.add(payItem4)
         payItems.add(payItem5)
         payItems.add(payItem6)
         payItems.add(payItem7)

        rvItems2.layoutManager= LinearLayoutManager(activity!!)
        rvItems2.adapter= CashierReconcDetailTitleAdapter(activity!!, payItems)

//        rvItems3.layoutManager= LinearLayoutManager(activity!!)
//        rvItems3.adapter= CashierReconcDetailChildAdapter(activity!!, PosBusManager.getTestData(titles3))
    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        getTitleView().text=getString(R.string.reconciliation_details)
        getLeftTitleView().text=getString(R.string.cashier_reconciliation)
    }

    override fun initListener() {
    }

    override fun lazyLoad() {

    }

}


