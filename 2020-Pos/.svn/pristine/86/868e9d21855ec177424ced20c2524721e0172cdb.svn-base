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
import com.epro.pos.mvp.contract.EditStockGoodsPutinDetailContract
import com.epro.pos.mvp.model.bean.ProductDetail
import com.epro.pos.mvp.model.bean.StockGoodsPutin
import com.epro.pos.mvp.presenter.EditStockGoodsPutinDetailPresenter
import com.epro.pos.ui.activity.ScanActivity
import com.epro.pos.ui.adapter.EditStockGoodsPutinDetailAdapter
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.*
import com.mike.baselib.view.CommonDialog
import com.mike.baselib.view.recyclerview.MultipleType
import kotlinx.android.synthetic.main.popup_edit_stock_goods_putin_detail.*
import kotlinx.android.synthetic.main.popup_edit_stock_goods_putin_detail.llTitle
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions


class EditStockGoodsPutinDetailPopup : BaseDialogFragment<EditStockGoodsPutinDetailContract.View, EditStockGoodsPutinDetailPresenter>(), EditStockGoodsPutinDetailContract.View, View.OnClickListener, EditStockGoodsPutinDetailAdapter.OnAddClickListener {
    override fun onAddStockPutinSuccess() {
        if (stockStatus == PosConst.STOCK_STATUS_OUT) {
            toast(getString(R.string.saved_successfully))
        } else {
            toast(getString(R.string.storage_success))
        }
        EventBus.getDefault().post(RefreshShowListUIEvent())
        dismiss()
    }

    override fun onUpdateStockPutinSuccess() {
        if (stockStatus == PosConst.STOCK_STATUS_IN) {
            toast(getString(R.string.saved_successfully))
        } else {
            toast(getString(R.string.storage_success))
        }
        EventBus.getDefault().post(RefreshShowListUIEvent())
        dismiss()
    }

