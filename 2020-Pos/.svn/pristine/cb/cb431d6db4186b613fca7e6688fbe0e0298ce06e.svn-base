package com.epro.pos.ui.fragment

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.epro.pos.listener.CartProductChangeEvent
import com.epro.pos.listener.CashierFunChangeEvent
import com.mike.baselib.fragment.BaseTitleBarListFragment
import com.epro.pos.mvp.contract.ProductListContract
import com.epro.pos.mvp.model.bean.CartProduct
import com.epro.pos.mvp.model.bean.Product
import com.epro.pos.mvp.model.bean.ProductCategory
import com.epro.pos.mvp.presenter.ProductListPresenter
import com.epro.pos.ui.MainActivity
import com.epro.pos.ui.adapter.ProductListAdapter
import com.epro.pos.utils.PosBusManager
import com.google.gson.reflect.TypeToken
import com.mike.baselib.fragment.BaseListFragment
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.ext_createJsonKey
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.doAsync

class ProductListFragment : BaseListFragment<Product, ProductListContract.View
        , ProductListPresenter, ProductListAdapter>(), ProductListContract.View {

    companion object {
        const val TAG = "ProductList"
        const val CASHIER_FUN = "cashier_fun"
        fun newInstance(category: ProductCategory,isRefund: Boolean): ProductListFragment {
           return newInstance(category.goodsProductDetailList,isRefund)
        }
        fun newInstance(products:ArrayList<Product>,isRefund: Boolean): ProductListFragment {
            val args = Bundle()
            args.putString(TAG, AppBusManager.toJson(products))
            args.putBoolean(CASHIER_FUN, isRefund)
            val fragment = ProductListFragment()
            fragment.setArguments(args)
            return fragment
        }
    }

    override fun getListAdapter(list: ArrayList<Product>): ProductListAdapter {
        val type = object : TypeToken<ArrayList<Product>>() {}.type
        val products = AppBusManager.fromJson<ArrayList<Product>>(arguments?.getString(TAG)!!,type)
        if(products!=null){
            list.addAll(products)
        }
        return ProductListAdapter(activity!!, list)
    }

    override fun getListData() {

        // mPresenter.getProductList("")
    }

    override fun initData() {

    }

    override fun getPresenter(): ProductListPresenter {
        return ProductListPresenter()
    }
    var isRefund = false
    override fun initView() {
        super.initView()
        isRefund = arguments?.getBoolean(CartFragment.CASHIER_FUN)!!
        getRefreshView().setEnableLoadMore(false)
        getRefreshView().setEnableRefresh(false)
        getRvListView().layoutManager = GridLayoutManager(activity, 4)
        listDataAdapter?.onItemClickListener = object : ProductListAdapter.OnItemClickListener {
            override fun onClick(item: Product) {
                val cartProduct = CartProduct(if(PosBusManager.isRefund()) -1 else 1, item)
                PosBusManager.setOneCartProduct(cartProduct)
                EventBus.getDefault().post(CartProductChangeEvent())
            }
        }
    }

    override fun initListener() {
    }

    override fun lazyLoad() {

    }

}


