package com.epro.pos.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Vibrator
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.RadioGroup
import cn.bingoogolapple.qrcode.core.BarcodeType
import cn.bingoogolapple.qrcode.core.QRCodeView
import com.epro.pos.R
import com.epro.pos.listener.ScanResultEvent
import com.epro.pos.listener.SearchResultEvent
import com.epro.pos.utils.Beep
import com.mike.baselib.activity.BaseSimpleActivity
import com.mike.baselib.utils.AppContext
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.QRCodeUtil
import com.mike.baselib.utils.ext_loadImage
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.mike.baselib.utils.toast

/**
 * 扫码页面
 */
class ScanActivity : BaseSimpleActivity(), QRCodeView.Delegate, View.OnClickListener {

    companion object {
        const val SCAN_TYPE = "scan_type"
        const val SCAN_FOR_GOODS = 1//选择商品 入库 盘点
        const val SCAN_FOR_PAY = 2 //扫码支付
        const val SCAN_ADD_GOODS = 3 //添加商品到购物车

        fun launchWithScanType(fragment: Fragment, requestCode: Int, scanType: Int = SCAN_FOR_GOODS) {
            fragment.startActivityForResult(Intent(fragment.activity, ScanActivity::class.java).putExtra(SCAN_TYPE, scanType), requestCode)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            tvBack -> {
                finish()
            }
        }
    }

    var scanType = SCAN_FOR_GOODS

    override fun layoutContentId(): Int {
        return R.layout.activity_scan
    }

    var beep: Beep?=null
    override fun initData() {
        // 1、初始化Beep声音管理器
        beep = Beep(this)
    }


    override fun initListener() {
        tvBack.setOnClickListener(this)

    }

    private val TAG = ScanActivity::class.java!!.getSimpleName() + "_"

    override fun initView() {
        super.initView()
        setSupportActionBar(toolbar)
        zScanview.setDelegate(this)
        scanType = intent.getIntExtra(SCAN_TYPE, SCAN_FOR_PAY)
        when (scanType) {
            SCAN_FOR_GOODS -> {

            }
            SCAN_FOR_PAY -> {
                radioGroup.visibility = View.VISIBLE
                radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
                    override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                        if (R.id.rbScanPay == p1) {
                            llQrcode.visibility = View.GONE
                            zScanview.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
                            zScanview.startSpotAndShowRect() // 显示扫描框，并开始识别
                        } else if (R.id.rbQrcode == p1) {
                            llQrcode.visibility = View.VISIBLE
                            ivQrcode.ext_loadImage(QRCodeUtil.getQrImagePath(this@ScanActivity, "epro",
                                    DisplayManager.dip2px(330F)!!, DisplayManager.dip2px(330F)!!, null))
                            zScanview.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
                        }
                    }
                })
            }
        }
        zScanview.setType(BarcodeType.ALL, null) // 识别所有类型的码
    }

    override fun onStart() {
        super.onStart()
        zScanview.startCamera() // 打开后置摄像头开始预览，但是并未开始识别
        //mzxingview.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        zScanview.startSpotAndShowRect() // 显示扫描框，并开始识别
    }

    override fun onStop() {
        zScanview.stopCamera() // 关闭摄像头预览，并且隐藏扫描框
        super.onStop()
    }

    override fun onDestroy() {
        zScanview.onDestroy() // 销毁二维码扫描控件
        AppContext.getInstance().mainHandler.removeCallbacksAndMessages(null)
        super.onDestroy()
    }

    private fun vibrateAndDing() {
//        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
//        vibrator.vibrate(200)
        beep!!.playAndVibrate()
    }


    override fun onScanQRCodeSuccess(result: String?) {
        Log.i(TAG, "result:$result")
        vibrateAndDing()
        if (result.toString().isNotEmpty()) {
            val event = ScanResultEvent()
            event.result = result!!
            event.scanType = scanType
            EventBus.getDefault().post(event)
        } else {
            toast(getString(R.string.scan_to_null))
        }
        if (scanType == SCAN_FOR_PAY) {
            setResult(Activity.RESULT_OK)
            finish()
        }
    }

    override fun onCameraAmbientBrightnessChanged(isDark: Boolean) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        var tipText = zScanview.scanBoxView.tipText
        val ambientBrightnessTip = "\n"+getString(R.string.environment_is_too_dark)
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                zScanview.scanBoxView.tipText = tipText + ambientBrightnessTip
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip))
                zScanview.scanBoxView.tipText = tipText
            }
        }
    }

    override fun onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错")
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onSearchResultEvent(event: SearchResultEvent) {
        when (scanType) {
            SCAN_FOR_GOODS -> {//支持连续扫码
                if (event.isEmpty) {
                    setResult(Activity.RESULT_OK)
                    finish()
                    toast(getString(R.string.not_search_product))
                } else {
                    toast(getString(R.string.add_product_success))
                }
                AppContext.getInstance().mainHandler.postDelayed(Runnable {
                    zScanview.startSpot() // 连续开始识别
                }, 2000)
            }
            SCAN_ADD_GOODS -> {
                if (event.isEmpty) {
                    setResult(Activity.RESULT_OK)
                    finish()
                    toast(getString(R.string.not_search_product))
                } else {
                    toast(getString(R.string.add_product_success))
                    setResult(Activity.RESULT_OK, Intent())
                    finish()
                }
            }
        }
    }

}