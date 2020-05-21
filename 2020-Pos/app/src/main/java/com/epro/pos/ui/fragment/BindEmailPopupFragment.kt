package com.epro.pos.ui.fragment;

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import com.epro.pos.R
import com.epro.pos.mvp.contract.BindEmailPopupContract
import com.epro.pos.mvp.model.bean.GetUserVfBean
import com.epro.pos.mvp.presenter.BindEmailPopupPresenter
import com.epro.pos.ui.view.PhoneOrEmailCheckDialog
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ValidateUtils
import kotlinx.android.synthetic.main.activity_bind_email_popup.*
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor


class BindEmailPopupFragment : BaseDialogFragment<BindEmailPopupContract.View, BindEmailPopupPresenter>(), BindEmailPopupContract.View, View.OnClickListener {
    override fun onGetUserCodeSuccess(result: GetUserVfBean.Result,type: String) {
        btnGetCode.start()
        if ("next".equals(type)){
            viewFlip.showNext()
        }
    }

    override fun onClick(v: View?) {
        when(v){

            //下一步
            tvPopRight->{
                var phoneNum = etPopPhone.text.toString().trim()
                val checkOk = checkAccount(phoneNum)
                var mString = getString(R.string.end_add_phone)
                val indexOf = phoneNum!!.indexOf("@")
                if (indexOf>=1){
                    tvPhoneNum.text = phoneNum!!.substring(0,1)+"****"+phoneNum!!.substring(indexOf)+mString
                }
                if(checkOk){
                    mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_EP,phoneNum, PosConst.VF_TYPE_BIND_ACCOUNT , "next")
                }else{
                    //弹框提示格式不对
                    showDialog()
                }
            }

            //取消
            tvPopCancel->{
                dialog.dismiss()
            }
            //取消
            tvCancel->{
                dialog.dismiss()
            }
            //发送验证码
            btnGetCode->{
                btnGetCode.enableUI()
                var phoneNum = etPopPhone.text.toString().trim()
                mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_EP,phoneNum, PosConst.VF_TYPE_BIND_ACCOUNT , PosConst.GET_VFCODE)
            }
        }
    }

    override fun initView() {

    }

    override fun getContentLayoutId(): Int {
        return R.layout.activity_bind_email_popup
    }

    companion object {
        const val TAG = "BindEmailPopup"
        fun newInstance(str: String): BindEmailPopupFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = BindEmailPopupFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): BindEmailPopupFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): BindEmailPopupPresenter {
        return BindEmailPopupPresenter()
    }

    override fun onBindEmailPopupSuccess() {
        dialog.dismiss()
        toast(getString(R.string.bind_success))
    }

    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!!*45/100
    }

    override fun getHeight(): Int {
        return DisplayManager.getScreenHeight()!!*61/100
    }

    override fun initData() {

    }

    override fun initListener() {
        tvPopCancel.setOnClickListener(this)
        tvPopRight.setOnClickListener(this)
        etPopPhone.addTextChangedListener(textWatcher)
        tvCancel.setOnClickListener(this)
        btnGetCode.setOnClickListener(this)
        btnGetCode.enableUI()
        doneEditTextCode()
    }

    var index = 1
    private var mArray: Array<EditText>? = null
    private var needListen = true
    private fun doneEditTextCode() {
        mArray = arrayOf(vfCode, vfCode2, vfCode3, vfCode4,vfCode5,vfCode6)
        for (i in 0 until mArray!!.size) {
            mArray!![i].addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                        s: CharSequence,
                        start: Int,
                        count: Int,
                        after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

                }

                override fun afterTextChanged(s: Editable) {
                    if (needListen) {
                        needListen = false
                        if (codes.size < 6 && s.length > 0){
                            codes.add(s.toString().substring(0, 1))
                        }
                        showCode()
                        needListen = true
                    }
                    var flag1 = TextUtils.isEmpty(vfCode.text.toString())
                    var flag2 = TextUtils.isEmpty(vfCode2.text.toString())
                    var flag3 = TextUtils.isEmpty(vfCode3.text.toString())
                    var flag4 = TextUtils.isEmpty(vfCode4.text.toString())
                    var flag5 = TextUtils.isEmpty(vfCode5.text.toString())
                    var flag6 = TextUtils.isEmpty(vfCode6.text.toString())
                    if (!flag1 && !flag2 && !flag3 && !flag4&&!flag5&&!flag6){
                        var code = vfCode.text.toString()+vfCode2.text.toString()+vfCode3.text.toString()+vfCode4.text.toString()+vfCode5.text.toString()+vfCode6.text.toString()
                        var mAccount =etPopPhone.text.toString().trim()
                        tvRight.visibility = View.VISIBLE
                        mPresenter.BindEmailPopup(PosConst.BIND_PHONE_EMAIL,mAccount!!,code,PosConst.LOGIN_TYPE_EP)
                    }else{
                        tvRight.visibility = View.GONE
                    }
                }
            })
            mArray!![i].setOnKeyListener(object :View.OnKeyListener{
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    logTools.t("YB").d("KEYCODE_DEL 111")
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && codes.size > 0 ){
                        logTools.t("YB").d("KEYCODE_DEL 222")
                        codes.removeAt(codes.size - 1)
                        showCode()
                    }
                    return false
                }
            })
            mArray!![i].setOnFocusChangeListener (object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    logTools.t("YB").d("FocusChange 000")
                    if (hasFocus) {
                        logTools.t("YB").d("FocusChange 111")
                        setFocus()
                    }
                }
            })
        }
    }

    /**
     * 显示输入的验证码
     */
    private fun showCode() {
        if (codes!!.size >= 1) {
            val code1 = codes[0]
            if (code1 != vfCode.getText().toString()) {
                vfCode.setText(code1)
            }
        } else {
            vfCode.setText("")
        }
        if (codes.size >= 2) {
            val code2 = codes[1]
            if (code2 != vfCode2.getText().toString()) {
                vfCode2.setText(code2)
            }
        } else {
            vfCode2.setText("")
        }
        if (codes.size >= 3) {
            val code3 = codes[2]
            if (code3 != vfCode3.getText().toString()) {
                vfCode3.setText(code3)
            }
        } else {
            vfCode3.setText("")
        }
        if (codes.size >= 4) {
            val code4 = codes[3]
            if (code4 != vfCode4.getText().toString()) {
                vfCode4.setText(code4)
            }
        } else {
            vfCode4.setText("")
        }
        if (codes.size >= 5) {
            val code5 = codes[4]
            if (code5 != vfCode5.getText().toString()) {
                vfCode5.setText(code5)
            }
        } else {
            vfCode5.setText("")
        }
        if (codes.size >= 6) {
            val code6 = codes[5]
            if (code6 != vfCode6.getText().toString()) {
                vfCode6.setText(code6)
            }
        } else {
            vfCode6.setText("")
        }
        setFocus()
    }

    //设置焦点
    private fun setFocus() {
        if (codes!!.size == 0) {
            vfCode.setFocusable(true)
            vfCode.setFocusableInTouchMode(true)
            vfCode.requestFocus()
            vfCode.setSelection(vfCode.getText().length)
           // showSoftInput(vfCode)
        }
        if (codes.size == 1) {
            vfCode2.setFocusable(true)
            vfCode2.setFocusableInTouchMode(true)
            vfCode2.requestFocus()
            vfCode2.setSelection(vfCode2.getText().length)
         //   showSoftInput(vfCode2)
        }
        if (codes.size == 2) {
            vfCode3.setFocusable(true)
            vfCode3.setFocusableInTouchMode(true)
            vfCode3.requestFocus()
            vfCode3.setSelection(vfCode3.getText().length)
           // showSoftInput(vfCode3)
        }
        if (codes.size == 3) {
            vfCode4.setFocusable(true)
            vfCode4.setFocusableInTouchMode(true)
            vfCode4.requestFocus()
            vfCode4.setSelection(vfCode4.getText().length)
           // showSoftInput(vfCode4)
        }
        if (codes.size == 4) {
            vfCode5.setFocusable(true)
            vfCode5.setFocusableInTouchMode(true)
            vfCode5.requestFocus()
            vfCode5.setSelection(vfCode5.getText().length)
            //showSoftInput(vfCode5)
        }
        if (codes.size >= 5) {
            vfCode6.setFocusable(true)
            vfCode6.setFocusableInTouchMode(true)
            vfCode6.requestFocus()
            vfCode6.setSelection(vfCode6.getText().length)
        }
    }

    private val codes: ArrayList<String> = ArrayList()

    private val textWatcher = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
        override fun afterTextChanged(s: Editable?) {
            var flag: Boolean = TextUtils.isEmpty(etPopPhone.text.toString())
            tvPopRight.isEnabled = !flag
            tvPopRight.textColor = if (!flag) resources.getColor(R.color.mainColor) else resources.getColor(R.color.main_50)
        }
    }

    private fun showDialog() {
        PhoneOrEmailCheckDialog.Builder(activity!!)
                .setTitle(getString(R.string.email_format_error))
                .setContent(getString(R.string.pls_input_email_num))
                .setConfirmText(getString(R.string.pls_confirm_pop))
                .setOnConfirmListener(object : PhoneOrEmailCheckDialog.OnConfirmListener {
                    override fun onClick(dialog: Dialog) {
                        dialog.dismiss()
                    }
                })
                .create()
                .show()

    }
    private fun checkAccount(phoneNum:String) :Boolean{
        if(!ValidateUtils.validateEmail(phoneNum)){
            return false
        }
        return true
    }

    override fun showError(errorMsg: String, errorCode: Int, type: String) {
       super.showError(errorMsg, errorCode, type)
        if (type.equals(PosConst.VALID_USER)){
            if ("120005".equals(errorCode)){

            }
        }
    }
}


