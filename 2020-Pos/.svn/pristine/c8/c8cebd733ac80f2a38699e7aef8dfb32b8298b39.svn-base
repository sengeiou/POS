package com.epro.pos

import android.app.Activity
import android.os.Bundle
import com.mike.baselib.base.BaseApplication
import com.mike.baselib.net.RetrofitManager
import com.epro.pos.api.ApiService
import com.epro.pos.utils.PosBusManager
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.net.exception.ExceptionHandle
import com.mike.baselib.rx.scheduler.SchedulerUtils
import com.mike.baselib.utils.DateUtils
import com.umeng.commonsdk.UMConfigure
import kotlin.properties.Delegates
import com.umeng.message.IUmengRegisterCallback
import com.umeng.message.PushAgent
import io.reactivex.Observable


class PosApplication : BaseApplication() {

    companion object {
        var apiService: ApiService by Delegates.notNull()
            private set
    }

    override fun onCreate() {
        // Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
        // Beta.tipsDialogLayoutId = R.layout.upgrade_tips_dialog
        /*在application中初始化时设置监听，监听策略的收取*/
//        Beta.upgradeListener = UpgradeListener { ret, strategy, isManual, isSilence ->
//            if (strategy != null) {
//                val i = Intent()
//                i.setClass(applicationContext, UpgradeActivity::class.java!!)
//                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                startActivity(i)
//            } else {
//                Toast.makeText(this, "没有更新", Toast.LENGTH_LONG).show()
//            }
//        }
//
//        /* 设置更新状态回调接口 */
//        Beta.upgradeStateListener = object : UpgradeStateListener {
//            override fun onDownloadCompleted(isManual: Boolean) {
//            }
//
//            override fun onUpgradeSuccess(isManual: Boolean) {
//                Toast.makeText(applicationContext, "UPGRADE_SUCCESS", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onUpgradeFailed(isManual: Boolean) {
//                Toast.makeText(applicationContext, "UPGRADE_FAILED", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onUpgrading(isManual: Boolean) {
//                Toast.makeText(applicationContext, "UPGRADE_CHECKING", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onUpgradeNoVersion(isManual: Boolean) {
//                Toast.makeText(applicationContext, "UPGRADE_NO_VERSION", Toast.LENGTH_SHORT).show()
//            }
//        }

        super.onCreate()
        apiService = RetrofitManager.getRetrofit().create(ApiService::class.java)
        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks)

        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this, "5d380ec70cafb2442d0001cf", "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "666021ef31b772823148c31803c5bd30")

        //获取消息推送代理示例
        val mPushAgent = PushAgent.getInstance(this)
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(object : IUmengRegisterCallback {
            override fun onSuccess(deviceToken: String) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                logTools.i("注册成功：deviceToken：-------->  $deviceToken")
            }

            override fun onFailure(s: String, s1: String) {
                logTools.e("注册失败：-------->  s:$s,s1:$s1")
            }
        })
        //获取服务器时间
        getSystemTime()
    }

    private fun getSystemTime() {
        val d = Observable.just(true).flatMap {
            return@flatMap apiService.getSystemTime()
        }.compose(SchedulerUtils.ioToMain()).subscribe({ bean ->
            if (bean.code == ErrorStatus.SUCCESS) {
                val value = DateUtils.dateToStamp(bean.result as String) - System.currentTimeMillis()
                PosBusManager.setTimeDifferenceValue(value)
            } else {

            }
        }, { throwable ->
            //处理异常
            ExceptionHandle.handleException(throwable)

        })
    }


    private val mActivityLifecycleCallbacks = object : ActivityLifecycleCallbacks {
        override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
            logTools.d("onCreated: " + activity.componentName.className)
        }

        override fun onActivityStarted(activity: Activity) {
            logTools.d("onStart: " + activity.componentName.className)
        }

        override fun onActivityResumed(activity: Activity) {
            if(!PosBusManager.isGetSystemTimeSuccess()){
                getSystemTime()
            }

        }

        override fun onActivityPaused(activity: Activity) {

        }

        override fun onActivityStopped(activity: Activity) {

        }

        override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

        }

        override fun onActivityDestroyed(activity: Activity) {
            logTools.d("onDestroy: " + activity.componentName.className)
        }
    }
}
