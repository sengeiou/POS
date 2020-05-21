package com.epro.pos.ui.login;

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.listener.LoginSuccessEvent
import com.epro.pos.mvp.contract.FindPasswordContract
import com.epro.pos.mvp.model.bean.BusinessBaseInfoBean
import com.epro.pos.mvp.model.bean.FindPasswordBean
import com.epro.pos.mvp.model.bean.GetVfBean
import com.epro.pos.mvp.model.bean.LoginBean
import com.epro.pos.mvp.presenter.FindPasswordPresenter
import com.epro.pos.ui.MainActivity
import com.epro.pos.ui.view.LoginAreaSelectPopup
import com.epro.pos.utils.PosBusManager
import com.epro.pos.utils.PosConst
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.*
import kotlinx.android.synthetic.main.activity_find_password.*
import kotlinx.android.synthetic.main.layout_findpassword_phone.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.textColor
import com.mike.baselib.utils.toast
import java.text.SimpleDateFormat
import java.util.*


/**
 * 找回密码页面
 */
class FindPasswordActivity : BaseTitleBarCustomActivity<FindPasswordContract.View, FindPasswordPresenter>(), FindPasswordContract.View, View.OnClickListener {

    override fun onBusinessInfoSuccess(result: BusinessBaseInfoBean.Result) {
        if (!AppBusManager.getShopId().equals(result.shopId)){
            AppBusManager.setUserId(result.userId.toInt())
            AppBusManager.setShopName(result.shopName)
            AppBusManager.setShopId(result.shopId)
            SPUtils.remove(this,"pathPhoto")
            SPUtils.remove(this,"selectPhoto")
            SPUtils.remove(this,"renew")
        }
        EventBus.getDefault().post(LoginSuccessEvent())
        setResult(Activity.RESULT_OK)
        toast(getString(R.string.logout_success_toast))
        PosBusManager.shopIdChange(result.shopId)
        finish()
    }

    override fun loginSuccessed(result: LoginBean.Result) {
        mPresenter.BusinessBaseInfo(PosConst.BUSINESS_BASE_INFO)
        AppBusManager.setToken(result.shiroCookie)
        AppBusManager.setUsername(etPhone.text.toString())
        saveDateTime()
    }

    private fun saveDateTime() {
        var simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var date = Date(System.currentTimeMillis())
        var time = simpleDateFormat.format(date)
        AppBusManager.setLoginTime(time)
    }

    override fun onGetVfCodeSuccess(result: GetVfBean.Result) {
        toast(getString(R.string.get_code_success))
    }

