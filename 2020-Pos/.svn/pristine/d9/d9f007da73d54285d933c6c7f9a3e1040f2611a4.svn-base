package com.epro.pos.ui.fragment;

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.listener.RefreshShowListUIEvent
import com.epro.pos.listener.ScanResultEvent
import com.epro.pos.listener.SearchResultEvent
import com.epro.pos.listener.SelectedProductEvent
import com.epro.pos.mvp.contract.EditStockTakingDetailContract
import com.epro.pos.mvp.model.bean.ProductDetail
import com.epro.pos.mvp.model.bean.StockTaking
import com.epro.pos.mvp.presenter.EditStockTakingDetailPresenter
import com.epro.pos.ui.activity.ScanActivity
import com.epro.pos.ui.adapter.EditStockTakingDetailAdapter
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.*
import com.mike.baselib.view.CommonDialog
import com.mike.baselib.view.recyclerview.MultipleType
import kotlinx.android.synthetic.main.popup_edit_stock_taking_detail.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class EditStockTakingDetailPopup : BaseDialogFragment<EditStockTakingDetailContract.View, EditStockTakingDetailPresenter>(), EditStockTakingDetailContract.View, View.OnClickListener, EditStockTakingDetailAdapter.OnAddClickListener {
    override fun onAddStockTakingSuccess() {
        if (stockStatus==PosConst.STOCK_TAKING_NO) {
            toast(getString(R.string.saved_successfully))
        } else {
            toast(getString(R.string.inventory_success))
        }
        EventBus.getDefault().post(RefreshShowListUIEvent())
        dismiss()
    }

    override fun onUpdateStockTakingSuccess() {
        if (stockStatus==PosConst.STOCK_TAKING_YES) {
            toast(getString(R.string.saved_successfully))
        } else {
            toast(getString(R.string.inventory_success))
        }
        EventBus.getDefault().post(RefreshShowListUIEvent())
        dismiss()
    }

    override fun onDeleteStockTakingSuccess() {
        toast(getString(R.string.delete_success))
        EventBus.getDefault().post(RefreshShowListUIEvent())
        dismiss()
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        when (type) {
            PosConst.GET_GOODS_DETAIL_FROM_BARCODE -> {
                val sevent = SearchResultEvent()
                sevent.isEmpty = true
                EventBus.getDefault().post(sevent)
            }
        }
    }

    override fun onGetProductDetailSuccess(productDetail: ProductDetail) {
        val spList = ArrayList<StockTaking.StockProductInfo>()
        val sp = StockTaking.StockProductInfo(1, productDetail.buyingPrice, productDetail.goodsName, productDetail.productBarCode,
                productDetail.productId, productDetail.specificationsValueNames, productDetail.productNumber, productDetail.goodsTypeName, productDetail.goodsUnitName)
        spList.add(sp)
        adapter!!.updateData(spList)
        val sevent = SearchResultEvent()
        sevent.isEmpty = false
        EventBus.getDefault().post(sevent)
    }

    override fun onAddClick() {
        showSelectGoodsTypeDialog()
    }

    //选择商品添加方式
    private fun showSelectGoodsTypeDialog() {
        CommonDialog.Builder(activity!!)
                .setContent(getString(R.string.choose_how_to_add_products))
                .setCancelText(getString(R.string.scan_code))
                .setConfirmText(getString(R.string.product_archive))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        SelectGoodsPopup.newInstance().show(childFragmentManager, "select_goods")
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        startScan()
                    }
                })
                .create()
                .show()
    }

    private fun showDeleteStockBillDialog() {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.delete))
                .setContent(getString(R.string.confirm_detele_doc))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        mPresenter.deleteStockTaking(stockTaking!!.inventoryCode, PosConst.DELETE_STOCK_TAKING)
                    }
                })
                .create()
                .show()
    }
    var stockStatus=PosConst.STOCK_TAKING_NO
    private fun showSaveStockBillDialog(isCancel: Boolean = false) {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.edit_person_save))
                .setContent(getString(R.string.page_content_has_been_modified))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        val remark = if (etRemark.text.toString().isEmpty()) null else etRemark.text.toString()
                        //adapter!!.mData.removeAt(adapter!!.mData.size-1)
                        stockStatus=PosConst.STOCK_TAKING_NO
                        if (stockTaking == null) {
                            mPresenter.addStockTaking(PosConst.STOCK_TAKING_NO, remark, adapter!!.mData, PosConst.ADD_STOCK_TAKING)
                        } else {
                            mPresenter.updateStockTaking(PosConst.STOCK_TAKING_NO, stockTaking!!.inventoryCode, remark, adapter!!.mData, PosConst.UPDATE_STOCK_TAKING)
                        }
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        if (isCancel) {
                            dismiss()
                        }
                    }
                })
                .create()
                .show()
    }

    private fun showSaveAndInStockBillDialog() {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.keep_warehouse))
                .setContent(getString(R.string.page_content_has_been_modified))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        // adapter!!.mData.removeAt(adapter!!.mData.size-1)
                        val remark = if (etRemark.text.toString().isEmpty()) null else etRemark.text.toString()
                        stockStatus=PosConst.STOCK_TAKING_YES
                        if (stockTaking == null) {
                            mPresenter.addStockTaking(PosConst.STOCK_TAKING_YES, remark, adapter!!.mData, PosConst.ADD_STOCK_TAKING)
                        } else {
                            mPresenter.updateStockTaking(PosConst.STOCK_TAKING_YES, stockTaking!!.inventoryCode, remark, adapter!!.mData, PosConst.UPDATE_STOCK_TAKING)
                        }
                    }
                })
                .create()
                .show()
    }

    @AfterPermissionGranted(CartFragment.RC_CAMERA)
    private fun startScan() {
        if (EasyPermissions.hasPermissions(activity!!, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            // Already have permission, do the thing
            ScanActivity.launchWithScanType(this, FOR_GOODS)
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.pls_permission_open_camera),
                    CartFragment.RC_CAMERA, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    var scanBarCode = ""
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onScanResultEvent(event: ScanResultEvent) {
        if (event.scanType == ScanActivity.SCAN_FOR_GOODS) {
            scanBarCode = event.result
            mPresenter.getProdutDetailByBarcode(scanBarCode, PosConst.GET_GOODS_DETAIL_FROM_BARCODE)
        }
    }


    override fun onClick(p0: View?) {
        when (p0) {
            tvCancel -> {
                if (adapter!!.mData.isEmpty() || (stockTaking != null && stockTaking!!.inventoryStatus == PosConst.STOCK_TAKING_YES)
                        || !adapter!!.checkChanged()) {
                    dismiss()
                } else {
                    showSaveStockBillDialog(true)
                }
            }
            tvDelete -> {
                showDeleteStockBillDialog()
            }
            tvSave -> {
                if (adapter!!.mData.isEmpty()) {
                    toast(getString(R.string.pls_choose_products))
                    return
                }
//                if (adapter!!.checkStockNumZero()) {
//                    toast("输入库存数量不能为0")
//                    return
//                }
                val remark = if (etRemark.text.toString().isEmpty()) null else etRemark.text.toString()
                if (stockTaking == null) {
                    mPresenter.addStockTaking(PosConst.STOCK_TAKING_NO, remark, adapter!!.mData, PosConst.ADD_STOCK_TAKING)
                } else {
                    mPresenter.updateStockTaking(PosConst.STOCK_TAKING_NO, stockTaking!!.inventoryCode, remark, adapter!!.mData, PosConst.UPDATE_STOCK_TAKING)
                }
            }
            tvSaveAndIn -> {
                if (adapter!!.mData.isEmpty()) {
                    toast(getString(R.string.pls_choose_products))
                    return
                }
//                if (adapter!!.checkStockNumZero()) {
//                    toast("输入库存数量不能为0")
//                    return
//                }
                showSaveAndInStockBillDialog()
            }
            tvAdd -> {
                SelectGoodsPopup.newInstance().show(childFragmentManager, "select_goods")
            }
            tvScanAdd -> {
                startScan()
            }
        }
    }

    companion object {
        const val TAG = "EditStockDetail"
        const val FOR_GOODS = 10
        fun newInstance(stockTaking: StockTaking?): EditStockTakingDetailPopup {
            val args = Bundle()
            args.putString(AppBusManager.ext_createJsonKey(StockTaking::class.java), AppBusManager.toJson(stockTaking))
            val fragment = EditStockTakingDetailPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): EditStockTakingDetailPopup {
            return newInstance(null)
        }
    }

    override fun getPresenter(): EditStockTakingDetailPresenter {
        return EditStockTakingDetailPresenter()
    }


    override fun getContentLayoutId(): Int {
        return R.layout.popup_edit_stock_taking_detail
    }


    override fun initData() {

    }

    val unit = AppBusManager.getAmountUnit()
    var titleList = arrayListOf<String>()
    var weights = arrayListOf(1F, 3F, 2F, 1F, 1F, 1F, 1F, 1.5F)
    var stockTaking: StockTaking? = null
    var adapter: EditStockTakingDetailAdapter? = null
    @SuppressLint("ResourceType")
    override fun initView() {
        stockTaking = AppBusManager.fromJsonWithClassKey(arguments!!, StockTaking::class.java)
        val products = ArrayList<StockTaking.StockProductInfo>()
        if (stockTaking != null && stockTaking!!.inventoryStatus == PosConst.STOCK_TAKING_YES) {  //详情模式
            titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number), AppContext.getInstance().getString(R.string.barcode), AppContext.getInstance().getString(R.string.productname), AppContext.getInstance().getString(R.string.unit), AppContext.getInstance().getString(R.string.specification), AppContext.getInstance().getString(R.string.the_actual_amount), AppContext.getInstance().getString(R.string.system_inventory), AppContext.getInstance().getString(R.string.profit_and_loss), AppContext.getInstance().getString(R.string.commodity_purchase_price)+"($unit)", AppContext.getInstance().getString(R.string.profit_and_loss_amount)+"($unit)")
            weights = arrayListOf(1F, 2F, 2F, 1F, 1F, 1F, 1F, 1F, 1F, 1F)
            tvBusinessNo.text = getString(R.string.business_ticket_number) + stockTaking?.inventoryCode
            tvGoodsStatus.text = getString(R.string.product_status) + PosBusManager.getStockTakingText(stockTaking?.inventoryStatus!!)
            products.addAll(stockTaking!!.infoResponseList)
            tvTitle.text = getString(R.string.stock_taking_detail)
            llLeft.visibility = View.GONE
            llRight.gravity = Gravity.START
            tvRemark.text=stockTaking!!.remarks
        } else {
            titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number), AppContext.getInstance().getString(R.string.barcode),getString(R.string.productname), AppContext.getInstance().getString(R.string.unit), AppContext.getInstance().getString(R.string.specification), AppContext.getInstance().getString(R.string.the_actual_amount), AppContext.getInstance().getString(R.string.system_inventory),AppContext.getInstance().getString(R.string.profit_and_loss), AppContext.getInstance().getString(R.string.commodity_purchase_price)+"($unit)", AppContext.getInstance().getString(R.string.profit_and_loss_amount)+"($unit)", AppContext.getInstance().getString(R.string.operating))
            weights = arrayListOf(1F, 2F, 2F, 1F, 1F, 1F, 1F, 1F, 1F, 1F, 1F)
            llBottom.visibility = View.VISIBLE
            etRemark.visibility = View.VISIBLE
            tvTitle.text = getString(R.string.edit_inventory)
            if (stockTaking == null) {
                tvTitle.text=getString(R.string.add_inventory)
                tvDelete.visibility = View.GONE
            } else {
                tvBusinessNo.text = getString(R.string.business_ticket_number) + stockTaking?.inventoryCode
                tvGoodsStatus.text = getString(R.string.product_status) + PosBusManager.getStockTakingText(stockTaking?.inventoryStatus!!)
                products.addAll(stockTaking!!.infoResponseList)
                etRemark.setText(stockTaking!!.remarks)
            }
            // products.add(StockTaking.StockProductInfo())
        }

        for (i in titleList.indices) {
            val tv = TextView(activity)
            tv.text = titleList[i]
            tv.paint.isFakeBoldText = true
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 12.toFloat())
            tv.setTextColor(resources.getColor(R.color.mainTextColor))
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, if (weights.size == 0) 1.toFloat() else weights[i])
            tv.layoutParams = params
            llTitle.addView(tv)
        }

        rvList.layoutManager = LinearLayoutManager(activity!!)
        adapter = EditStockTakingDetailAdapter(activity!!, products, object : MultipleType<StockTaking.StockProductInfo> {
            override fun getLayoutId(item: StockTaking.StockProductInfo, position: Int): Int {
//                if (stockTaking == null) {
//                    if (position == products.size - 1) {
//                        return R.layout.item_add_foot
//                    } else {
//                        return R.layout.item_editstock_detail
//                    }
//                }
                return R.layout.item_editstock_detail
            }

        })
        for (i in titleList.indices) {
            val tv = TextView(activity)
            tv.id = i
            if (i == 0) {
                tv.text = getString(R.string.total_point)
            }
            tv.paint.isFakeBoldText = true
            tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16.toFloat())
            tv.setTextColor(resources.getColor(R.color.mainTextColor))
            val params = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, if (weights.size == 0) 1.toFloat() else weights[i])
            tv.layoutParams = params
            llTotal.addView(tv)
        }
        val tvTotalNum = llTotal.findViewById<TextView>(5)
        val tvSysTotalNum = llTotal.findViewById<TextView>(6)
        val tvProfitLossNum = llTotal.findViewById<TextView>(7)
        val tvProfitLossAmount = llTotal.findViewById<TextView>(9)
        adapter!!.onNumChangeListener = object : EditStockTakingDetailAdapter.OnNumChangeListener {
            override fun onNumChange(totalNum: Long, sysTotalNum: Int, profitLossAmount: String) {
                tvTotalNum.text = totalNum.toString()
                tvSysTotalNum.text = sysTotalNum.toString()
                tvProfitLossNum.text = (totalNum - sysTotalNum).toString()
                tvProfitLossAmount.text = profitLossAmount
            }
        }
        adapter!!.stockTaking = stockTaking
        adapter!!.textWeights = weights
        adapter!!.onAddClickListener = this
        rvList.adapter = adapter
        adapter!!.updateTotal()
    }

    override fun initListener() {
        tvCancel.setOnClickListener(this)
        tvSave.setOnClickListener(this)
        tvDelete.setOnClickListener(this)
        tvSaveAndIn.setOnClickListener(this)
        tvAdd.setOnClickListener(this)
        tvScanAdd.setOnClickListener(this)
    }

    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!! * 9 / 10
    }

    override fun getHeight(): Int {
        return DisplayManager.getScreenHeight()!! * 86 / 100
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSelectedProductEvent(event: SelectedProductEvent) {
        val spList = ArrayList<StockTaking.StockProductInfo>()
        for (p in event.products) {
            val sp = StockTaking.StockProductInfo(1, p.buyingPrice!!, p.goodsName, p.productBarCode,
                    p.productId, p.specificationsValueNames, p.productNumber!!, "--", p.goodsUnitName)
            spList.add(sp)
        }
        adapter!!.updateData(spList)
    }

}


