package com.epro.pos.ui.fragment

import android.annotation.SuppressLint
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.bigkoo.pickerview.listener.CustomListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.TimePickerView
import com.epro.pos.R
import com.epro.pos.mvp.contract.CashierReconciliationContract
import com.epro.pos.mvp.model.bean.CashierReconciliationBean
import com.epro.pos.mvp.presenter.CashierReconciliationPresenter
import com.epro.pos.ui.MainActivity
import com.epro.pos.ui.login.LoginActivity
import com.epro.pos.ui.view.PhoneOrEmailCheckDialog
import com.epro.pos.utils.PosConst
import com.epro.pos.utils.PrintUtils
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.LocaleManager
import com.mike.baselib.view.CommonDialog
import kotlinx.android.synthetic.main.fragment_cashier_reconciliation.*
import com.mike.baselib.utils.toast
import java.io.IOException
import java.io.OutputStream
import java.lang.Exception
import java.lang.StringBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class CashierReconciliationFragment: BaseTitleBarCustomFragment<CashierReconciliationContract.View, CashierReconciliationPresenter>(),CashierReconciliationContract.View, View.OnClickListener {

    override fun changeLogout() {
        mPresenter.ExitLogout(PosConst.LOGOUT)
    }

    override fun onExitLogoutSuccess() {
        clearData()
        LoginActivity.launchForReslut(activity!!, MainActivity.REQUEST_CODE_FOR_LOGIN_RESULT)
        toast(getString(R.string.logout_success))
    }

    var printBean:CashierReconciliationBean.Result?=null
    override fun onCashierReconciliationSucess(result: CashierReconciliationBean.Result) {
        logTools.t("YB").d("onCashierReconciliationSucess ")
        tvStore.text = "["+result.shopId+"]"+result.shopName                   //营业门店
        tvName.text =  "["+result.cashierId+"]"+result.cashierName             //收银员
        tvDate.text = result.createTime                                        //对账日期
        tvFirstOrder.text = result.firstSaleTime                               //首笔时间
        tvEndOrder.text = result.lastSaleTime                                  //末笔时间
        tvALLOrder.text = result.totalCount.toString()                         //笔数
        visibilitySaleView(result)
        visibilityLineView(result)
        tvALLCount.text = result.totalCount.toString()                             //收入总笔数
        tvAll.text = result.totalAmount.toString()                                 //合计总收入
        tvInComeNum.text = result.saleTotalCount.toString()                        //收入笔数
        tvInComeMoney.text = result.saleTotalAmount.toString()                     //收入金额
        tvExpend.text = result.backTotalCount.toString()                           //支出笔数
        tvExpendMoney.text = result.backTotalAmount.toString()                     //支出金额
        printBean = result
    }

    //销售资金view显示隐藏
    private fun visibilitySaleView(result: CashierReconciliationBean.Result) {
        if (0==result.cashSaleCount.toInt()&&0==result.cashBackCount.toInt()){
            logTools.t("YB").d("cashSaleCount "+result.cashSaleCount.toString()+" cashBackCount :"+result.cashBackCount.toString())
            llCashMoney.visibility = View.GONE
            llCashMoney2.visibility = View.GONE
            llCash.visibility = View.GONE
        }else{
            llCashMoney.visibility = View.VISIBLE
            llCashMoney2.visibility = View.VISIBLE
            llCash.visibility = View.VISIBLE
            tvSaleNum.text = result.cashSaleCount.toString()                        //现金销售笔数
            tvSaleMoney.text = result.cashSaleAmount.toString()                     //现金销售金额
            tvReturn.text = result.cashBackCount.toString()                         //现金退货笔数
            tvReturnMoney.text = result.cashBackAmount.toString()                    //现金退货金额
        }
        if (0==result.bankCardSaleCount.toInt()&&0==result.bankCardBackCount.toInt()){
            logTools.t("YB").d("bankCardSaleCount "+result.bankCardSaleCount.toString()+" bankCardBackCount :"+result.bankCardBackCount.toString())
            llBankCard.visibility = View.GONE
            llCard.visibility = View.GONE
            llBankCard2.visibility = View.GONE
        }else{
            llBankCard.visibility = View.VISIBLE
            llCard.visibility = View.VISIBLE
            llBankCard2.visibility = View.VISIBLE
            tvSaleBank.text = result.bankCardSaleCount.toString()                    //银行卡销售笔数
            tvSaleBankMoney.text = result.bankCardSaleAmount.toString()              //银行卡销售金额
            tvSaleBankBack.text = result.bankCardBackCount.toString()                //银行卡退货笔数
            tvBankMoneyBack.text = result.bankCardBackAmount.toString()               //银行卡退货金额
        }
        if (0==result.wxSaleCount.toInt()&&0==result.wxBackCount.toInt()){
            logTools.t("YB").d("wxSaleCount "+result.wxSaleCount.toString()+" wxBackCount :"+result.wxBackCount.toString())
            llWxSale.visibility = View.GONE
            llWx.visibility = View.GONE
            llWxSale2.visibility = View.GONE
        }else{
            llWxSale.visibility = View.VISIBLE
            llWx.visibility = View.VISIBLE
            llWxSale2.visibility = View.VISIBLE
            tvSalewx.text = result.wxSaleCount.toString()                             //微信销售笔数
            tvSalewxMoney.text = result.wxSaleAmount.toString()                       //微信销售金额
            tvSalewxBack.text = result.wxBackCount.toString()                          //微信退货笔数
            tvwxMoneyBack.text = result.wxBackAmount.toString()                        //微信退货金额
        }

        if (0==result.zfbSaleCount.toInt()&&0==result.zfbBackCount.toInt()){
            logTools.t("YB").d("zfbSaleCount "+result.zfbSaleCount.toString()+" zfbBackCount :"+result.zfbBackCount.toString())
            llZfbSale.visibility = View.GONE
            llZfb.visibility = View.GONE
            llZfbSale2.visibility = View.GONE
        }else{
            llZfbSale.visibility = View.VISIBLE
            llZfb.visibility = View.VISIBLE
            llZfbSale2.visibility = View.VISIBLE
            tvSalezfb.text = result.zfbSaleCount.toString()                            //支付宝销售笔数
            tvSalezfbMoney.text = result.zfbSaleAmount.toString()                      //支付宝销售金额
            tvSalezfbBack.text = result.zfbBackCount.toString()                        //支付宝退货笔数
            tvzfbMoneyBack.text = result.zfbBackAmount.toString()                      //支付宝退货金额
        }

        if (0==result.faceSaleCount.toInt()&&0==result.faceBackCount.toInt()){
            logTools.t("YB").d("faceSaleAmount "+result.faceSaleAmount.toString()+" faceBackCount :"+result.faceBackCount.toString())
            llFaceSale.visibility = View.GONE
            llFace.visibility = View.GONE
            llFaceSale2.visibility = View.GONE
        }else{
            llFaceSale.visibility = View.VISIBLE
            llFace.visibility = View.VISIBLE
            llFaceSale2.visibility = View.VISIBLE
            tvSaleFace.text = result.faceSaleCount.toString()                            //刷脸销售笔数
            tvSaleFaceMoney.text = result.faceSaleAmount.toString()                      //刷脸销售金额
            tvSaleFaceBack.text = result.faceBackCount.toString()                        //刷脸退货笔数
            tvFaceMoneyBack.text = result.faceBackCount.toString()                       //刷脸退货金额
        }
    }


    private fun visibilityLineView(result: CashierReconciliationBean.Result) {
        var flag = 0==result.cashSaleCount.toInt()&&0==result.cashBackCount.toInt()&&0==result.bankCardSaleCount.toInt()&&0==result.bankCardBackCount.toInt()&&0==result.wxSaleCount.toInt()&&0==result.wxBackCount.toInt()&&
                0==result.zfbSaleCount.toInt()&&0==result.zfbBackCount.toInt() && 0==result.faceSaleCount.toInt()&&0==result.faceBackCount.toInt()
        if (flag){
            lineView.visibility = View.GONE
        }else{
            lineView.visibility = View.VISIBLE
        }
    }

    override fun onClick(v: View?) {
       when(v){
           //开始日期
           tvStartDate->{
                 showDateTime(dateArray,START_TIME_DATE)
                 pvCustomLunar!!.show()
           }
           //开始时间
           tvStartTime->{
               showDateTime(timeArray, START_TIME_HOUR)
               pvCustomLunar!!.show()
           }
           //结束日期
           tvEndDate->{
               showDateTime(dateArray, END_TIME_DATE)
               pvCustomLunar!!.show()
           }
           //结束时间
           tvEndTime->{
               showDateTime(timeArray, END_TIME_HOUR)
               pvCustomLunar!!.show()
           }

           //对账
           tvCashier->{
               var startDate =tvStartDate.text.toString().trim()
               var startTime = tvStartTime.text.toString().trim()
               var endDate = tvEndDate.text.toString().trim()
               var endTime = tvEndTime.text.toString().trim()
               mPresenter.startCashier(PosConst.CASHIER_RECONCILIATION, "$startDate $startTime","$endDate $endTime")
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
                       logTools.t("YB").d("SIZE:"+bluetoothDevices.size)
                       showPrintDialog()
                       return
                   }else{
                       logTools.t("YB").d("SIZE else:"+bluetoothDevices.size)
                       ConnectThread(mBluetoothAdapter!!.getRemoteDevice(bluetoothDevices[bluetoothDevices.size-1].address)).start()
                   }
               }else{
                   toast(getString(R.string.device_not_bluetooth))
               }
           }
           //交班推出系统
           tvOut->{
               showOutDialog()
           }
       }
    }

    /**
     * 连接为客户端
     */
     var mmSocket: BluetoothSocket? = null
     var mBluetoothAdapter:BluetoothAdapter? = null
     var uuid: UUID?=null
    //打印的输出流
    var outPutStream: OutputStream? = null
    val exceptionCod2 = 200
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
                        }catch (e2:Exception){
                            logTools.t("YB").d(" Couldn't establish Bluetooth connection! ")
                        }
                    }
                }

                if(mmSocket?.isConnected!!){
                    //连接成功获取输出流
                    logTools.t("YB").d("isConnected")
                    outPutStream = mmSocket?.outputStream
                    printmText(printBean)
                }
        }
    }