    companion object {
        const val TAG = "findPassword"
        const val FOR_AREA_CODE = 1
        var  COUNT =1
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, FindPasswordActivity::class.java).putExtra(TAG, str))
        }
    }


    override fun getPresenter(): FindPasswordPresenter {
        return FindPasswordPresenter()
    }

    override fun onFindPasswordSuccess( result:FindPasswordBean.Result) {
        initFindThreeView()
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_find_password
    }

    override fun initData() {

    }

    override fun onClick(p0: View?) {
        when (p0) {
            //图形验证码生成
            ivPhoneVfCode->{
                val  codeUtils = CodeUtils.getInstance()
                val  bitmap = codeUtils.createBitmap()
                ivPhoneVfCode.setImageBitmap(bitmap)
            }
            //忘记密码首页下一步
            btnPhoneNext -> {
                val account = etPhone.text.toString().trim()
                val code = etPhoneImageVfcode.text.toString().trim()//图形验证码
                val codeUtils = CodeUtils.getInstance()
                val imgCode = codeUtils.code
                if (1 == homePage){
                    //手机号验证
                    if (!ValidateUtils.validatePhoneNo(account)){
                        toast(getString(R.string.phone_num_wrong_format))
                       return
                    }else if (!imgCode.equals(code,true)){
                        toast(getString(R.string.code_no_mismatch))
                        return
                    }else{
                        viewFlipperFind.showNext()
                        initFindOneView(homePage,account)
                    }

                } else if (2 == homePage){
                    //邮箱验证
                    if (!ValidateUtils.validateEmail(account)){
                        toast(getString(R.string.email_format_wrong))
                        return
                    }else if (!imgCode.equals(code,true)){
                        toast(getString(R.string.code_no_mismatch))
                        return
                    }else{
                        viewFlipperFind.showNext()
                        initFindOneView(homePage,account)
                    }
                }
            }

            tvAreaCode -> {
                showAreaPopup()
            }

            //第一步
            btnFindOne->{
                //效验验证码准备下一步
                initFindTwoView()
            }

            //第二步
            btnFindTwo->{
                //重置密码
                val newPassword = etPhonePassword.text.toString().trim()
                val newConfirmPassword = etFindPassword.text.toString().trim()
                val account = etPhone.text.toString().trim()
                val code = etPhoneOne.text.toString().trim()
                var areaCode = tvAreaCode.text.toString().trim()

                if (!ValidateUtils.validatePassword(newPassword)){
                    toast(getString(R.string.password_format_error))
                    return
                }else if (!newPassword.equals(newConfirmPassword)){
                    toast(getString(R.string.two_input_psw_mismatch))
                    return
                }
                if (1==homePage){
                    mPresenter.findPassword(areaCode+"_"+account,newPassword,newConfirmPassword,code,PosConst.LOGIN_TYPE_MP,PosConst.FIND_PASSWORD)
                } else if (2 == homePage){
                    mPresenter.findPassword(account,newPassword,newConfirmPassword,code,PosConst.LOGIN_TYPE_EP,PosConst.FIND_PASSWORD)
                }
               /* //最后一个view 应该放到findsuccess回调成功初始化
                initFindThreeView()*/
            }

            //第三步
            btnFindThree->{
                val account = etPhone.text.toString().trim()
                var areaCode = tvAreaCode.text.toString().trim()
                val newConfirmPassword = etFindPassword.text.toString().trim()
                if (1 == homePage){
                    mPresenter.login(areaCode + "_" + account, newConfirmPassword, PosConst.LOGIN_TYPE_MP, PosConst.LOGIN)
                }else if (2 == homePage){
                    mPresenter.login(account, newConfirmPassword, PosConst.LOGIN_TYPE_EP, PosConst.LOGIN)
                }
            }

            btnGetCode->{
                //获取验证码
                val account = etPhone.text.toString().trim()
                var areaCode = tvAreaCode.text.toString().trim()
                btnGetCode.start()
                if (1 == homePage){
                    mPresenter.getVfCode(areaCode+"_"+account,PosConst.VF_TYPE_FINDPASSWORD , PosConst.GET_VFCODE)
                }else if (2 == homePage){
                    mPresenter.getVfCode(account,PosConst.VF_TYPE_FINDPASSWORD , PosConst.GET_VFCODE)
                }
            }

            getLeftView()->{
                when (backPage) {
                    0 -> {
                        finish()
                    }
                    1 -> {
                        viewFlipperFind.showPrevious()
                        backPage = 0
                    }
                    2 -> {
                        val account = etPhone.text.toString().trim()
                        initFindOneView(homePage,account)
                    }
                    3 -> {
                        handler.removeMessages(COUNT)
                        if (timer != null){
                            timer!!.cancel()
                            timer = null
                        }
                        initFindTwoView()
                    }
                }
            }
        }
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        var num = 5
        override fun handleMessage(msg: android.os.Message) {
            when (msg.what) {
                COUNT->{
                    if (num in 0..5){
                        afterFive.text = num.toString()+"s"
                        //倒计时0跳转登入
                        if (num ==0){
                            val account = etPhone.text.toString().trim()
                            var areaCode = tvAreaCode.text.toString().trim()
                            val newConfirmPassword = etFindPassword.text.toString().trim()
                            if (1 == homePage){
                                mPresenter.login(areaCode + "_" + account, newConfirmPassword, PosConst.LOGIN_TYPE_MP, PosConst.LOGIN)
                            }else if (2 == homePage){
                                mPresenter.login(account, newConfirmPassword, PosConst.LOGIN_TYPE_EP, PosConst.LOGIN)
                            }
                            timer!!.cancel()
                        }
                        num--
                    }else{
                        num = 5
                    }
                }
            }
        }
    }

    var timer:Timer?=null
    private fun readyLogin() {
        timer = Timer()
        val end = System.currentTimeMillis()+1000*5
        timer?.schedule(object :TimerTask(){
            override fun run() {
                 handler.sendEmptyMessage(COUNT)
            }
        },0,1000)
    }


    private fun initFindThreeView() {
        llFindOne.visibility = View.GONE
        llFindTwo.visibility = View.GONE
        llFindThree.visibility = View.VISIBLE
        textOne.textColor = resources.getColor(R.color.mainColor)
        textTwo.textColor = resources.getColor(R.color.mainColor)
        textThree.textColor = resources.getColor(R.color.mainColor)
        radiusBackground(3)
        readyLogin()
        backPage = 3
    }

    private fun initFindTwoView() {
        llFindOne.visibility = View.GONE
        llFindTwo.visibility = View.VISIBLE
        llFindThree.visibility = View.GONE
        textOne.textColor = resources.getColor(R.color.mainColor)
        textTwo.textColor = resources.getColor(R.color.mainColor)
        textThree.textColor = resources.getColor(R.color.secondaryTextColor)
        //圆点背景
        radiusBackground(2)
        backPage = 2
    }

    private fun initFindOneView(homePage:Int,account:String) {
        btnGetCode.enableUI()
        llFindOne.visibility = View.VISIBLE
        llFindTwo.visibility = View.GONE
        llFindThree.visibility = View.GONE
        textOne.textColor = resources.getColor(R.color.mainColor)
        textTwo.textColor = resources.getColor(R.color.secondaryTextColor)
        textThree.textColor = resources.getColor(R.color.secondaryTextColor)
        radiusBackground(1)
        if (1==homePage){
            textOneTitle.text = getString(R.string.text_One_Title_1)+account+", "+getString(R.string.text_One_Title_3)
        }else{
            textOneTitle.text = getString(R.string.text_One_Title_2)+account+", "+getString(R.string.text_One_Title_3)
        }
        backPage = 1
    }


    override fun initListener() {
        //手机号找回
        tvAreaCode.setOnClickListener(this)
        etPhone.setOnClickListener(this)
        btnPhoneNext.setOnClickListener(this)
        etPhoneImageVfcode.setOnClickListener(this)
        ivPhoneVfCode.setOnClickListener(this)

        btnFindOne.setOnClickListener(this)
        btnFindTwo.setOnClickListener(this)
        btnFindThree.setOnClickListener(this)
        btnGetCode.setOnClickListener(this)
        getLeftView().setOnClickListener(this)

        cbFindPhoneEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPhonePassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })
        cbFindPswEyes.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etFindPassword.inputType = if (isChecked) InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            else InputType.TYPE_TEXT_VARIATION_PASSWORD or InputType.TYPE_CLASS_TEXT
        })

        radioGroup.check(R.id.rbPhoneFind)
        radioGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: RadioGroup?, p1: Int) {
                if (p1 == R.id.rbPhoneFind) {
                    refreshPhoneUI()
                    homePage = 1
                } else {
                   refreshEmailUI()
                    homePage = 2
                }
            }
        })

        etPhone.addTextChangedListener(textWatcher)
        etPhoneImageVfcode.addTextChangedListener(textWatcher)
        etPhoneOne.addTextChangedListener(textWatcher)
        etPhonePassword.addTextChangedListener(textWatcher)
        etFindPassword.addTextChangedListener(textWatcher)
        tvAreaCode.setOnClickListener(this)
    }

    //刷新手机找回UI
    fun refreshPhoneUI(){
        setCheckedPoninter(R.mipmap.selection,rbPhoneFind)
        setCheckedPoninter(R.mipmap.unchecked,rbEmailFind)
        tvAreaCode.visibility = View.VISIBLE
        etPhone.text = null
        etPhone.hint = getString(R.string.pls_input_phone_number)
        etPhoneImageVfcode.text = null
        val  codeUtils = CodeUtils.getInstance()
        val  bitmap = codeUtils.createBitmap()
        ivPhoneVfCode.setImageBitmap(bitmap)
    }

    //刷新Email找回UI
    fun refreshEmailUI(){
        setCheckedPoninter(R.mipmap.unchecked,rbPhoneFind)
        setCheckedPoninter(R.mipmap.selection,rbEmailFind)
        tvAreaCode.visibility = View.GONE
        etPhone.hint = getString(R.string.pls_input_email_address)
        etPhone.text = null
        etPhoneImageVfcode.text = null
        val  codeUtils = CodeUtils.getInstance()
        val  bitmap = codeUtils.createBitmap()
        ivPhoneVfCode.setImageBitmap(bitmap)
    }

    override fun onDestroy() {
        super.onDestroy()
        etPhone.removeTextChangedListener(textWatcher)
        etPhoneImageVfcode.removeTextChangedListener(textWatcher)
        etPhoneOne.removeTextChangedListener(textWatcher)
        etPhonePassword.removeTextChangedListener(textWatcher)
        etFindPassword.removeTextChangedListener(textWatcher)
    }


    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }

    var homePage:Int = 1
    var backPage:Int = 0
    override fun initView() {
        super.initView()
        setHaveBackView(true)
        setBackActivityView()
        logTools.t("YB").d("initView")
        backPage = 0
        getTitleBar().visibility = View.VISIBLE
        getTitleView().text = getString(R.string.find_psw)
        cbFindPswEyes.isChecked = false
        cbFindPhoneEyes.isChecked = false
        //初始化图形验证码
       val  codeUtils = CodeUtils.getInstance()
       val  bitmap = codeUtils.createBitmap()
        ivPhoneVfCode.setImageBitmap(bitmap)
    }


    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            var flag = true
            when (radioGroup.checkedRadioButtonId) {
                R.id.rbPhoneFind -> {
                        flag = TextUtils.isEmpty(etPhone.text.toString()) || TextUtils.isEmpty(etPhoneImageVfcode.text.toString())
                        btnPhoneNext.isEnabled = !flag
                        btnPhoneNext.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_red else R.drawable.shape_find_psw_bg)
                }
                R.id.rbEmailFind -> {
                    flag = TextUtils.isEmpty(etPhone.text.toString()) || TextUtils.isEmpty(etPhoneImageVfcode.text.toString())
                    btnPhoneNext.isEnabled = !flag
                    btnPhoneNext.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_red else R.drawable.shape_find_psw_bg)
                }
            }
            flag = TextUtils.isEmpty(etPhoneOne.text.toString())
            btnFindOne.isEnabled = !flag

            btnFindOne.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_red else R.drawable.shape_find_psw_bg)

            flag = TextUtils.isEmpty(etPhonePassword.text.toString())||TextUtils.isEmpty(etFindPassword.text.toString())
            btnFindTwo.isEnabled = !flag
            btnFindTwo.setBackgroundResource(if (!flag) R.drawable.selector_bg_btn_red else R.drawable.shape_find_psw_bg)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    private fun setCheckedPoninter(res: Int, radioButton: RadioButton) {
        val drawable = resources?.getDrawable(res)
        drawable?.bounds = Rect(0, 0, drawable?.minimumWidth!!, drawable?.minimumHeight!!)
        radioButton.setCompoundDrawables(drawable, null, null, null)
    }

    private fun radiusBackground(i: Int) {
        when (i) {
            1 -> {
                imgOne.setImageResource(R.mipmap.first_step)
                imgTwo.setImageResource(R.mipmap.the_second_step)
                imgThree.setImageResource(R.mipmap.the_third_step)
                imgViewPoint.setImageResource(R.mipmap.dotted_line)
                imgViewPoint2.setImageResource(R.mipmap.dotted_line)
            }
            2 -> {
                imgOne.setImageResource(R.mipmap.first_step)
                imgTwo.setImageResource(R.mipmap.the_second_step02)
                imgThree.setImageResource(R.mipmap.the_third_step)
                imgViewPoint.setImageResource(R.mipmap.dotted_line02)
                imgViewPoint2.setImageResource(R.mipmap.dotted_line)
            }
            3 -> {
                imgOne.setImageResource(R.mipmap.first_step)
                imgTwo.setImageResource(R.mipmap.the_second_step02)
                imgThree.setImageResource(R.mipmap.the_third_step03)
                imgViewPoint.setImageResource(R.mipmap.dotted_line02)
                imgViewPoint2.setImageResource(R.mipmap.dotted_line02)
            }
        }
    }

    //地区选择
    private fun showAreaPopup() {
        var pop = LoginAreaSelectPopup(this)
        pop.setBackground(ColorDrawable(resources.getColor(android.R.color.transparent)))
        pop.popupGravity = Gravity.BOTTOM
        pop.width =rlPhoneSelect.measuredWidth!!
        pop.showPopupWindow(rlPhoneSelect)
        val loginPhoneHK = pop.contentView.findViewById<TextView>(R.id.loginPhoneHk)
        val loginPhoneCN = pop.contentView.findViewById<TextView>(R.id.loginPhoneCN)
        loginPhoneHK.setOnClickListener {
            loginPhoneHK.textColor = resources.getColor(R.color.mainColor)
            loginPhoneHK.background = resources.getDrawable(R.color.login_area_select)
            loginPhoneCN.textColor  = resources.getColor(R.color.mainTextColor)
            loginPhoneCN.background = resources.getDrawable(R.color.mainMatchColor)
            tvAreaCode.text = "+852"
        }
        loginPhoneCN.setOnClickListener {
            loginPhoneHK.textColor = resources.getColor(R.color.mainTextColor)
            loginPhoneHK.background = resources.getDrawable(R.color.mainMatchColor)
            loginPhoneCN.textColor  = resources.getColor(R.color.mainColor)
            loginPhoneCN.background = resources.getDrawable(R.color.login_area_select)
            tvAreaCode.text = "+86"
        }
    }
}


