package com.epro.pos.ui.login;

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import com.epro.pos.R
import com.epro.pos.listener.LoginSuccessEvent
import com.epro.pos.mvp.contract.LoginContract
import com.epro.pos.mvp.model.bean.LoginBean
import com.epro.pos.mvp.model.bean.PersonCenterBean
import com.epro.pos.mvp.presenter.LoginPresenter
import com.epro.pos.ui.AppConfigActivity
import com.epro.pos.ui.MainActivity
import com.epro.pos.ui.fragment.SplashFragment
import com.epro.pos.ui.view.CustomProgressDialog
import com.epro.pos.ui.view.LoginAreaSelectPopup
import com.epro.pos.ui.view.PhoneOrEmailCheckDialog
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.*
import com.mike.baselib.view.CommonDialog
import kotlinx.android.synthetic.main.activity_login.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor
import com.mike.baselib.utils.toast
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*


class LoginActivity : BaseTitleBarCustomActivity<LoginContract.View, LoginPresenter>(), LoginContract.View, View.OnClickListener {

    companion object {
        private const val REQUEST_CODE_REGISTER = 1
        private const val LANGUAGE_FAN = 1
        private const val LANGUAGE_CN = 2
        private const val LANGUAGE_ENGLISH = 3
        const val TAG = "Login"
        fun launchForReslut(activity: Activity, requestCode: Int) {
            //GsonBuilder().serializeNulls()
            activity.startActivityForResult(Intent(activity, LoginActivity::class.java), requestCode)
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            //手机号登入
            btnPhoneLogin -> {
                val account = etPhone.text.toString().trim()
                val password = etPhonePassword.text.toString().trim()
                val areaCode = tvAreaCode.text.toString()
                val passwordNull = etPhonePassword.text.toString()
                if (!ValidateUtils.validatePhoneNo(account)) {
                    toast(getString(R.string.phone_num_wrong_format))
                    return
                }

                if (TextUtils.isEmpty(passwordNull)){
                    toast(getString(R.string.password_null))
                    return
                }

//                if (!ValidateUtils.validatePassword(password)) {
//                    toast(getString(R.string.password_num_wrong))
//                    return
//                }
                // showLoginProgress()
                mPresenter.login(areaCode + "_" + account, password, PosConst.LOGIN_TYPE_MP, PosConst.LOGIN)
            }
            //邮箱登入
            btnEmailLogin -> {
                val account = etEmail.text.toString().trim()
                val password = etEmailPassword.text.toString().trim()
                val passwordNull = etEmailPassword.text.toString()
                if (!ValidateUtils.validateEmail(account)) {
                    toast(getString(R.string.email_format_wrong))
                    return
                }

                if (TextUtils.isEmpty(passwordNull)){
                    toast(getString(R.string.password_null))
                    return
                }
                /*if (!ValidateUtils.validatePassword(password)) {
                    toast(getString(R.string.password_num_wrong))
                    return
                }*/
                // showLoginProgress()
                mPresenter.login(account, password, PosConst.LOGIN_TYPE_EP, PosConst.LOGIN)
            }
            //门店登入
            btnShopIdLogin -> {
                val account = etShopId.text.toString().trim()
                val userId = etUserName.text.toString().trim()
                val password = etShopIdPassword.text.toString().trim()
                val passwordNull = etShopIdPassword.text.toString()
                // showLoginProgress()
                if (TextUtils.isEmpty(passwordNull)){
                    toast(getString(R.string.password_null))
                    return
                }
                /*if (!ValidateUtils.validatePassword(password)) {
                    toast(getString(R.string.password_num_wrong))
                    return
                }*/
                mPresenter.login(account+userId, password, PosConst.LOGIN_TYPE_EP, PosConst.LOGIN)
            }
            //忘记密码
            tvForget -> {
                startActivity<FindPasswordActivity>()
            }
            tvEmailForget -> {
                startActivity<FindPasswordActivity>()
            }
            tvShopIdForget -> {
                startActivity<FindPasswordActivity>()
            }
            //手机号区号选择
            tvAreaCode -> {
                showAreaPopup()
            }
            //繁体中文
            languageFan -> {
                selectLanguage(LANGUAGE_FAN)
            }
            //简体中文
            languageCN -> {
                selectLanguage(LANGUAGE_CN)
            }
            //English
            languageEn -> {
                selectLanguage(LANGUAGE_ENGLISH)
            }
        }
    }