/*    tvInComeNum.text = result.saleTotalCount.toString()                        //收入笔数
    tvInComeMoney.text = result.saleTotalAmount.toString()                     //收入金额
    tvExpend.text = result.backTotalCount.toString()                           //支出笔数
    tvExpendMoney.text = result.backTotalAmount.toString()                     //支出金额*/

    fun printmText(result:CashierReconciliationBean.Result?){
        selectCommand(PrintUtils.RESET)
        selectCommand(PrintUtils.ALIGN_CENTER)
        selectCommand(PrintUtils.LINE_SPACING_DEFAULT)
        printText(getString(R.string.cashier_add_reconciliation)+"\n")
        printText("- - - - - - - - - - - - - - -\n")
        selectCommand(PrintUtils.NORMAL)
        selectCommand(PrintUtils.ALIGN_LEFT)
        printText(businessTitleString!!+" "+"["+result?.shopId+"]"+result?.shopName+"\n")
        var title2 =  personTitleString!!.substring(0,1)+" "+personTitleString!!.substring(2,3)+" "+personTitleString!!.substring(4,5)+" "+
                personTitleString!!.substring(personTitleString!!.length-1,personTitleString!!.length)
        printText(title2!!+" "+"["+result?.cashierId+"]"+result?.cashierName+"\n")
        printText(cashierString!!+" "+result?.createTime+"\n")
        printText(getTitleString(firstOrderTitle)+" "+result?.firstSaleTime+"\n")
        printText(getTitleString(endOrderTitle)+" "+result?.lastSaleTime+"\n")
        printText(getTitleString(pensNumberTitle)+" "+result?.totalCount.toString()+"\n")
        printText("- - - - - - - - - - - - - - -\n")
        if(0!=result?.cashSaleCount!!.toInt()&&0!=result?.cashBackCount!!.toInt()){
            //现金
            printText(getString(R.string.cash_money)+"\n")
            printText(getString(R.string.sales_number)+" "+result?.cashSaleCount.toString()+" "+getString(R.string.saleAmount)+" "+result?.cashSaleAmount.toString()+"\n")
            printText(getString(R.string.number_of_returns)+" "+result?.cashBackCount.toString()+" "+getString(R.string.amount)+" "+result?.cashBackAmount.toString()+"\n")
        }

        if (0!=result?.bankCardSaleCount!!.toInt()&&0!=result?.bankCardBackCount!!.toInt()){
            //银行卡
            printText(getString(R.string.bank_card)+"\n")
            printText(getString(R.string.sales_number)+" "+result?.bankCardSaleCount.toString()+" "+getString(R.string.saleAmount)+" "+result?.bankCardSaleAmount.toString()+"\n")
            printText(getString(R.string.number_of_returns)+" "+result?.bankCardBackCount.toString()+" "+getString(R.string.amount)+" "+result?.bankCardBackAmount.toString()+"\n")
        }
        if (0!=result?.wxSaleCount.toInt()&&0!=result?.wxBackCount.toInt()){
            //微信
            printText(getString(R.string.wx_sale_count)+"\n")
            printText(getString(R.string.sales_number)+" "+result?.wxSaleCount.toString()+" "+getString(R.string.saleAmount)+" "+result?.wxSaleAmount.toString()+"\n")
            printText(getString(R.string.number_of_returns)+" "+result?.wxBackCount.toString()+" "+getString(R.string.amount)+" "+result?.wxBackAmount.toString()+"\n")
        }
        if (0!=result?.zfbSaleCount.toInt()&&0!=result?.zfbBackCount.toInt()){
            //支付宝
            printText(getString(R.string.zfb_sale_count)+"\n")
            printText(getString(R.string.sales_number)+" "+result?.zfbSaleCount.toString()+" "+getString(R.string.saleAmount)+" "+result?.zfbSaleAmount.toString()+"\n")
            printText(getString(R.string.number_of_returns)+" "+result?.zfbBackCount.toString()+" "+getString(R.string.amount)+" "+result?.zfbBackAmount.toString()+"\n")
        }
        if ((0!=result?.cashSaleCount!!.toInt()&&0!=result?.cashBackCount!!.toInt())||(0!=result?.bankCardSaleCount!!.toInt()&&0!=result?.bankCardBackCount.toInt())||(0!=result?.wxSaleCount!!.toInt()&&0!=result?.wxBackCount.toInt())||(0!=result?.zfbSaleCount.toInt()&&0!=result?.zfbBackCount.toInt())){
            printText("- - - - - - - - - - - - - - -\n")
        }

        printText(getString(R.string.total_income)+"   "+getString(R.string.total_number_of_pens)+" "+result?.totalCount+"\n")
        printText(getString(R.string.number_of_income)+" "+result?.saleTotalCount.toString()+getString(R.string.totalMoney)+" "+result?.saleTotalAmount.toString() +"\n")
        printText(getString(R.string.number_of_expenditures)+" "+result?.backTotalCount.toString()+getString(R.string.totalMoney)+" "+result?.backTotalAmount.toString()+"\n")
        printText(getString(R.string.total_list)+" "+result?.totalAmount+"\n")
        printText("- - - - - - - - - - - - - - -\n")
        printText(getString(R.string.cashier_list_end)+"\n")
    }

    fun getTitleString(v:View):String{
        var sb = StringBuilder()
        var homeTitle = businessTitleString!!.length
        when(v){
            firstOrderTitle->{
                var start = firstTitle!!.substring(0,1)
                sb.append(start)
                for (i in  0 until homeTitle-2){
                    sb.append(" ")
                }
                sb = sb.append(firstTitle!!.substring(firstTitle!!.length-3,firstTitle!!.length-2)+" "+firstTitle!!.substring(firstTitle!!.length-1,firstTitle!!.length))
            }
            endOrderTitle->{
                var start = endOrderTitleString!!.substring(0,1)
                sb.append(start)
                for (i in  0 until homeTitle-2){
                    sb.append(" ")
                }
                sb = sb.append(endOrderTitleString!!.substring(endOrderTitleString!!.length-3,endOrderTitleString!!.length-2)+" "+endOrderTitleString!!.substring(endOrderTitleString!!.length-1,endOrderTitleString!!.length))
            }
            pensNumberTitle->{
                var start = pensNumberString!!.substring(0,1)
                sb.append(start)
                for (i in  0 until homeTitle-2){
                    sb.append(" ")
                }
                sb = sb.append(pensNumberString!!.substring(pensNumberString!!.length-3,pensNumberString!!.length-2)+" "+pensNumberString!!.substring(pensNumberString!!.length-1,pensNumberString!!.length))
            }
        }
        return sb.toString()
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
            Toast.makeText(activity, getString(R.string.send_failed), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(activity, getString(R.string.send_failed), Toast.LENGTH_SHORT).show()
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

    private val timeArray = booleanArrayOf(false, false, false, true, true, true)
    private val dateArray = booleanArrayOf(true, true, true, false,false,false)
    var bluetoothDevices =  ArrayList<BluetoothDevice>()
    companion object {
        const val TAG = "CashierReconciliationFragment"
        const val START_TIME_DATE = 1
        const val START_TIME_HOUR = 2
        const val END_TIME_DATE = 3
        const val END_TIME_HOUR = 4
        const val PRINT_TYPE = 1664
        fun newInstance(str: String): CashierReconciliationFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = CashierReconciliationFragment()
            fragment.setArguments(args)
            return fragment
        }
        fun newInstance(): CashierReconciliationFragment {
            return newInstance("")
        }
    }

    override fun layoutContentId(): Int {
       return R.layout.fragment_cashier_reconciliation
    }


    override fun initView() {
        super.initView()
        getTitleView().text = getString(R.string.cashier_reconciliation_list)
        addSpaceTitle()
        var time = AppBusManager.getLoginTime()
        var endTime =getCurrentTime()
        if (!time.isEmpty()&&!endTime.isEmpty()){
            tvStartDate.text = time.split(" ")[0]
            tvStartTime.text = time.split(" ")[1]
            tvEndDate.text = endTime.split(" ")[0]
            tvEndTime .text = endTime.split(" ")[1]
        }
        mPresenter.startCashier(PosConst.CASHIER_RECONCILIATION,time,endTime)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
            var time = AppBusManager.getLoginTime()
            var endTime =getCurrentTime()
            if (!time.isEmpty()&&!endTime.isEmpty()){
                tvStartDate.text = time.split(" ")[0]
                tvStartTime.text = time.split(" ")[1]
                tvEndDate.text = endTime.split(" ")[0]
                tvEndTime .text = endTime.split(" ")[1]
            }
            mPresenter.startCashier(PosConst.CASHIER_RECONCILIATION,time,endTime)
        }
    }

    private fun getCurrentTime():String {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = Date(System.currentTimeMillis())
        var mEndTime = simpleDateFormat.format(date)
        return  mEndTime
    }

    private   var businessTitleString : String? = null
    private  var cashierString:String? =null
    private var personTitleString:String? = null
    private  var firstTitle:String ? =null
    private var endOrderTitleString:String?=null
    private  var pensNumberString:String?=null
    private fun addSpaceTitle() {

        businessTitleString = businessStoreTitle.text.substring(0,businessStoreTitle.length()-1)+" "+businessStoreTitle.text.substring(businessStoreTitle.length()-1,businessStoreTitle.length())
        cashierString = cashierDateTitle.text.substring(0,cashierDateTitle.length()-1)+" "+cashierDateTitle.text.substring(cashierDateTitle.length()-1,cashierDateTitle.length())
        personTitleString = cashierPersonTitle.text.substring(0,1)+" "+cashierPersonTitle.text.substring(1,2)+" "+cashierPersonTitle.text.substring(2,3)+"   "+
                cashierPersonTitle.text.substring(cashierPersonTitle.text.length-1,cashierPersonTitle.text.length)
        firstTitle = firstOrderTitle.text.substring(0,1)+"        "+firstOrderTitle.text.substring(1,2)+" "+firstOrderTitle.text.substring(firstOrderTitle.text.length-1,firstOrderTitle.text.length)
        endOrderTitleString = endOrderTitle.text.substring(0,1)+"        "+endOrderTitle.text.substring(1,2)+" "+endOrderTitle.text.substring(endOrderTitle.text.length-1,endOrderTitle.text.length)
        pensNumberString = pensNumberTitle.text.substring(0,1)+"        "+pensNumberTitle.text.substring(1,2)+" "+pensNumberTitle.text.substring(pensNumberTitle.text.length-1,pensNumberTitle.text.length)
        var flag = LocaleManager.LANGUAGE_ENGLISH.equals(LocaleManager(activity).language)
        if(!flag){
            //营业门店
            businessStoreTitle.text = businessTitleString
            //对账日期
            cashierDateTitle.text = cashierString
            //收银员
            cashierPersonTitle.text = personTitleString
            //首笔
           firstOrderTitle.text =  firstTitle
            //末笔
            endOrderTitle.text =  endOrderTitleString
            //笔数
            pensNumberTitle.text = pensNumberString
        }
    }

    override fun lazyLoad() {

    }

    override fun initData() {

    }

    override fun getPresenter(): CashierReconciliationPresenter {
       return CashierReconciliationPresenter()
    }

    override fun initListener() {
        tvStartDate.setOnClickListener(this)
        tvStartTime.setOnClickListener(this)
        tvEndDate.setOnClickListener(this)
        tvEndTime.setOnClickListener(this)
        tvCashier.setOnClickListener(this)
        tvPrint.setOnClickListener(this)
        tvOut.setOnClickListener(this)
    }

    private fun getTime(date: Date,type:Int): String {//可根据需要自行截取数据显示
        when(type){
            START_TIME_DATE->{
                val format = SimpleDateFormat("yyyy-MM-dd")
                return format.format(date)
            }
            START_TIME_HOUR->{
                val format = SimpleDateFormat("HH:mm:ss")
                return format.format(date)
            }
        }
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return format.format(date)
    }

    //交班退出系统
    private fun showOutDialog() {
        CommonDialog.Builder(activity!!)
                .setTitle(getString(R.string.title_prompt))
                .setContent(getString(R.string.you_want_quit_system))
                .setCancelText(getString(R.string.delete_dialog_cancel))
                .setConfirmText(getString(R.string.dailog_confirm))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        var startDate = tvStartDate.text.toString().trim()
                        var startTime = tvStartTime.text.toString().trim()
                        var tvEndDate = tvEndDate.text.toString().trim()
                        var tvEndTime = tvEndTime.text.toString().trim()
                        mPresenter.changeLogout(PosConst.CHANGE_USER_LOGOUT, "$startDate $startTime", "$tvEndDate $tvEndTime")
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

    fun clearData(){
        AppBusManager.setToken("")
    }

    var pvCustomLunar: TimePickerView?=null
    //日期选择器
    private fun showDateTime(timeType:BooleanArray,witch:Int) {

        val selectedDate = Calendar.getInstance()//系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH))
        val endDate = Calendar.getInstance()
        endDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH))

        pvCustomLunar = TimePickerBuilder(activity!!, OnTimeSelectListener { date, v ->
            //选中事件回调
            when(witch){
                START_TIME_DATE->{
                    tvStartDate.text = getTime(date, START_TIME_DATE)
                }
                START_TIME_HOUR->{
                    tvStartTime.text = getTime(date, START_TIME_HOUR)
                }
                END_TIME_DATE ->{
                    tvEndDate.text = getTime(date, START_TIME_DATE)
                }
                END_TIME_HOUR ->{
                    tvEndTime.text = getTime(date, START_TIME_HOUR)
                }
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.pickerview_custom_lunar,CustomListener{v->
                    val tvSubmit = v.findViewById<View>(R.id.tv_finish) as TextView
                    val ivCancel = v.findViewById<View>(R.id.iv_cancel) as TextView
                    tvSubmit.setOnClickListener {
                        pvCustomLunar!!.returnData()
                        pvCustomLunar!!.dismiss()
                    }
                    ivCancel.setOnClickListener{ pvCustomLunar!!.dismiss()}
                })
                .setType(timeType)
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#E5E5E5"))
                .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            mmSocket?.close()
            mmSocket = null
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}