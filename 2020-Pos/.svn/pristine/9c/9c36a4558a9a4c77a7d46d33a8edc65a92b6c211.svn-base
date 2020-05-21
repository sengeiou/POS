package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View
import com.epro.pos.R
import com.epro.pos.mvp.contract.FinancialManagerContract
import com.epro.pos.mvp.model.bean.FinancialBill
import com.epro.pos.mvp.model.bean.GetFinancialScanBean
import com.epro.pos.mvp.presenter.FinancialManagerPresenter
import com.epro.pos.ui.adapter.FinancialBillListAdapter
import com.epro.pos.ui.view.CustomTimerPicker
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarListFragment
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.DateUtils
import com.mike.baselib.view.recyclerview.MultipleType
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.layout_financial_header.*
import org.jetbrains.anko.backgroundColor
import com.mike.baselib.utils.toast


/**
 * 财务管理页面
 */
class FinancialManagerFragment : BaseTitleBarListFragment<FinancialBill, FinancialManagerContract.View
        , FinancialManagerPresenter, FinancialBillListAdapter>(), FinancialManagerContract.View, CustomTimerPicker.OnTimerConfirmClickLisener {
    override fun onTimerConfirmClick() {
        startTime = listDataAdapter!!.customTimerPicker!!.getStartTimeText()
        endTime = listDataAdapter!!.customTimerPicker!!.getEndTimeText()
        page = 1
        getListData()
        listDataAdapter!!.startTime=startTime
        listDataAdapter!!.endTime=endTime
    }

    override fun onGetFinancialBillDetailSuccess(bill: FinancialBill) {

    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_financial_manager
    }

    override fun onGetFinancialScanSuccess(result: GetFinancialScanBean.Result) {
        val f = listDataAdapter!!.mData[0]
        f.financialScan = result
        listDataAdapter!!.notifyDataSetChanged()
        page = 1
        getListData()
    }
    var startTime = DateUtils.getCurrentDay() + " 00:00:00"
    var endTime = DateUtils.getCurrentDay() + " 23:59:59"
    override fun getListData() {
        listDataAdapter!!.customTimerPicker!!.onTimerConfirmClickLisener = this
        val orderSn = if (TextUtils.isEmpty(listDataAdapter!!.customSearchView!!.getSearchText())) null else listDataAdapter!!.customSearchView!!.getSearchText()
        mPresenter.getFinancialBillList(page, startTime, endTime, orderSn, PosConst.GET_FINANCIAL_BILL_LIST)
    }

    override fun getListAdapter(list: ArrayList<FinancialBill>): FinancialBillListAdapter {
        return FinancialBillListAdapter(activity!!, list, object : MultipleType<FinancialBill> {
            override fun getLayoutId(item: FinancialBill, position: Int): Int {
                if (position == 0) {
                    return R.layout.layout_financial_header
                } else {
                    return R.layout.item_financialbill_showlist
                }
            }
        })
    }

    companion object {
        const val TAG = "getFinancialBillList"
        fun newInstance(str: String): FinancialManagerFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = FinancialManagerFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): FinancialManagerFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): FinancialManagerPresenter {
        return FinancialManagerPresenter()
    }

    override fun initData() {

    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (errorCode == ErrorStatus.SUCCESS) {
            getMultipleStatusView().showContent()
            if (type == PosConst.GET_FINANCIAL_BILL_LIST) {
                if (page == 1) { //第一页请求时,刷新头部,这是时候才知道数据是不是为空,显示空UI 其他页数不刷新头部
                    listDataAdapter!!.notifyItemChanged(0)
                }
            }
        }
    }


    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.financial_manager)
        getRefreshView().setPrimaryColors(resources.getColor(R.color.bottomColor), resources.getColor(R.color.mainTextColor))
        getRvListView().backgroundColor = resources.getColor(R.color.bottomColor)
//        getRvListView().post(Runnable {
//            page = 1
//            getListData()
//        })
        listDataAdapter?.onItemClickListener = object : FinancialBillListAdapter.OnItemClickListener {
            override fun onClick(item: FinancialBill) {
//                if(item.billSn.isNotEmpty()){
//                    childFragmentManager.beginTransaction().replace(R.id.fragmentContent,
//                            BusShowListContainerFragment.newInstance(PosConst.GET_FINANCIAL_BILL_DETAIL, AppBusManager.toJson(item))).commitAllowingStateLoss()
//                }
                if(item.financialScan!=null){
                    return
                }
                if(TextUtils.isEmpty(item.orderSn)){
                    toast(getString(R.string.order_to_null))
                    return
                }
                childFragmentManager!!.beginTransaction().add(R.id.fragmentContent, OrderDetailFragment.newInstance(item.orderSn, PosConst.GET_FINANCIAL_BILL_LIST)).commitAllowingStateLoss()
            }
        }

        listDataAdapter?.onSearchClickListener=object :FinancialBillListAdapter.OnSearchClickListener{
            override fun onSearchClick() {
                startTime = listDataAdapter!!.customTimerPicker!!.getStartTimeText()
                endTime = listDataAdapter!!.customTimerPicker!!.getEndTimeText()
                page = 1
                getListData()
                listDataAdapter!!.startTime=startTime
                listDataAdapter!!.endTime=endTime
            }
        }

        getRvListView().addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val canScrollDown = recyclerView?.canScrollVertically(-1) //是否可以向下滑动 false 表示不行 true表示可以
                val canScrollUp = recyclerView?.canScrollVertically(1)   //是否可以向上滑动 false表示不行 true表示可以
                getRefreshView().setEnableRefresh(!canScrollDown!!)
                if (!canScrollDown && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    (getRefreshView().refreshHeader as ClassicsHeader).onStateChanged(getRefreshView(), RefreshState.PullDownToRefresh, RefreshState.PullDownToRefresh)
                }
                getRefreshView().setEnableLoadMore(!canScrollUp!!)
                if (!canScrollUp && newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    (getRefreshView().refreshFooter as ClassicsFooter).onStateChanged(getRefreshView(), RefreshState.PullUpToLoad, RefreshState.PullUpToLoad)
                }
            }
        })
    }

    override fun initListener() {
    }

    override fun lazyLoad() {
        startTime = DateUtils.getCurrentDay() + " 00:00:00"
        endTime = DateUtils.getCurrentDay() + " 23:59:59"
        listDataAdapter!!.mData.clear()
        listDataAdapter!!.mData.add(FinancialBill((-1).toString()))//预先添加头部item
        listDataAdapter!!.notifyDataSetChanged()
        mPresenter.getFinancialScan(PosConst.GET_FINANCIAL_SCAN)
        listDataAdapter!!.startTime=startTime
        listDataAdapter!!.endTime=endTime
    }

}