    @AfterPermissionGranted(MainActivity.RC_PHONE_STATE)
    private fun requestPerms() {
        if (EasyPermissions.hasPermissions(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.READ_PHONE_STATE)) {
            createUuid()
            // Already have permission, do the thing
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.request_some_dev_perms),
                    MainActivity.RC_PHONE_STATE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE)
        }
        if (Build.VERSION.SDK_INT > 28 && getApplicationContext()!!.getApplicationInfo().targetSdkVersion > 28) {

        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        super.onPermissionsDenied(requestCode, perms)
        showPermsDialog()
    }

    private fun showPermsDialog() {
        CommonDialog.Builder(this)
                .setContent(getString(R.string.must_some_perm))
                .setOnConfirmListener(object : CommonDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                        requestPerms()
                    }
                })
                .setOnCancelListener(object : CommonDialog.OnCancelListener {
                    override fun onClick(dialog: Dialog) {
                        finish()
                    }
                })
                .create()
                .show()
    }

    private fun createUuid() {
        var uuid = AppBusManager.getUuid()
        uuid = if (TextUtils.isEmpty(uuid)) Md5.digest32(DeviceUidGenerator.generate(this)) else uuid
        AppBusManager.setUuid(uuid)
    }


    var page = 1
    override fun initListener() {
        btnPhoneLogin.setOnClickListener(this)
        btnEmailLogin.setOnClickListener(this)
        btnShopIdLogin.setOnClickListener(this)
        tvForget.setOnClickListener(this)
        tvEmailForget.setOnClickListener(this)
        tvShopIdForget.setOnClickListener(this)
        tvAreaCode.setOnClickListener(this)
        languageFan.setOnClickListener(this)
        languageCN.setOnClickListener(this)
        languageEn.setOnClickListener(this)
        ivLogo.setOnClickListener(this)
        cbPhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbEmailEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etEmailPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbShopIdEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etShopIdPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })

        etPhone.addTextChangedListener(textWatcher)
        etPhonePassword.addTextChangedListener(textWatcher)
        etEmail.addTextChangedListener(textWatcher)
        etEmailPassword.addTextChangedListener(textWatcher)
        etShopId.addTextChangedListener(textWatcher)
        etShopIdPassword.addTextChangedListener(textWatcher)

        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                when (p1) {
                    R.id.rbPhoneLogin -> {
                        if (page == 1) {
                        } else if (page == 2) {
                            viewFlipper.showPrevious()
                        } else {
                            viewFlipper.showNext()
                        }
                        updateIndicator(rbPhoneLogin)
                        page = 1
                    }
                    R.id.rbEmailLogin -> {
                        if (page == 1) {
                            viewFlipper.showNext()
                        } else if (page == 2) {
                        } else {
                            viewFlipper.showPrevious()
                        }
                        updateIndicator(rbEmailLogin)
                        page = 2
                    }
                    R.id.rbShopIDLogin -> {
                        if (page == 1) {
                            viewFlipper.showNext()
                            viewFlipper.showNext()
                        } else if (page == 2) {
                            viewFlipper.showNext()
                        } else {
                        }
                        updateIndicator(rbShopIDLogin)
                        page = 3
                    }
                }
            }
        })
    }

    fun updateIndicator(v: View) {
        when (v) {
            rbPhoneLogin -> {
                setCheckedPoninter(R.drawable.shape_logintab_indicator, rbPhoneLogin)
                setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbEmailLogin)
                setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbShopIDLogin)
            }
            rbEmailLogin -> {
                setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbPhoneLogin)
                setCheckedPoninter(R.drawable.shape_logintab_indicator, rbEmailLogin)
                setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbShopIDLogin)
            }
            rbShopIDLogin -> {
                setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbPhoneLogin)
                setCheckedPoninter(R.drawable.shape_logintab_indicator_tran, rbEmailLogin)
                setCheckedPoninter(R.drawable.shape_logintab_indicator, rbShopIDLogin)
            }
        }
    }

    override fun getPresenter(): LoginPresenter {
        return LoginPresenter()
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
        return R.layout.activity_login
    }

    override fun loginSuccess(result: LoginBean.Result) {
        AppBusManager.setToken(result.shiroCookie)
        mPresenter.PersonCenter(PosConst.GET_PERSON_INFO)
        AppBusManager.setUsername(etPhone.text.toString())
        saveDateTime()
    }


    private fun saveDateTime() {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = Date(System.currentTimeMillis())
        var time = simpleDateFormat.format(date)
        AppBusManager.setLoginTime(time)
    }

    //userID保存
    override fun onPersonCenterSuccess(result:PersonCenterBean.Result) {
        if (!AppBusManager.getShopId().equals(result.shopId)) {
            AppBusManager.setUserNum(result.account)
            AppBusManager.setUserId(result.id.toInt())
            AppBusManager.setUserEmail(result.email)
            AppBusManager.setShopName(result.shopName)
            AppBusManager.setShopId(result.shopId)
            SPUtils.remove(this, "pathPhoto")
            SPUtils.remove(this, "selectPhoto")
            SPUtils.remove(this, "renew")
        }
        AppBusManager.setBargain(result.isBargain)
        toast(getString(R.string.logout_success_toast))
        setResult(Activity.RESULT_OK)
        EventBus.getDefault().post(LoginSuccessEvent())
        PosBusManager.shopIdChange(result.shopId)
        finish()
    }

    val runnable = SplashClose(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPerms()
    }

    override fun onDestroy() {
        super.onDestroy()
        val splashFragment = supportFragmentManager.findFragmentByTag("splash")
        if (splashFragment != null && splashFragment.isAdded!!) {
            supportFragmentManager.beginTransaction().remove(splashFragment).commitAllowingStateLoss()
        }
        AppContext.getInstance().mainHandler.removeCallbacks(runnable)
        etPhone.removeTextChangedListener(textWatcher)
        etPhonePassword.removeTextChangedListener(textWatcher)
        etEmail.removeTextChangedListener(textWatcher)
        etEmailPassword.removeTextChangedListener(textWatcher)
        etShopId.removeTextChangedListener(textWatcher)
        etShopIdPassword.removeTextChangedListener(textWatcher)
    }

    inner class SplashClose(activity: FragmentActivity) : Runnable {
        override fun run() {
            val activity = reference?.get()
            val supportFragmentManager = activity?.supportFragmentManager
            val splashFragment = supportFragmentManager?.findFragmentByTag("splash")
            if (splashFragment != null && splashFragment.isAdded!!) {
                supportFragmentManager.beginTransaction().remove(splashFragment).commitAllowingStateLoss()
            }
        }

        var reference: WeakReference<FragmentActivity>? = null

        init {
            reference = WeakReference(activity)
        }

    }

    override fun initData() {
    }

    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }


    override fun initView() {
        super.initView()
        window.setBackgroundDrawable(resources.getDrawable(R.drawable.shape_bg_login))
        getTitleBar().visibility = View.GONE
        etPhone.setText(AppBusManager.getUsername())
        etShopId.setText(AppBusManager.getShopId())
        cbPhoneEyes.isChecked = false
        cbEmailEyes.isChecked = false
        cbShopIdEyes.isChecked = false
        radioGroup.check(R.id.rbPhoneLogin)
        selectLanguage(LANGUAGE_FAN)
        if(!AppConfig.isPublish){
            tvDev.visibility=View.VISIBLE
            tvDev.text="环境配置("+AppBusManager.getDevName()+")"
            tvDev.setOnClickListener(View.OnClickListener {
                startActivity<AppConfigActivity>()
            })
        }
    }

    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            var flag = false
            when (radioGroup.checkedRadioButtonId) {
                R.id.rbPhoneLogin -> {
                    flag = TextUtils.isEmpty(etPhone.text.toString()) || TextUtils.isEmpty(etPhonePassword.text.toString())
                    btnPhoneLogin.isEnabled = !flag
                    btnPhoneLogin.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_login else R.drawable.shape_bg_btn_login_disable)
                }
                R.id.rbEmailLogin -> {
                    flag = TextUtils.isEmpty(etEmail.text.toString()) || TextUtils.isEmpty(etEmailPassword.text.toString())
                    btnEmailLogin.isEnabled = !flag
                    btnEmailLogin.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_login else R.drawable.shape_bg_btn_login_disable)
                }
                R.id.rbShopIDLogin -> {
                    flag = TextUtils.isEmpty(etShopId.text.toString()) || TextUtils.isEmpty(etShopIdPassword.text.toString())
                    btnShopIdLogin.isEnabled = !flag
                    btnShopIdLogin.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_login else R.drawable.shape_bg_btn_login_disable)
                }
            }

        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
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


    private fun setCheckedPoninter(res: Int, radioButton: RadioButton) {
        val drawable = resources?.getDrawable(res)
        drawable?.bounds = Rect(0, 0, drawable?.minimumWidth!!, drawable?.minimumHeight!!)
        radioButton.setCompoundDrawables(null, null, null, drawable)
    }

    //语言选择
    private fun selectLanguage(language: Int) {
        when (language) {
            1 -> {
                languageFanLine.visibility = View.VISIBLE
                languageCNLine.visibility = View.GONE
                languageEnLine.visibility = View.GONE
              //  BaseApplication.localeManager?.setNewLocale(this, LocaleManager.LANGUAGE_TRADITIONAL)
            }
            2 -> {
                languageFanLine.visibility = View.GONE
                languageCNLine.visibility = View.VISIBLE
                languageEnLine.visibility = View.GONE
              //  BaseApplication.localeManager?.setNewLocale(this, LocaleManager.LANGUAGE_CHINESE)
            }
            3 -> {
                languageFanLine.visibility = View.GONE
                languageCNLine.visibility = View.GONE
                languageEnLine.visibility = View.VISIBLE
              //  BaseApplication.localeManager?.setNewLocale(this, LocaleManager.LANGUAGE_ENGLISH)
            }
        }
    }

    //地区选择
    private fun showAreaPopup() {
        var pop = LoginAreaSelectPopup(this)
        pop.setBackground(ColorDrawable(resources.getColor(android.R.color.transparent)))
        pop.popupGravity = Gravity.BOTTOM
        pop.width = rlInputPhone.measuredWidth!!
        pop.showPopupWindow(rlInputPhone)
        val loginPhoneHK = pop.contentView.findViewById<TextView>(R.id.loginPhoneHk)
        val loginPhoneCN = pop.contentView.findViewById<TextView>(R.id.loginPhoneCN)
        loginPhoneHK.setOnClickListener {
            loginPhoneHK.textColor = resources.getColor(R.color.mainColor)
            loginPhoneHK.background = resources.getDrawable(R.color.login_area_select)
            loginPhoneCN.textColor = resources.getColor(R.color.mainTextColor)
            loginPhoneCN.background = resources.getDrawable(R.color.mainMatchColor)
            tvAreaCode.text = "+852"
        }

        loginPhoneCN.setOnClickListener {
            loginPhoneHK.textColor = resources.getColor(R.color.mainTextColor)
            loginPhoneHK.background = resources.getDrawable(R.color.mainMatchColor)
            loginPhoneCN.textColor = resources.getColor(R.color.mainColor)
            loginPhoneCN.background = resources.getDrawable(R.color.login_area_select)
            tvAreaCode.text = "+86"
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoginSuccessEvent(event: LoginSuccessEvent) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
        super.showError(errorMsg, errorCode, type)
        if (type == PosConst.BUSINESS_BASE_INFO) {
            AppBusManager.setToken("")
        }
    }

}

