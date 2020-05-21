package com.epro.pos.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.FragmentTransaction
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.KeyEvent
import android.view.View
import android.widget.ImageView
import com.epro.pos.R
import com.epro.pos.listener.*
import com.epro.pos.mvp.model.bean.Item
import com.epro.pos.ui.adapter.MainMenuAdapter
import com.epro.pos.ui.fragment.*
import com.epro.pos.ui.login.LoginActivity
import com.epro.pos.utils.PosConst
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.listener.TokenExpiredEvent
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.ext_loadGif
import com.mike.baselib.utils.toast
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class MainActivity : BaseSimpleActivity(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            llBottom -> {
                isPos = !isPos
                initTab()
                switchFragment()
                tvSystem.text = if (isPos)getString(R.string.app_name) else getString(R.string.pos_cashier)
                ivSystem.setImageResource(if (isPos) R.mipmap.enter_merchant_background else R.mipmap.enter_pos)
            }
        }
    }


    companion object {
        const val REQUEST_CODE_FOR_LOGIN_RESULT = 1
        const val TAG = "MainActivity"
        const val RC_PHONE_STATE = 16

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, MainActivity::class.java).putExtra(TAG, str))
        }

        fun launchMain(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            context.startActivity(intent)
        }
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_main
    }

    private val mBackStageTitles = arrayOf(R.string.home_statistics, R.string.data_board, R.string.goods_files, R.string.trade_manager, R.string.financial_manager, R.string.stock_manager, R.string.netshop_manager, R.string.message_center, R.string.account_setting)
    private val mPosTitles = arrayOf(R.string.cashier, R.string.refunds, R.string.trade_query, R.string.cashier_reconciliation)

    // 未被选中的图标
    private val mBackStageIconUnSelectIds = intArrayOf(R.mipmap.home_statistics_unselected, R.mipmap.data_board_unselected, R.mipmap.product_file_unselected, R.mipmap.order_management_unselected, R.mipmap.financial_management_unselected, R.mipmap.inventory_management_unselected, R.mipmap.site_management_unselected, R.mipmap.message_center_unselected, R.mipmap.system_settings_unselected)
    // 被选中的图标
    private val mBackStageIconSelectIds = intArrayOf(R.mipmap.home_statistics_selected, R.mipmap.data_board_selected, R.mipmap.product_file_selected, R.mipmap.order_management_selected, R.mipmap.financial_management_selected, R.mipmap.inventory_management_selected, R.mipmap.site_management_selected, R.mipmap.message_center_selected, R.mipmap.system_settings_selected)
    // 未被选中的图标
    private val mPosIconUnSelectIds = intArrayOf(R.mipmap.pos_cashier_normal, R.mipmap.pos_refunds_normal, R.mipmap.pos_transaction_normal, R.mipmap.pos_cashier_recon_normal)
    // 被选中的图标
    private val mPosIconSelectIds = intArrayOf(R.mipmap.pos_cashier_select, R.mipmap.pos_refunds_select, R.mipmap.pos_transaction_select, R.mipmap.pos_cashier_recon_select)

    private var mHomeFragment: HomeFragment? = null
    private var mDataBoardFragment: DataBoardFragment? = null
    private var mGoodsFilesFragment: BusShowListContainerFragment? = null
    private var mTradeManageFragment: TradeManageFragment? = null
    private var mFinancialManagerFragment: FinancialManagerFragment? = null
    private var mStockManageFragment: StockManageFragment? = null
    private var mNetShopManageFragment: NetShopManageFragment? = null
    private var mMessageCenterFragment: MessageCenterFragment? = null
    private var mAccountSetFragment: SystemSettingsFragment? = null

    private var mCashierFragment: CashierFragment? = null
    //private var mRefundsFragment: CashierFragment? = null
    private var mTradeQueryFragment: BusShowListContainerFragment? = null
    private var mCashierReconciliationFragment: CashierReconciliationFragment? = null
    //private var mPosSetFragment: PosSetFragment? = null


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_FOR_LOGIN_RESULT -> {
                if (resultCode == Activity.RESULT_CANCELED) {
                    finish()
                }
                if (resultCode == Activity.RESULT_OK) {
                }
            }
        }
    }

    private var isPos = true
    var mBackStageIndex = 0
    var mPosIndex = 0
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //AppConfig.init()
        val tag = intent?.getStringExtra("EXIT_TAG")
        val finish = intent?.getBooleanExtra("FINISH", true)
        if (tag != null && !TextUtils.isEmpty(tag)) {
            if ("CLEAR_TOP".equals(tag)) {//退出程序
                if (finish!!) {
                    finish()
                }
            }
        }
        ImageView(this).ext_loadGif(R.mipmap.gif_loading)
    }

    override fun initStatusBar() {
        super.initStatusBar()
//        StatusBarUtil.darkMode(this@MainActivity,false)
//        StatusBarUtil.immersive(this@MainActivity, resources.getColor(R.color.purple_d7a6fc), 1f)
        // StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }

    private fun initBackStageTab() {
        val menuList = ArrayList<Item>()
        for (i in mBackStageTitles.indices) {
            menuList.add(Item(mBackStageIconUnSelectIds[i], getString(mBackStageTitles[i]), i == 0).valueIcon2(mBackStageIconSelectIds[i]))
        }
        val it = menuList[1]
        it.valueContent2("1")
        rvMenu.layoutManager = LinearLayoutManager(this)
        rvMenu.adapter = MainMenuAdapter(this, menuList)
        (rvMenu.adapter as MainMenuAdapter).onItemClickListener = object : MainMenuAdapter.OnItemClickListener {
            override fun onItemClick(item: Item, position: Int) {
                mBackStageIndex = position
                switchBackStageFragment()
            }
        }

    }

    private fun initPosTab() {
        val menuList = ArrayList<Item>()
        for (i in mPosTitles.indices) {
            menuList.add(Item(mPosIconUnSelectIds[i], getString(mPosTitles[i]), i == 0).valueIcon2(mPosIconSelectIds[i]))
        }
        rvMenu.layoutManager = LinearLayoutManager(this)
        rvMenu.adapter = MainMenuAdapter(this, menuList)
        (rvMenu.adapter as MainMenuAdapter).onItemClickListener = object : MainMenuAdapter.OnItemClickListener {
            override fun onItemClick(category: Item, position: Int) {
                mPosIndex = position
                switchPosFragment()
            }
        }

    }

    private fun initTab() {
        if (isPos) {
            initPosTab()
        } else {
            initBackStageTab()
        }
    }

    private fun switchFragment() {
        if (isPos) {
            switchPosFragment()
        } else {
            switchBackStageFragment()
        }
    }

    /**
     * 切换Fragment
     * @param position 下标
     */
    fun switchBackStageFragment() {
        AppUtils.closeKeyboard(this)
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (mBackStageIndex) {
            0 // 首页
            -> mHomeFragment?.let {
                transaction.show(it)
            } ?: HomeFragment().let {
                mHomeFragment = it
                transaction.add(com.epro.pos.R.id.fl_container, it, "tab_home")
            }

            1 // 数据看板
            -> mDataBoardFragment?.let {
                transaction.show(it)
            } ?: DataBoardFragment().let {
                mDataBoardFragment = it
                transaction.add(R.id.fl_container, it, "tab_data")
            }

            2 // 商品档案
            -> mGoodsFilesFragment?.let {
                transaction.show(it)
            } ?: BusShowListContainerFragment.newInstance(PosConst.GET_GOODS_FILE_LIST).let {
                mGoodsFilesFragment = it
                transaction.add(R.id.fl_container, it, "tab_goods")
            }

            3 // 交易管理
            -> {
                mTradeManageFragment?.let {
                    transaction.show(it)
                    logTools.d("mTradeManageFragment1")
                } ?: TradeManageFragment.newInstance().let {
                    mTradeManageFragment = it
                    transaction.add(R.id.fl_container, it, "tab_trade")
                    logTools.d("mTradeManageFragment2")
                }
            }
            4 // 财务管理
            -> mFinancialManagerFragment?.let {
                transaction.show(it)
            } ?: FinancialManagerFragment().let {
                mFinancialManagerFragment = it
                transaction.add(R.id.fl_container, it, "tab_financial")
            }
            5 // 库促管理
            -> mStockManageFragment?.let {
                transaction.show(it)
            } ?: StockManageFragment.newInstance().let {
                mStockManageFragment = it
                transaction.add(R.id.fl_container, it, "tab_stock")
            }
            6// 网店管理
            -> mNetShopManageFragment?.let {
                transaction.show(it)
            } ?: NetShopManageFragment.newInstance().let {
                mNetShopManageFragment = it
                transaction.add(R.id.fl_container, it, "tab_netshop")
            }
            7//6消息中心
            -> mMessageCenterFragment?.let {
                transaction.show(it)
            } ?: MessageCenterFragment().let {
                mMessageCenterFragment = it
                transaction.add(R.id.fl_container, it, "tab_message")
            }
            8// 账户设置
            -> mAccountSetFragment?.let {
                transaction.show(it)
            } ?: SystemSettingsFragment().let {
                mAccountSetFragment = it
                transaction.add(R.id.fl_container, it, "tab_account")
            }
            else -> {

            }
        }
        val datas = (rvMenu.adapter as MainMenuAdapter).mData
        for (i in datas.indices) {
            datas[i].judgeValue = i == mBackStageIndex
        }
        rvMenu.adapter.notifyDataSetChanged()
        transaction.commitAllowingStateLoss()
    }

    /**
     * 切换Fragment
     * @param position 下标
     */
    private fun switchPosFragment() {
        AppUtils.closeKeyboard(this)
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (mPosIndex) {
            0 // 收银台
            -> {
                mCashierFragment?.let {
                    transaction.show(it)
                } ?: CashierFragment.newInstance(false).let {
                    mCashierFragment = it
                    transaction.add(R.id.fl_container, it, "tab_cashier")
                }
                EventBus.getDefault().post(CashierFunChangeEvent(false))
            }
            1 // 退货退款
            -> {
                mCashierFragment?.let {
                    transaction.show(it)
                } ?: CashierFragment.newInstance(true).let {
                    mCashierFragment = it
                    transaction.add(R.id.fl_container, it, "tab_cashier")
                }
                EventBus.getDefault().post(CashierFunChangeEvent(true))
            }

            2 // 交易查询
            -> mTradeQueryFragment?.let {
                transaction.show(it)
            } ?: BusShowListContainerFragment.newInstance(PosConst.GET_POS_TRADE_LIST).let {
                mTradeQueryFragment = it
                transaction.add(R.id.fl_container, it, "tab_trade_query")
            }

            3 // 收银对账
            -> mCashierReconciliationFragment?.let {
                transaction.show(it)
            } ?: CashierReconciliationFragment().let {
                mCashierReconciliationFragment = it
                transaction.add(R.id.fl_container, it, "tab_reconciliation")
            }
//            4 // pos设置
//            -> mPosSetFragment?.let {
//                transaction.show(it)
//            } ?: PosSetFragment().let {
//                mPosSetFragment = it
//                transaction.add(R.productId.fl_container, it, "tab_pos_set")
//            }
            else -> {

            }
        }
        val datas = (rvMenu.adapter as MainMenuAdapter).mData
        for (i in datas.indices) {
            datas[i].judgeValue = i == mPosIndex
        }
        rvMenu.adapter.notifyDataSetChanged()
        transaction.commitAllowingStateLoss()
    }


    /**
     * 隐藏所有的Fragment
     * @param transaction transaction
     */
    private fun hideBackStageFragments(transaction: FragmentTransaction) {
        mHomeFragment?.let { transaction.hide(it) }
        mDataBoardFragment?.let { transaction.hide(it) }
        mGoodsFilesFragment?.let { transaction.hide(it) }
        mTradeManageFragment?.let { transaction.hide(it) }
        mFinancialManagerFragment?.let { transaction.hide(it) }
        mStockManageFragment?.let { transaction.hide(it) }
        mNetShopManageFragment?.let { transaction.hide(it) }
        mAccountSetFragment?.let { transaction.hide(it) }
        mMessageCenterFragment?.let { transaction.hide(it) }
    }

    private fun hidePosFragments(transaction: FragmentTransaction) {
        mCashierFragment?.let { transaction.hide(it) }
        // mRefundsFragment?.let { transaction.hide(it) }
        mTradeQueryFragment?.let { transaction.hide(it) }
        mCashierReconciliationFragment?.let { transaction.hide(it) }
        // mPosSetFragment?.let { transaction.hide(it) }
    }

    private fun hideFragments(transaction: FragmentTransaction) {
        hideBackStageFragments(transaction)
        hidePosFragments(transaction)
    }

    fun clearFragment() {
        for (f in supportFragmentManager.fragments) {
            if (f.isAdded) {
                supportFragmentManager.beginTransaction().remove(f).commitAllowingStateLoss()
            }
        }
        mHomeFragment = null
        mDataBoardFragment = null
        mGoodsFilesFragment = null
        mTradeManageFragment = null
        mFinancialManagerFragment = null
        mStockManageFragment = null
        mNetShopManageFragment = null
        mAccountSetFragment = null
        mMessageCenterFragment = null
        mCashierFragment = null
        mTradeQueryFragment = null
        mCashierReconciliationFragment = null
    }

    override fun initView() {
        super.initView()
        if (!AppBusManager.isLogin()) {
            goLogin()
        } else {
            initTab()
            switchFragment()
            tvSystem.text = if (isPos) getString(R.string.app_name) else getString(R.string.pos_cashier)
        }
    }


    override fun initData() {
    }

    override fun initListener() {
        llBottom.setOnClickListener(this)
    }

    private var mExitTime: Long = 0

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                toast(getString(R.string.touch_again_logout))
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginSuccessEvent(event: LoginSuccessEvent) {
        isPos = true
        mPosIndex = 0
        logTools.d("do here")
        clearFragment()
        initTab()
        switchFragment()
        tvSystem.text = if (isPos) getString(R.string.app_name) else getString(R.string.pos_cashier)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onUnLoginEvent(event: UnLoginEvent) {
        logTools.d("do here")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTokenExpiredEvent(event: TokenExpiredEvent) {
        if (!AppBusManager.isLogin()) {
            launchMain(this)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBackStageCurrentTabChangeEvent(event: BackStageCurrentTabChangeEvent) {
        when (event.action) {
            PosConst.GET_ORDER_RECORD_LIST -> {//直接跳转订单记录页面
                if (event.position == 3) { //订单管理
                    if (mTradeManageFragment == null) {
                        mTradeManageFragment = TradeManageFragment.newInstance(event.action, event.extra)
                        supportFragmentManager.beginTransaction().add(R.id.fl_container, mTradeManageFragment, "tab_trade").commitAllowingStateLoss()
                    } else {
                        mTradeManageFragment?.arguments!!.putString(TradeManageFragment.TAG, event.action)
                        mTradeManageFragment?.arguments!!.putString(TradeManageFragment.EXTRA, event.extra)
                    }
                }
            }

            PosConst.GET_NETSHOP_ONLINE_ORDER_LIST -> {
                if (event.position == 6) { //网店管理
                    if (mNetShopManageFragment == null) {
                        mNetShopManageFragment = NetShopManageFragment.newInstance(event.action, event.extra)
                        supportFragmentManager.beginTransaction().add(R.id.fl_container, mNetShopManageFragment, "tab_netshop").commitAllowingStateLoss()
                    } else {
                        mNetShopManageFragment?.arguments!!.putString(NetShopManageFragment.TAG, event.action)
                        mNetShopManageFragment?.arguments!!.putString(NetShopManageFragment.EXTRA, event.extra)
                    }
                }
            }

            //跳转商品档案
            PosConst.GET_GOODS_FILE_LIST->{
                if (event.position == 2) { //商品档案
                    if (mGoodsFilesFragment == null) {
                        mGoodsFilesFragment = BusShowListContainerFragment.newInstance(event.action, event.extra)
                        supportFragmentManager.beginTransaction().add(R.id.fl_container, mGoodsFilesFragment, "tab_netshop").commitAllowingStateLoss()
                    } else {
                        supportFragmentManager.beginTransaction().show(mGoodsFilesFragment)
                    }
                }
            }

            //系统设置
            PosConst.BUSINESS_BASE_INFO -> {
                if (8 == event.position) {
                    if (mAccountSetFragment == null) {
                        logTools.t("YB").d(" Main onClickItem 111 : ")
                        mAccountSetFragment = SystemSettingsFragment.newInstance(event.action,event.extra)
                        supportFragmentManager.beginTransaction().add(R.id.fl_container, mAccountSetFragment, "tab_settings").commitAllowingStateLoss()
                    } else {
                        var msg = FragmentChanageEvent()
                        msg.postion = 1
                        msg.from = event.extra
                        EventBus.getDefault().postSticky(msg)
                        logTools.t("YB").d(" Main onClickItem 222: "+msg.from)
                    }
                }
            }

            //意见反馈
            PosConst.COMMIT_FEEDBACK->{
                if (8 == event.position) {
                    if (mAccountSetFragment == null) {
                        mAccountSetFragment = SystemSettingsFragment.newInstance(event.action,event.extra)
                        supportFragmentManager.beginTransaction().add(R.id.fl_container, mAccountSetFragment, "tab_settings").commitAllowingStateLoss()
                    } else {
                        var msg = FragmentChanageEvent()
                        msg.postion = 2
                        msg.from = event.extra
                        EventBus.getDefault().postSticky(msg)
                        logTools.t("YB").d(" Main onClickItem : "+msg.from)
                    }
                }
            }

            //版本更新
            PosConst.VERSION_UPDATE->{
                if (8 == event.position) {
                    if (mAccountSetFragment == null) {
                        mAccountSetFragment = SystemSettingsFragment.newInstance(event.action,event.extra)
                        supportFragmentManager.beginTransaction().add(R.id.fl_container, mAccountSetFragment, "tab_settings").commitAllowingStateLoss()
                    } else {
                        var msg = FragmentChanageEvent()
                        msg.postion = 6
                        msg.from = event.extra
                        EventBus.getDefault().postSticky(msg)
                        logTools.t("YB").d(" Main onClickItem : "+msg.from)
                    }
                }
            }

            //库存预警
            PosConst.GET_STOCK_QUERY_LIST->{
                if (event.position == 5) {
                    if (mStockManageFragment == null) {
                        mStockManageFragment = StockManageFragment.newInstance(event.action,event.extra)
                        supportFragmentManager.beginTransaction().add(R.id.fl_container, mStockManageFragment, "tab_stock").commitAllowingStateLoss()
                    } else {
                        mStockManageFragment?.arguments!!.putString(StockManageFragment.TAG, event.action)
                        mStockManageFragment?.arguments!!.putString(StockManageFragment.EXTRA, event.extra)
                    }
                }
            }

        }
        mBackStageIndex = event.position
        switchBackStageFragment()

    }

    fun <T> getFragmentFromTabByClassName(tabTag: String, clazz: Class<T>): T {
        val tabFragment = supportFragmentManager.findFragmentByTag(tabTag)
        val fragment = tabFragment.childFragmentManager.findFragmentByTag(clazz.simpleName) as T
        return fragment
    }

    fun <T> getFragmentFromTabCashierByClassName(clazz: Class<T>): T {
        return getFragmentFromTabByClassName("tab_cashier", clazz)
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle?) {
        //super.onSaveInstanceState(outState)
    }

    fun goLogin() {
        if (!AppBusManager.isLogin()) {
            LoginActivity.launchForReslut(this, REQUEST_CODE_FOR_LOGIN_RESULT)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        goLogin()
    }
}

