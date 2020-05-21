package com.epro.pos.ui.fragment

import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.*
import android.support.v4.view.ViewPager
import android.support.v4.widget.SwipeRefreshLayout
import android.view.Gravity
import android.view.View
import android.widget.TabHost
import com.epro.pos.R
import com.epro.pos.listener.CashierFunChangeEvent
import com.epro.pos.listener.LoginSuccessEvent
import com.epro.pos.listener.ShopPosProductsChangeEvent
import com.epro.pos.mvp.contract.CashierContract
import com.epro.pos.mvp.model.bean.ProductCategory
import com.epro.pos.mvp.presenter.CashierPresenter
import com.epro.pos.ui.view.MoreCategorysPopup
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.flyco.tablayout.SlidingTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ext_setRightImageResource
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_cashier.*
import kotlinx.android.synthetic.main.fragment_cashier.llTitle
import kotlinx.android.synthetic.main.language_select_item.*
import kotlinx.android.synthetic.main.person_center_pop.view.*
import kotlinx.android.synthetic.main.popup_checkout.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor
import razerdp.basepopup.BasePopupWindow
import java.util.*
import kotlin.collections.ArrayList

/**
 * 收银台页面
 */
class CashierFragment : BaseTitleBarCustomFragment<CashierContract.View, CashierPresenter>(), CashierContract.View, View.OnClickListener {

    override fun onGetShopProductCategorysSuccess(categorys: ArrayList<ProductCategory>) {
        mTitles.clear()
        for (c in categorys) {
            mTitles.add(c.goodsTypeName)
        }
        val fragmentA = activity as FragmentActivity
        mFragments.clear()
        for (i in mTitles.indices) {
            mFragments.add(ProductListFragment.newInstance(categorys[i], arguments?.getBoolean(CASHIER_FUN)!!).setViewPageFragment(true))
        }
        mAdapter = MyPagerAdapter(fragmentA.supportFragmentManager)
        viewPager.adapter = mAdapter
        tabLayout.setViewPager(viewPager)
        tabLayout.currentTab=0
        for(i in mTitles.indices){
            tabLayout.getTitleView(i).setTextColor(resources.getColor(if(i==0) R.color.mainColor else R.color.mainTextColor))
            tabLayout.getTitleView(i).typeface = Typeface.defaultFromStyle( if(i==0) Typeface.BOLD else Typeface.NORMAL)
        }
        viewPager.addOnPageChangeListener(object :ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }
            override fun onPageSelected(position: Int) {
                for(i in mTitles.indices){
                    //tabLayout.getTitleView(i).setTextColor(resources.getColor(if(i==position) R.color.mainColor else R.color.mainTextColor))
                    tabLayout.getTitleView(i).typeface = Typeface.defaultFromStyle( if(i==position) Typeface.BOLD else Typeface.NORMAL)
                }
                logTools.d(position)
            }

        })
        cartFragment?.products=PosBusManager.getShopProductCategorys()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginSuccessEvent(event: LoginSuccessEvent) {
        mPresenter.getShopProductCategorys(true, PosConst.GET_POS_CATEGORY_PRODUCTS)
    }

    override fun onClick(v: View?) {
        when (v) {
            llMore -> {
                tvMore.ext_setRightImageResource(R.mipmap.arrow_up)
                showMoreCategoryPopWindow()
            }
        }
    }

    private fun showMoreCategoryPopWindow() {
        if (mTitles.isEmpty()) {
            toast(getString(R.string.more_categories_not_loaded))
            return
        }
        val pop = MoreCategorysPopup(activity!!, mTitles)
        pop.popupGravity = Gravity.BOTTOM
        pop.width = llTitle.measuredWidth+20
        pop.categorys = mTitles
        pop.setBackground(ColorDrawable(resources.getColor(android.R.color.transparent)))
        pop.onItemClickListener = object : MoreCategorysPopup.OnItemClickListener {
            override fun onClick(item: String, position: Int) {
                tabLayout.currentTab = position
                pop.dismiss()
            }
        }
        pop.onDismissListener=object:MoreCategorysPopup.OnDismissListener{
            override fun onDismiss() {
               tvMore.ext_setRightImageResource(R.mipmap.arrow_down)
            }
        }
        pop.offsetX=-10
        pop.showPopupWindow(vLine1)
    }

    companion object {
        const val TAG = "CashierFragment"
        const val CASHIER_FUN = "cashier_fun"
        fun newInstance(isRefund: Boolean): CashierFragment {
            val args = Bundle()
            args.putBoolean(CASHIER_FUN, isRefund)
            val fragment = CashierFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): CashierFragment {
            return newInstance(false)
        }
    }

    private val mFragments = ArrayList<Fragment>()
    private var mAdapter: MyPagerAdapter? = null
    val res = AppContext.getInstance().resources
    val mTitles = ArrayList<String>()
    var isRefresh = false
    var cartFragment:CartFragment?=null
    override fun initView() {
        super.initView()
        setRefreshEnable(true)
        val isRefund=arguments?.getBoolean(CASHIER_FUN)!!
        getTitleView().text =if(isRefund) resources.getString(R.string.refunds) else resources.getString(R.string.cashier)
        cartFragment=CartFragment.newInstance(isRefund)
        childFragmentManager.beginTransaction().replace(R.id.fl_container, cartFragment, CartFragment::class.java.simpleName).commit()
        getRefreshLayout().setOnRefreshListener { refreshlayout ->
            refreshProductsFromNet()
        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onCashierFunChangeEvent(event: CashierFunChangeEvent) {
        getTitleView().text =if(event.isRefund) resources.getString(R.string.refunds) else resources.getString(R.string.cashier)
    }


    fun refreshProductsFromNet() {
        isRefresh = true
        lazyLoad()
        isRefresh = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState)
    }

    override fun layoutContentId(): Int {
        return R.layout.fragment_cashier
    }

    override fun lazyLoad() {
        mPresenter.getShopProductCategorys(isRefresh, PosConst.GET_POS_CATEGORY_PRODUCTS)
    }

    override fun initData() {

    }

    override fun getPresenter(): CashierPresenter {
        return CashierPresenter()
    }


    override fun initListener() {
        llMore.setOnClickListener(this)
    }

    private inner class MyPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int {
            return mFragments.size
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return mTitles[position].toString()
        }

        override fun getItem(position: Int): Fragment {
            return mFragments.get(position)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onShopPosProductsChangeEvent(event: ShopPosProductsChangeEvent) {
        refreshProductsFromNet()
    }

}