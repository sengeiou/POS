package com.epro.pos.ui.fragment;

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.listener.SelectedProductEvent
import com.epro.pos.mvp.contract.SelectGoodsContract
import com.epro.pos.mvp.model.bean.GoodsCategoryCount
import com.epro.pos.mvp.model.bean.Product
import com.epro.pos.mvp.presenter.SelectGoodsPresenter
import com.epro.pos.ui.adapter.CategoryCountAdapter
import com.epro.pos.ui.adapter.SelectProductAdapter
import com.epro.pos.ui.view.CustomSearchView
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.interface_.Judgable
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.*
import com.scwang.smartrefresh.layout.constant.RefreshState
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import kotlinx.android.synthetic.main.popup_select_goods.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.padding
import com.mike.baselib.utils.toast

/**
 * 后台商品选择弹窗
 */
class SelectGoodsPopup : BaseDialogFragment<SelectGoodsContract.View, SelectGoodsPresenter>(), SelectGoodsContract.View, View.OnClickListener, SelectProductAdapter.OnItemSelectListener, CustomSearchView.OnQueryClickListner {
    override fun onQueryClick(content: String) {
        page=1
        AppUtils.closeKeyboard(dialog)
        getListData()
    }

    override fun onItemSelect(item: Product) {
        val tvAllSelect = llTitle.findViewById<TextView>(R.id.tvAll)
        tvAllSelect.tag = ext_isAllTrue(listDataAdapter!!.mData)
        tvAllSelect.ext_setLeftImageResource(if (tvAllSelect.tag as Boolean) R.mipmap.icon_item_checked else R.mipmap.icon_item_uncheck)
        listDataAdapter!!.isAllSelect = tvAllSelect.tag as Boolean
    }

    override fun onClick(p0: View?) {
        when (p0) {
            llTitle.findViewById<TextView>(R.id.tvAll) -> {
                var isAllSelect = p0!!.tag as Boolean
                isAllSelect = !isAllSelect
                p0.tag = isAllSelect
                (p0 as TextView).ext_setLeftImageResource(if (isAllSelect) R.mipmap.icon_item_checked else R.mipmap.icon_item_uncheck)
                ext_setAllValue(listDataAdapter!!.mData, isAllSelect)
                listDataAdapter!!.isAllSelect = isAllSelect
                listDataAdapter!!.notifyDataSetChanged()
            }
            tvSure -> {
                val datas = getSelectedDatas(getString(R.string.please_select_a_product_first))
                if (datas.isEmpty()) {
                    return
                }
                val event = SelectedProductEvent()
                event.products.clear()
                event.products.addAll(datas)
                EventBus.getDefault().post(event)
                dismiss()
            }

            tvBack -> {
                dismiss()
            }
        }
    }

    /**
     * 获取被选择的数据
     */
    private fun getSelectedDatas(alert: String): ArrayList<Product> {
        val jList = ArrayList<Product>()
        if (ext_isAllFalse(listDataAdapter!!.mData)) {
            toast(alert)
            return jList
        }
        for (j in listDataAdapter!!.mData) {
            if (j.judge()) {
                jList.add(j)
            }
        }
        return jList
    }


    override fun onGetProductList(products: ArrayList<Product>) {
        if (page == 1) {
            this.dataList.clear()
            this.dataList.addAll(products)
            listDataAdapter?.setData(this.dataList)
            refreshLayout?.finishRefresh()
            refreshLayout.setNoMoreData(false)
        } else {
            if (products.size == 0) {
                ToastUtil.showToast(getString(R.string.not_more_data))
                refreshLayout?.finishLoadMoreWithNoMoreData()
                return
            }
            this.dataList.addAll(products)
            listDataAdapter?.setData(this.dataList)
            refreshLayout?.finishLoadMore()
        }
    }

    override fun onGetGoodsCategoryCountList(categoryCounts: ArrayList<GoodsCategoryCount>) {
        val cates = ArrayList<GoodsCategoryCount>()
        var total = 0
        for (c in categoryCounts) {
            total += c.productTypeCount
        }
        val c1 = GoodsCategoryCount(total, 0.toString(), getString(R.string.all_products), true)
        cates.add(c1)
        cates.addAll(categoryCounts)
        rvCategorys.layoutManager = LinearLayoutManager(activity!!)
        rvCategorys.adapter = CategoryCountAdapter(activity!!, cates)
        (rvCategorys.adapter as CategoryCountAdapter).onItemClickListener = object : CategoryCountAdapter.OnItemClickListener {
            override fun onClick(Item: GoodsCategoryCount) {
                categoryId = Item.typeId
                if (Item.typeId == 0.toString()) {
                    categoryId = null
                }
                searchView.setSearchText("")
                page = 1
                getListData()
            }

        }
        page = 1
        getListData()
    }