    override fun onDeleteStockPutinSuccess() {
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
        val spList = ArrayList<StockGoodsPutin.StockProductInfo>()
        val sp = StockGoodsPutin.StockProductInfo(productDetail.buyingPrice, productDetail.goodsName, productDetail.goodsUnitName, productDetail.productBarCode,
                productDetail.specificationsValueNames, productDetail.productId, 1, 1, 1)
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
                        mPresenter.deleteStockPutin(stockGoodsPutin!!.stockCode, PosConst.DELETE_STOCK_PUTIN)
                    }
                })
                .create()
                .show()
    }


    var stockStatus = PosConst.STOCK_STATUS_OUT
    private fun showSaveStockBillDialog(isCancel: Boolean = false) {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.language_save))
                .setContent(getString(R.string.page_content_has_been_modified))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        if (adapter!!.mData.isEmpty()) {
                            toast(getString(R.string.pls_choose_products))
                            return
                        }
                        if (adapter!!.checkStockNumZero()) {
                            toast(getString(R.string.input_stock_quantity_cannot_be_0))
                            return
                        }
                        val remark = if (etRemark.text.toString().isEmpty()) null else etRemark.text.toString()
                        //adapter!!.mData.removeAt(adapter!!.mData.size - 1)
                        stockStatus = PosConst.STOCK_STATUS_OUT
                        if (stockGoodsPutin == null) {
                            mPresenter.addStockPutin(PosConst.STOCK_STATUS_OUT, remark, adapter!!.mData, PosConst.ADD_STOCK_PUTIN)
                        } else {
                            mPresenter.updateStockPutin(PosConst.STOCK_STATUS_OUT, stockGoodsPutin!!.stockCode, remark, adapter!!.mData, PosConst.UPDATE_STOCK_PUTIN)
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
                        // adapter!!.mData.removeAt(adapter!!.mData.size - 1)
                        val remark = if (etRemark.text.toString().isEmpty()) null else etRemark.text.toString()
                        stockStatus = PosConst.STOCK_STATUS_IN
                        if (stockGoodsPutin == null) {
                            mPresenter.addStockPutin(PosConst.STOCK_STATUS_IN, remark, adapter!!.mData, PosConst.ADD_STOCK_PUTIN)
                        } else {
                            mPresenter.updateStockPutin(PosConst.STOCK_STATUS_IN, stockGoodsPutin!!.stockCode, remark, adapter!!.mData, PosConst.UPDATE_STOCK_PUTIN)
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


    @SuppressLint("ResourceType")
    override fun onClick(p0: View?) {
        when (p0) {
            tvCancel -> {
                if (adapter!!.mData.isEmpty()
                        || (stockGoodsPutin != null && stockGoodsPutin!!.stockStatus == PosConst.STOCK_STATUS_IN) ||
                        !adapter!!.checkChanged()) {
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
                if (adapter!!.checkStockNumZero()) {
                    return
                }
                if( adapter!!.checkPrice()){
                    return
                }
                val remark = if (etRemark.text.toString().isEmpty()) null else etRemark.text.toString()
                if (stockGoodsPutin == null) {
                    mPresenter.addStockPutin(PosConst.STOCK_STATUS_OUT, remark, adapter!!.mData, PosConst.ADD_STOCK_PUTIN)
                } else {
                    mPresenter.updateStockPutin(PosConst.STOCK_STATUS_OUT, stockGoodsPutin!!.stockCode, remark, adapter!!.mData, PosConst.UPDATE_STOCK_PUTIN)
                }
            }
            tvSaveAndIn -> {
                if (adapter!!.mData.isEmpty()) {
                    toast(getString(R.string.pls_choose_products))
                    return
                }
                if (adapter!!.checkStockNumZero()) {
                    return
                }
                if( adapter!!.checkPrice()){
                    return
                }
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
        fun newInstance(stockGoodsPutin: StockGoodsPutin?): EditStockGoodsPutinDetailPopup {
            val args = Bundle()
            args.putString(AppBusManager.ext_createJsonKey(StockGoodsPutin::class.java), AppBusManager.toJson(stockGoodsPutin))
            val fragment = EditStockGoodsPutinDetailPopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): EditStockGoodsPutinDetailPopup {
            return newInstance(null)
        }
    }

    override fun getPresenter(): EditStockGoodsPutinDetailPresenter {
        return EditStockGoodsPutinDetailPresenter()
    }


    override fun getContentLayoutId(): Int {
        return R.layout.popup_edit_stock_goods_putin_detail
    }

    override fun initData() {

    }

    val unit = AppBusManager.getAmountUnit()
    var titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number),AppContext.getInstance().getString(R.string.barcode),AppContext.getInstance(). getString(R.string.productname), AppContext.getInstance().getString(R.string.unit), AppContext.getInstance().getString(R.string.specification), AppContext.getInstance().getString(R.string.creat_purchase_price)+"($unit)", AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.storage_method))
    var weights = arrayListOf(1F, 3F, 2F, 1F, 1F, 1F, 1F, 1.5F)
    var stockGoodsPutin: StockGoodsPutin? = null
    var adapter: EditStockGoodsPutinDetailAdapter? = null
    @SuppressLint("ResourceType")
    override fun initView() {
        stockGoodsPutin = AppBusManager.fromJsonWithClassKey(arguments!!, StockGoodsPutin::class.java)
        val products = ArrayList<StockGoodsPutin.StockProductInfo>()
        if (stockGoodsPutin != null && stockGoodsPutin!!.stockStatus == PosConst.STOCK_STATUS_IN) {  //详情模式
            titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number), AppContext.getInstance().getString(R.string.barcode),AppContext.getInstance().getString(R.string.productname), AppContext.getInstance().getString(R.string.unit), AppContext.getInstance().getString(R.string.specification), AppContext.getInstance().getString(R.string.creat_purchase_price)+"($unit)",AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.storage_method))
            weights = arrayListOf(1F, 3F, 2F, 1F, 1F, 1F, 1F, 1.5F)
            tvBusinessNo.text = getString(R.string.business_ticket_number)+ stockGoodsPutin?.stockCode
            tvGoodsStatus.text = getString(R.string.product_status)+ PosBusManager.getGoodsStatusText(stockGoodsPutin?.stockStatus!!)
            products.addAll(stockGoodsPutin?.stockInfoList!!)
            tvTitle.text = getString(R.string.stock_putin_detail)
            llLeft.visibility = View.GONE
            llRight.gravity = Gravity.START
            tvRemark.text = stockGoodsPutin?.stockDesc
        } else {
            titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number), AppContext.getInstance().getString(R.string.barcode),AppContext.getInstance(). getString(R.string.productname), AppContext.getInstance().getString(R.string.unit), AppContext.getInstance().getString(R.string.specification), AppContext.getInstance().getString(R.string.creat_purchase_price)+"($unit)", AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.storage_method), AppContext.getInstance().getString(R.string.operating))
            weights = arrayListOf(1F, 3F, 2F, 1F, 1F, 1F, 1F, 1.5F, 1F)
            llBottom.visibility = View.VISIBLE
            etRemark.visibility = View.VISIBLE
            tvTitle.text = getString(R.string.edit_stock_putin)
            if (stockGoodsPutin == null) {
                tvTitle.text = getString(R.string.add_storage)
                tvDelete.visibility = View.GONE
            } else {
                tvBusinessNo.text = getString(R.string.business_ticket_number) + stockGoodsPutin?.stockCode
                tvGoodsStatus.text = getString(R.string.product_status) + PosBusManager.getGoodsStatusText(stockGoodsPutin?.stockStatus!!)
                products.addAll(stockGoodsPutin?.stockInfoList!!)
                etRemark.setText(stockGoodsPutin?.stockDesc)
            }
            //products.add(StockGoodsPutin.StockProductInfo())
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
        adapter = EditStockGoodsPutinDetailAdapter(activity!!, products, object : MultipleType<StockGoodsPutin.StockProductInfo> {
            override fun getLayoutId(item: StockGoodsPutin.StockProductInfo, position: Int): Int {
//                if (stockGoodsPutin == null) {
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
        val tvTotalNum = llTotal.findViewById<TextView>(6)
        adapter!!.onNumChangeListener = object : EditStockGoodsPutinDetailAdapter.OnNumChangeListener {
            override fun onNumChange(totalNum: Long) {
                tvTotalNum.text = totalNum.toString()
            }
        }
        adapter!!.stockGoodsPutin = stockGoodsPutin
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
        val spList = ArrayList<StockGoodsPutin.StockProductInfo>()
        for (p in event.products) {
            val sp = StockGoodsPutin.StockProductInfo(p.buyingPrice!!, p.goodsName, p.goodsUnitName, p.productBarCode,
                    p.specificationsValueNames, p.productId, 1, 1, 1, p.goodsBinding)
            spList.add(sp)
        }
        adapter!!.updateData(spList)
    }

}


