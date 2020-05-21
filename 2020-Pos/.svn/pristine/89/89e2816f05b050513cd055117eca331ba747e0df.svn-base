package com.epro.comp.login.ui.activity;

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.InputType
import android.view.KeyEvent
import android.view.View
import android.widget.CompoundButton
import com.billy.cc.core.component.CC
import com.billy.cc.core.component.CCResult
import com.billy.cc.core.component.CCUtil
import com.tencent.bugly.crashreport.CrashReport
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.listener.LoginSuccessEvent
import com.mike.baselib.utils.*
import com.epro.comp.login.R
import com.epro.comp.login.mvp.contract.LoginContract
import com.epro.comp.login.mvp.model.bean.LoginBean
import com.epro.comp.login.mvp.presenter.LoginPresenter
import com.epro.comp.login.ui.fragment.SplashFragment
import com.epro.comp.login.utils.LoginBusManager
import com.epro.comp.login.utils.LoginConst
import kotlinx.android.synthetic.main.comp_login_activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import java.lang.ref.WeakReference


class LoginActivity : BaseTitleBarCustomActivity<LoginContract.View, LoginPresenter>(), LoginContract.View {
    override fun initListener() {
    }

    override fun getPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    companion object {
        private const val REQUEST_CODE_REGISTER = 1
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_REGISTER ->
                if (resultCode == RESULT_OK) {
                    finish()
                }
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.comp_login_activity_login
    }

    var loginSuccess = false

    override fun loginSuccess(user: LoginBean.LoginUser) {
        loginSuccess = true
        AppBusManager.setToken(user.token)
        LoginBusManager.setUser(user.user)
        AppBusManager.setUsername(etPhoneNo.text.toString().trim())
        AppBusManager.setPassword(AppBusManager.encryptPassword(etPassword.text.toString().trim()))
        EventBus.getDefault().post(LoginSuccessEvent())
        setResult(Activity.RESULT_OK)
        CrashReport.setUserId(AppBusManager.getUsername())
        toast("登录成功")
        finish()

    }

    override fun finish() {
        logTools.d(CCUtil.getNavigateCallId(this))
        if (CCUtil.getNavigateCallId(this) != null) {
            CC.sendCCResult(CCUtil.getNavigateCallId(this), if (loginSuccess) CCResult.success() else CCResult.error("cancel"))
        }
        super.finish()
    }

    override fun initData() {
    }


    val runnable=SplashClose(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val splashFragment: SplashFragment?
//        if(savedInstanceState!=null){
//            splashFragment= supportFragmentManager.findFragmentByTag("splash") as SplashFragment
//        }else{
//            supportFragmentManager.beginTransaction().add(R.id.flContent, SplashFragment(),"splash").commitAllowingStateLoss()
//        }
//        AppContext.getInstance().mainHandler.postDelayed(runnable,2000)
    }

    override fun onDestroy() {
        super.onDestroy()
        val splashFragment=supportFragmentManager.findFragmentByTag("splash")
        if(splashFragment!=null&&splashFragment.isAdded!!){
            supportFragmentManager.beginTransaction().remove(splashFragment).commitAllowingStateLoss()
        }
        AppContext.getInstance().mainHandler.removeCallbacks(runnable)
    }

    inner class SplashClose(activity: FragmentActivity) :Runnable {
        override fun run() {
            val activity=reference?.get()
            val supportFragmentManager=activity?.supportFragmentManager
            val splashFragment=supportFragmentManager?.findFragmentByTag("splash")
            if(splashFragment!=null&&splashFragment.isAdded!!){
                supportFragmentManager.beginTransaction().remove(splashFragment).commitAllowingStateLoss()
            }
        }
        var reference: WeakReference<FragmentActivity>?=null
        init {
            reference= WeakReference(activity)
        }

    }

    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }


    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        etPhoneNo.setText(AppBusManager.getUsername())
        btnLogin.setOnClickListener(View.OnClickListener {
            //登录
            val loginname = etPhoneNo.text.toString().trim()
            var password = etPassword.text.toString().trim()
            if (!mPresenter.checkParam(loginname, password)) {
                return@OnClickListener
            }
            password = AppBusManager.encryptPassword(password)
//            val loginname = "admin"
//            val password = "EFB273CD81AFF53727464390847D2019"
            AppBusManager.setToken("")
            mPresenter.login("", loginname, password, LoginConst.LOGIN!!)
            AppUtils.closeKeyboard(this)
        })

        btnLogin.setText("登录")

        tvForget.setOnClickListener(View.OnClickListener {
            startActivity<RegisterActivity>()
        })

        cbDelete.isChecked = false
        cbDelete.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPhoneNo.setText("")
        })

        cbEyes.isChecked = false
        cbEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })

        tvLogin.setOnClickListener(View.OnClickListener {
        })

        tvRegister.setOnClickListener(View.OnClickListener {
        })

        if (AppConfig.isPublish) {
            tvLogin.isClickable = false
        }

    }

    private var mExitTime: Long = 0
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (System.currentTimeMillis().minus(mExitTime) <= 2000) {
                finish()
            } else {
                mExitTime = System.currentTimeMillis()
                ToastUtil.showToast("再按一次退出程序")
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginSuccessEvent(event: LoginSuccessEvent) {
        loginSuccess = true
        setResult(Activity.RESULT_OK)
        finish()
    }
}