    override fun getContentLayoutId(): Int {
        return R.layout.popup_select_goods
    }

    fun getListData() {
        searchStr = if (TextUtils.isEmpty(searchView.getSearchText())) null else searchView.getSearchText()
        mPresenter.getProductList(page, searchStr, categoryId, "")
    }

    companion object {
        const val TAG = "SelectGoods"
        fun newInstance(str: String): SelectGoodsPopup {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = SelectGoodsPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): SelectGoodsPopup {
            return newInstance("")
        }
    }

    override fun getPresenter(): SelectGoodsPresenter {
        return SelectGoodsPresenter()
    }

    override fun initData() {
        mPresenter.getGoodsCategoryCountList("")
    }

    protected var page: Int = 1// 第一页开始
    protected var dataList = ArrayList<Product>()
    protected var listDataAdapter: SelectProductAdapter? = null
    var searchStr: String? = null
    var categoryId: String? = null
    val unit = AppBusManager.getAmountUnit()
    val titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number), AppContext.getInstance().getString(R.string.productname),AppContext.getInstance().getString(R.string.barcode),AppContext.getInstance().getString(R.string.product_specifications), AppContext.getInstance().getString(R.string.creat_purchase_price)+"($unit)",AppContext.getInstance().getString(R.string.retail_price)+"($unit)")
    val weights = arrayListOf(1F, 2F, 2F, 1F, 1F, 1F)
    override fun initView() {

        for (i in titleList.indices) {
            val tv = TextView(activity)
            if (i == 0) {
                tv.compoundDrawablePadding = DisplayManager.dip2px(8F)!!
                tv.ext_setLeftImageResource(R.mipmap.icon_item_uncheck)
                tv.id = R.id.tvAll
                tv.tag = false
                tv.setOnClickListener(this)
            }
            tv.text = titleList[i]
            tv.paint.isFakeBoldText = true
            tv.gravity = Gravity.CENTER_VERTICAL
            tv.padding = DisplayManager.dip2px(5f)!!
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 11.toFloat())
            tv.setTextColor(resources.getColor(R.color.mainTextColor))
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, if (weights.size == 0) 1.toFloat() else weights[i])
            tv.layoutParams = params
            llTitle.addView(tv)
        }

        rvProductList.layoutManager = LinearLayoutManager(activity!!)
        listDataAdapter = SelectProductAdapter(activity!!, ArrayList())
        listDataAdapter!!.onItemSelectListener = this
        listDataAdapter?.textWeights = weights
        rvProductList.adapter = listDataAdapter


        //设置全局的Header构建器
        AppBusManager.initRefreshUI(refreshLayout)
        refreshLayout.setEnableLoadMore(true)
        (refreshLayout.refreshHeader as ClassicsHeader).setPrimaryColor(resources.getColor(R.color.white))
        refreshLayout.setOnRefreshListener { refreshlayout ->
            page = 1
            getListData()
        }

        refreshLayout.setOnLoadMoreListener { refreshlayout ->
            page += 1
            getListData()
        }
    }

    override fun initListener() {
        tvSure.setOnClickListener(this)
        tvBack.setOnClickListener(this)
        searchView.onQueryClickListner = this
    }


    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!! * 9 / 10
    }

    override fun getHeight(): Int {
        return DisplayManager.getScreenHeight()!! * 85 / 100
    }


    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        ToastUtil.showToast(errorMsg)
        logTools.d(errorMsg)
        if (page == 1) {
            refreshLayout?.finishRefresh()
        } else {
            refreshLayout?.finishLoadMore()
        }
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showContent()
        }
    }

    override fun showLoading(type: String) {
        if (refreshLayout.state != RefreshState.Refreshing && refreshLayout.state != RefreshState.Loading) {
            multipleStatusView?.showLoading()
            multipleStatusView.findViewById<ImageView>(R.id.ivLoading).ext_loadGif(R.mipmap.gif_loading)
        }
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (errorCode == ErrorStatus.SUCCESS) {
            if (this.dataList.size == 0) {
                multipleStatusView?.showEmpty()
            } else {
                multipleStatusView?.showContent()
            }
        }
    }
}


