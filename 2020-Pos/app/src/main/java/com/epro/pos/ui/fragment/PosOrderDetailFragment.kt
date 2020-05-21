package com.epro.pos.ui.fragment;

import android.annotation.SuppressLint
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.widget.LinearLayoutManager
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.epro.pos.R
import com.epro.pos.mvp.contract.PosOrderDetailContract
import com.epro.pos.mvp.model.bean.GetPosOrderDetailBean
import com.epro.pos.mvp.model.bean.PosTradeOrder
import com.epro.pos.mvp.presenter.PosOrderDetailPresenter
import com.epro.pos.ui.adapter.PosOrderDetailGoodsAdapter
import com.epro.pos.ui.view.PhoneOrEmailCheckDialog
import com.epro.pos.utils.PosConst
import com.epro.pos.utils.PrintUtils
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.listener.ShopInfoChange
import com.mike.baselib.utils.*
import com.mike.baselib.view.CommonDialog
import com.mike.baselib.view.recyclerview.MultipleType
import kotlinx.android.synthetic.main.fragment_pos_order_detail.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class PosOrderDetailFragment : BaseTitleBarCustomFragment<PosOrderDetailContract.View, PosOrderDetailPresenter>(), PosOrderDetailContract.View, View.OnClickListener {

    override fun onRefundOrderSuccss(result: Any, type: String) {
        showRefundResult(result.toString())
    }

    override fun onClick(p0: View?) {
        when (p0) {
            tvRefund -> {
                val products = ArrayList<GetPosOrderDetailBean.Product>()
                var totalCount = 0
                for (i in adapter!!.mData.indices) {
                    if (i != adapter!!.mData.size - 1) {
                        val p = adapter!!.mData[i]
                        totalCount += p.preRefundCount!!
                        if (p.preRefundCount!! > 0) {
                            products.add(p)
                        }
                    }
                }
                if (totalCount == 0) {
                    showAlert(getString(R.string.please_enter_the_number_of))
                } else {
                    showRefundConfirm(products)
                }
            }
            tvAllRefund -> {
                showRefundConfirm(null)
            }
            //打印
            tvPrint->{
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
                if (mBluetoothAdapter!=null){
                    var devices =  mBluetoothAdapter?.bondedDevices
                    for (index in devices!!){
                        if (index.bluetoothClass.deviceClass == PRINT_TYPE){
                            bluetoothDevices.add(index)
                        }
                    }
                    if (bluetoothDevices.size==0){
                        showPrintDialog()
                        return
                    }else{
                        ConnectThread(mBluetoothAdapter!!.getRemoteDevice(bluetoothDevices[bluetoothDevices.size-1].address)).start()
                    }
                }else{
                    toast(getString(R.string.device_not_bluetooth))
                }
            }
        }

    }


    //连接打印
    private inner class ConnectThread(device: BluetoothDevice) : Thread() {
        init {
            try {
                mmSocket = device.createRfcommSocketToServiceRecord(uuid)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        override fun run() {
            //连接socket
            if (!mmSocket?.isConnected!!){
                logTools.t("YB").d(" !isConnected ")
                try {
                    mmSocket?.connect()
                }catch (e: IOException ){
                    try {
                        mmSocket = mBluetoothAdapter!!.getRemoteDevice(bluetoothDevices[bluetoothDevices.size-1].address).javaClass.getMethod("createRfcommSocket", Int::class.java).invoke(mBluetoothAdapter!!.getRemoteDevice(bluetoothDevices[bluetoothDevices.size-1].address),1) as BluetoothSocket?
                        mmSocket?.connect()
                        logTools.t("YB").d("Connected")
                    }catch (e2: Exception){
                        logTools.t("YB").d(" Couldn't establish Bluetooth connection! ")
                    }
                }
            }
            if(mmSocket?.isConnected!!){
                //连接成功获取输出流
                outPutStream = mmSocket?.outputStream
                printmText(productsBean)
            }
        }
    }

    fun printmText(bean:GetPosOrderDetailBean.Result?) {
        var size = bean?.products?.size
        var totalPrice  = 0.toDouble()
        var discountPrice =  0.toDouble()
        selectCommand(PrintUtils.RESET)
        selectCommand(PrintUtils.ALIGN_CENTER)
        selectCommand(PrintUtils.LINE_SPACING_DEFAULT)
        printText( AppBusManager.getShopName()+ "\n")
        printText("  \n")
        selectCommand(PrintUtils.NORMAL)
        selectCommand(PrintUtils.ALIGN_LEFT)
        printText(getString(R.string.print_order_num)+" "+order!!.orderSn+"\n")
        printText(getString(R.string.cashier_person)+" "+bean?.cashierName+"\n")
        selectCommand(PrintUtils.ALIGN_RIGHT)
        printText(getString(R.string.time)+" "+getDateTime()+"\n")
        selectCommand(PrintUtils.NORMAL)
        selectCommand(PrintUtils.ALIGN_LEFT)
        printText("- - - - - - - - - - - - - - -\n")
        selectCommand(PrintUtils.NORMAL)
        selectCommand(PrintUtils.ALIGN_LEFT)
        printText(PrintUtils.printFourData(getString(R.string.print_title_product_name),getString(R.string.print_title_product_num),getString(R.string.print_title_product_sale),getString(R.string.print_title_product_subtotal)+"\n"))
        printText("- - - - - - - - - - - - - - -\n")
        if (size == 0){
            printText(PrintUtils.printFourData("","","",""+"\n"))
            printText(getString(R.string.print_end_thank)+"\n")
            printText("  \n")
            return
        }else {
          var i =1
          while (i<=size!!){
              printText(PrintUtils.printFourData(bean!!.products[i-1].productDesc,bean!!.products[i-1].productCount.toString(),bean.products[i-1].salePrice,bean.products[i-1].discountPrice+"\n"))
              totalPrice += bean!!.products[i-1].totalPrice.toDouble()
              discountPrice +=bean!!.products[i-1].discountPrice.toDouble()
              i++
            }
        }
        printText("- - - - - - - - - - - - - - -\n")
        //优惠
        printText(PrintUtils.printTwoData(getString(R.string.print_offer),""+(totalPrice-discountPrice)+"\n"))
        //合计
        printText(PrintUtils.printTwoData(getString(R.string.print_all_count)+size,getString(R.string.print_should_pay)+totalPrice+"\n"))
        var mPayStyle:String?=""
        if (order!!.payType ==1 ) mPayStyle = getString(R.string.cash) else if (order!!.payType == 2) mPayStyle = getString(R.string.zhifubao)
        printText(PrintUtils.printTwoData(getString(R.string.print_pay_style)+mPayStyle,"\n"))
        printText("- - - - - - - - - - - - - - -\n")
        selectCommand(PrintUtils.ALIGN_CENTER)
        selectCommand(PrintUtils.LINE_SPACING_DEFAULT)
        printText(getString(R.string.print_end_thank)+"\n")
        printText("  \n")
    }
    private fun getDateTime():String {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = Date(System.currentTimeMillis())
        var time = simpleDateFormat.format(date)
        return time
    }

    //提示弹窗
    private fun showAlert(text: String) {
        CommonDialog.Builder(activity!!)
                .setContent(text)
                .setCancelIsVisibility(false)
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()
    }

    private fun showRefundResult(text: String) {
        val view =LayoutInflater.from(activity!!).inflate(R.layout.dialog_refund,null)
        val tvContent2=view.findViewById<TextView>(R.id.tvContent2)
        tvContent2.text=AppBusManager.getAmountUnit()+text
        CommonDialog.Builder(activity!!)
                .setCustomContentView(view)
                .setCancelIsVisibility(false)
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        removeThis()
                    }
                })
                .create()
                .show()
    }

    private fun showRefundConfirm(products: ArrayList<GetPosOrderDetailBean.Product>?) {
        var refundAmount = 0.toDouble()
        if (products == null) {
            refundAmount = order!!.orderActualAmount.toDouble()
        } else {
            for (p in products) {
                refundAmount += p.discountPrice.toDouble()*p.preRefundCount!!
            }
        }
        CommonDialog.Builder(activity!!)
                .setTitle(if (products == null) getString(R.string.return_of_entire_order) else getString(R.string.returns))
                .setContent(getString(R.string.order_num) + order!!.orderSn+getString(R.string.confirm_returns))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        mPresenter.refundOrder(order!!.orderSn, products, if (products == null) PosConst.REFUND_WHOLE_ORDER else PosConst.REFUND_PART_ORDER)
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()


    }

    /**
     * 连接为客户端
     */
    var mmSocket: BluetoothSocket? = null
    var mBluetoothAdapter: BluetoothAdapter? = null
    var uuid: UUID?=null
    //打印的输出流
    var outPutStream: OutputStream? = null
    val exceptionCod2 = 200
    var bluetoothDevices = java.util.ArrayList<BluetoothDevice>()

    companion object {
        const val TAG = "getPosOrderDetail"
        const val PRINT_TYPE = 1664
        fun newInstance(order: PosTradeOrder): PosOrderDetailFragment {
            val args = Bundle()
            args.putString(ext_createJsonKey(PosTradeOrder::class.java), AppBusManager.toJson(order))
            val fragment = PosOrderDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getPresenter(): PosOrderDetailPresenter {
        return PosOrderDetailPresenter()
    }

    var productsBean :GetPosOrderDetailBean.Result?=null
    override fun onGetPosOrderDetailSuccess(result: GetPosOrderDetailBean.Result) {
        if (result.products.isEmpty()) {
            toast(getString(R.string.product_is_empty))
            return
        }
        val products = ArrayList<GetPosOrderDetailBean.Product>()
        for (p in result.products) {
            p.preRefundCount = 0
        }
        products.addAll(result.products)
        products.add(result.products[0])
        adapter!!.orderDetail=result
        adapter!!.setData(products)
        productsBean = result
    }


    override fun layoutContentId(): Int {
        return R.layout.fragment_pos_order_detail
    }

    override fun initData() {

    }

    var order: PosTradeOrder? = null
    val unit = AppBusManager.getAmountUnit()
    var titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number), AppContext.getInstance().getString(R.string.barcode), AppContext.getInstance().getString(R.string.productname), AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.discount_percent_sign), AppContext.getInstance().getString(R.string.print_title_product_sale)+"($unit)", AppContext.getInstance().getString(R.string.print_title_product_subtotal)+"($unit)", AppContext.getInstance().getString(R.string.return_nums), AppContext.getInstance().getString(R.string.return_quantity))
    var weights = arrayListOf(1F, 2F, 2F, 1F, 1F, 1F, 1F, 1F, 1.5F)
    var adapter: PosOrderDetailGoodsAdapter? = null
    override fun initView() {
        super.initView()
        setHaveBackView(true)
        getLeftTitleView().text = getString(R.string.trade_query)
        getTitleView().text = getString(R.string.order_detail)
        order = AppBusManager.fromJsonWithClassKey(arguments!!, PosTradeOrder::class.java)
        if(order!!.orderType==PosConst.ORDER_TYPE_REFUND){
            tvRefund.visibility=View.GONE
            tvAllRefund.visibility=View.GONE
            titleList = arrayListOf(AppContext.getInstance().getString(R.string.serial_number), AppContext.getInstance().getString(R.string.barcode), AppContext.getInstance().getString(R.string.productname), AppContext.getInstance().getString(R.string.print_title_product_num), AppContext.getInstance().getString(R.string.discount_percent_sign), AppContext.getInstance().getString(R.string.print_title_product_sale)+"($unit)", AppContext.getInstance().getString(R.string.print_title_product_subtotal)+"($unit)")
            weights = arrayListOf(1F, 2F, 2F, 1F, 1F, 1F, 1F)
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

        rvOrderGoods.layoutManager = LinearLayoutManager(activity!!)
        adapter = PosOrderDetailGoodsAdapter(activity!!, ArrayList(), object : MultipleType<GetPosOrderDetailBean.Product> {
            override fun getLayoutId(item: GetPosOrderDetailBean.Product, position: Int): Int {
                return if (position == adapter!!.mData.size - 1) {
                    R.layout.item_total_amount
                } else {
                    R.layout.item_pos_orderdetail_goods
                }
            }

        })
        adapter!!.textWeights = weights
        adapter!!.order=order
        rvOrderGoods.adapter = adapter

    }

    override fun initListener() {
        tvRefund.setOnClickListener(this)
        tvAllRefund.setOnClickListener(this)
        tvPrint.setOnClickListener(this)
    }

    override fun lazyLoad() {
        mPresenter.getPosOrderDetail(order!!.orderSn, PosConst.GET_POS_ORDER_DETAIL)
    }

    /**
     * 打印文字
     *
     * @param text 要打印的文字
     */
    fun printText(text: String) {
        try {
            val data = text.toByteArray(charset("gbk"))
            outPutStream!!.write(data, 0, data.size)
            outPutStream!!.flush()
        } catch (e: IOException) {
            Toast.makeText(activity,getString(R.string.send_failed), Toast.LENGTH_SHORT).show();
            e.printStackTrace()
        }

    }

    /**
     * 设置打印格式
     *
     * @param command 格式指令
     */
    fun selectCommand(command: ByteArray) {
        try {
            outPutStream!!.write(command)
            outPutStream!!.flush()
        } catch (e: IOException) {
            Toast.makeText(activity, getString(R.string.send_failed), Toast.LENGTH_SHORT).show();
            e.printStackTrace()
        }

    }
    //在打印异常时更新ui
    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                exceptionCod2->{
                    toast(getString(R.string.print_failed))
                }
            }
        }
    }

    //尚未连接打印机弹框
    private fun showPrintDialog() {
        PhoneOrEmailCheckDialog.Builder(activity!!)
                .setTitle(getString(R.string.title_prompt))
                .setContent(getString(R.string.no_printer))
                .setConfirmText(getString(R.string.pls_confirm_pop))
                .setOnConfirmListener(object : PhoneOrEmailCheckDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    fun shopIdChange(event: ShopInfoChange){
        AppBusManager.setShopName(event.name)
    }
}


