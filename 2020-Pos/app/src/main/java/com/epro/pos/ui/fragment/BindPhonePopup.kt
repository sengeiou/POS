package com.epro.pos.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.epro.pos.R
import com.epro.pos.mvp.contract.BindPhonePopContract
import com.epro.pos.mvp.model.bean.GetUserVfBean
import com.epro.pos.mvp.model.bean.GetVfBean
import com.epro.pos.mvp.presenter.BindPhonePopPresenter
import com.epro.pos.ui.view.PhoneOrEmailCheckDialog
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ValidateUtils
import kotlinx.android.synthetic.main.activity_bind_phone_pop.*
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor


class BindPhonePopup : BaseDialogFragment<BindPhonePopContract.View, BindPhonePopPresenter>(), BindPhonePopContract.View, View.OnClickListener {

    override fun onGetUserCodeSuccess(result: GetUserVfBean.Result,type: String) {
        btnGetCode.start()
        if ("next".equals(type)){
            viewFlip.showPrevious()
        }
    }

    var selectType=0
    override fun onClick(v: View?) {

        when(v){
            //选择地区
            imgPopPhone->{
                viewFlip.showNext()
            }
            //返回
            tvPopBack->{
                viewFlip.showPrevious()
            }
            //下一步
            tvPopRight->{
                val phoneNum = etPopPhone.text.toString().trim()
                val checkOk = checkAccount(phoneNum)
                var mString = getString(R.string.end_add_phone)
                var tvArea = tvSelectCty.text.toString().trim()
                if (checkOk){
                    val phone=  phoneNum.substring(0,2)+"****"+phoneNum.substring(phoneNum.length-2,phoneNum.length)
                    if (tvArea.startsWith("+852")){
                        mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_MP,"+852_$phoneNum",PosConst.VF_TYPE_BIND_ACCOUNT,"next")
                        tvPhoneNum.text = "+852 "+phone+mString
                    }else if(tvArea.startsWith("+86")){
                        mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_MP,"+86_$phoneNum",PosConst.VF_TYPE_BIND_ACCOUNT,"next")
                        tvPhoneNum.text = "+86 "+phone+mString
                    }else{
                        tvPhoneNum.text = phone+mString
                    }
                }else{
                    //弹框提示格式不对
                    showDialog()
                }
            }
            //选择香港
            tvSelectHK->{
                if (imgSelectPhone2.visibility == View.VISIBLE){
                    imgSelectPhone2.visibility = View.GONE
                    imgSelectPhone.visibility = View.VISIBLE
                    tvSelectCty.text = getString(R.string.country_select_pop_hk)
                }else{
                    imgSelectPhone.visibility = View.VISIBLE
                    tvSelectCty.text = getString(R.string.country_select_pop_hk)
                }
                selectType = 1
                viewFlip.showPrevious()
            }
            //选择大陆
            tvSelectCN->{
                if (imgSelectPhone.visibility == View.VISIBLE){
                    imgSelectPhone.visibility = View.GONE
                    imgSelectPhone2.visibility = View.VISIBLE
                    tvSelectCty.text = getString(R.string.country_select_pop_cn)
                }else{
                    imgSelectPhone2.visibility = View.VISIBLE
                    tvSelectCty.text = getString(R.string.country_select_pop_cn)
                }
                selectType = 2
                viewFlip.showPrevious()
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
                val phoneNum = etPopPhone.text.toString().trim()
                var tvArea = tvSelectCty.text.toString().trim()
                if (tvArea.startsWith("+86")){
                    mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_MP,"+86_$phoneNum",PosConst.VF_TYPE_BIND_ACCOUNT,PosConst.BIND_PHONE_EMAIL)
                }else{
                    mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_MP,"+852_$phoneNum",PosConst.VF_TYPE_BIND_ACCOUNT,PosConst.BIND_PHONE_EMAIL)
                }
            }
        }
    }

    private fun showDialog() {
        PhoneOrEmailCheckDialog.Builder(activity!!)
                .setTitle(getString(R.string.phone_format_error))
                .setContent(getString(R.string.pls_input_phone_num))
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
        if(!ValidateUtils.validatePhoneNo(phoneNum)){
            return false
        }
        return true
    }

    override fun getContentLayoutId(): Int {
        return  R.layout.activity_bind_phone_pop
    }

    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!!*45/100
    }

    override fun getHeight(): Int {
        return DisplayManager.getScreenHeight()!!*61/100
    }
    override fun initView() {
        tvSelectCty.text = getString(R.string.country_select_pop_hk)
    }

    companion object {
        const val TAG = "BindPhonePop"
        fun newInstance(str: String): BindPhonePopup {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = BindPhonePopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): BindPhonePopup {
            return newInstance("")
        }
    }

    override fun getPresenter(): BindPhonePopPresenter {
        return BindPhonePopPresenter()
    }
    override fun onBindPhonePopSuccess() {
         toast(getString(R.string.bind_success))
        dialog.dismiss()
    }
    override fun initData() {
    }
    override fun initListener() {
        imgPopPhone.setOnClickListener(this)
        tvPopBack.setOnClickListener(this)
        tvSelectHK.setOnClickListener(this)
        tvSelectCN.setOnClickListener(this)
        etPopPhone.addTextChangedListener(textWatcher)
        tvPopRight.setOnClickListener(this)
        tvPopCancel.setOnClickListener(this)
        tvCancel.setOnClickListener(this)
        btnGetCode.setOnClickListener(this)
        btnGetCode.enableUI()
        doneEditTextCode()
    }

    var index = 1
    private var mArray: Array<EditText>? = null
    private var needListen = true
    private val codes: ArrayList<String> = ArrayList()
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
                        tvRight.visibility = View.VISIBLE
                        var code = vfCode.text.toString()+vfCode2.text.toString()+vfCode3.text.toString()+vfCode4.text.toString()+vfCode5.text.toString()+vfCode6.text.toString()
                        var mAccount =etPopPhone.text.toString().trim()
                        var tvArea = tvSelectCty.text.toString().trim()
                        if (tvArea.startsWith("+86")){
                            mPresenter.BindPhonePop(PosConst.BIND_PHONE_EMAIL,"+86_$mAccount",code,PosConst.LOGIN_TYPE_MP)
                        }else{
                            mPresenter.BindPhonePop(PosConst.BIND_PHONE_EMAIL,"+852_$mAccount",code,PosConst.LOGIN_TYPE_MP)
                        }
                    }else{
                        tvRight.visibility = View.GONE
                    }
                }
            })
            mArray!![i].setOnKeyListener(object :View.OnKeyListener{
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && codes.size > 0 ){
                        codes.removeAt(codes.size - 1)
                        showCode()
                    }
                    return false
                }
            })
            mArray!![i].setOnFocusChangeListener (object :View.OnFocusChangeListener{
                override fun onFocusChange(v: View?, hasFocus: Boolean) {
                    if (hasFocus) {
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

}


