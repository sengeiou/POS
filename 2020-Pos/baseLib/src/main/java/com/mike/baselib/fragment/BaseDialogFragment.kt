package com.mike.baselib.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.annotation.NonNull
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import com.classic.common.MultipleStatusView
import com.mike.baselib.R
import com.mike.baselib.base.IBaseView
import com.mike.baselib.base.IPresenter
import com.mike.baselib.net.exception.ErrorStatus
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.dialogfragment_base_custom.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions


abstract class BaseDialogFragment<V : IBaseView, P : IPresenter<V>> : DialogFragment(), EasyPermissions.PermissionCallbacks, IBaseView {

    protected val mPresenter by lazy { getPresenter() }
    protected val logTools = LogTools("BaseFragment_" + this.javaClass.simpleName)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this as V)
        initView()
        initData()
        initListener()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCanceledOnTouchOutside(isCanceledOnTouchOutside())
        val view = inflater.inflate(getLayoutId(), container, false) //布局的高度会无效 wrap_content
        val flContent = view.findViewById<FrameLayout>(R.id.flContent)
        flContent.removeAllViews()
        flContent.addView(inflater.inflate(getContentLayoutId(),null))
        flContent.post(Runnable {
            logTools.d(flContent.height)
            logTools.d(DisplayManager.getScreenHeight())
            val params=multipleStatusView.layoutParams
            params.height=flContent.height
            multipleStatusView.layoutParams=params
        })
        return view
    }

    open fun isCanceledOnTouchOutside():Boolean{
       return false
    }

    fun getLayoutId(): Int {
        return R.layout.dialogfragment_base_custom
    }

    /**
     * 加载布局
     */
    @LayoutRes
    abstract fun getContentLayoutId(): Int

    /**
     * 初始化 View
     */
    abstract fun initView()

    abstract fun initData()

    abstract fun getPresenter(): P


    abstract fun initListener()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.detachView()
        activity?.let { AppContext.getInstance().refWatcher?.watch(activity!!) }
        EventBus.getDefault().unregister(this)
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
        logTools.d("获取成功的权限$perms")
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
            Toast.makeText(activity, getString(R.string.permission_denied) + sb + getString(R.string.and_no_longer_ask), Toast.LENGTH_SHORT).show()
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

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        ToastUtil.showToast(errorMsg)
        logTools.d(errorMsg)
        if (errorCode == ErrorStatus.NETWORK_ERROR) {
            multipleStatusView.showNoNetwork()
        } else {
            multipleStatusView.showContent()
        }
    }

    override fun showLoading(type: String) {
        if(multipleStatusView.viewStatus!=MultipleStatusView.STATUS_LOADING){
            multipleStatusView.showLoading()
            multipleStatusView.findViewById<ImageView>(R.id.ivLoading).ext_loadGif(R.mipmap.gif_loading)
        }
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        if (errorCode == ErrorStatus.SUCCESS) {
            multipleStatusView.showContent()
        }
    }


    fun getMultipleStatusView(): MultipleStatusView {
        return multipleStatusView
    }

    override fun onStart() {
        super.onStart()
        val window = dialog.window
        val params = window!!.attributes
        // params.alpha=1f//设置内容透明度
        if(getWidth()!=0){
            params.width = getWidth()
        }
        if(getHeight()!=0){
            val p=flContent.layoutParams
            p.height=getHeight()
            flContent.layoutParams=p
        }
        window.attributes = params
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    open fun getWidth(): Int {
        return 0
    }

    open fun getHeight(): Int {
        return 0
    }

}
