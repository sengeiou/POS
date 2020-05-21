package com.mike.baselib.activity

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.annotation.NonNull
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.mike.baselib.R
import com.mike.baselib.base.BaseApplication
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.utils.*
import com.umeng.message.PushAgent
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


/**
 * @author mike
 * created: 2019/2/12
 * desc:BaseActivity基类
 */
abstract class BaseActivity<V: IBaseView,P: IPresenter<V>>: AppCompatActivity(),EasyPermissions.PermissionCallbacks, IBaseView {

    protected val mPresenter by lazy { getPresenter() }

    protected var logTools= LogTools("BaseActivity_"+this.javaClass.simpleName)

    protected var rootView:View?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        if(!intent.getBooleanExtra("cannotRequestedOrientation",false)){
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        super.onCreate(savedInstanceState)
        rootView=LayoutInflater.from(this).inflate(layoutId(),null)
        setContentView(rootView)
        mPresenter.attachView(this as V)
        initView()
        initData()
        initListener()
        EventBus.getDefault().register(this)
        initStatusBar()
        ActivityCollector.addActivity(this, this.javaClass)
        PushAgent.getInstance(this).onAppStart()
    }

    open fun initStatusBar(){
        StatusBarUtil.darkMode(this)
    }

    /**
     *  加载布局
     */
    abstract fun layoutId(): Int

    /**
     * 初始化数据
     */
    abstract fun initData()


    abstract fun initListener()

    /**
     * 初始化 View
     */
    abstract fun initView()

    abstract fun   getPresenter():P

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
        AppContext.getInstance().refWatcher?.watch(this)
        EventBus.getDefault().unregister(this)
        ActivityCollector.removeActivity(this)
    }


    /**
     * 重写要申请权限的Activity或者Fragment的onRequestPermissionsResult()方法，
     * 在里面调用EasyPermissions.onRequestPermissionsResult()，实现回调。
     *
     * @param requestCode  权限请求的识别码
     * @param permissions  申请的权限
     * @param grantResults 授权结果
     */
    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    /**
     * 当权限被成功申请的时候执行回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        logTools.d( "获取成功的权限$perms")
    }

    /**
     * 当权限申请失败的时候执行的回调
     *
     * @param requestCode 权限请求的识别码
     * @param perms       申请的权限的名字
     */
    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        //处理权限名字字符串
        val sb = StringBuffer()
        for (str in perms) {
            sb.append(str)
            sb.append("\n")
        }
        sb.replace(sb.length - 2, sb.length, "")
        //用户点击拒绝并不在询问时候调用
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            Toast.makeText(this, getString(R.string.permission_denied) + sb + getString(R.string.and_no_longer_ask), Toast.LENGTH_SHORT).show()
            AppSettingsDialog.Builder(this)
                    .setRationale(getString(R.string.this_feature_requires) + sb + getString(R.string.otherwise_it_cannot_be_used_normally))
                    .setPositiveButton(getString(R.string.ok))
                    .setNegativeButton(getString(R.string.no))
                    .build()
                    .show()
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {/* Do something */
    }

    class MessageEvent/* Additional fields if needed */

    override fun showLoading(type: String) {
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
    }

    override fun attachBaseContext(base: Context) {
       // super.attachBaseContext(base)
        super.attachBaseContext(BaseApplication.localeManager?.setLocale(base))
    }

}


