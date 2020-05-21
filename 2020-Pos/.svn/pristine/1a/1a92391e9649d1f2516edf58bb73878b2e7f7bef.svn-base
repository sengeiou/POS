package com.epro.pos.ui.fragment.settings

import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.ModifyPasswordContract
import com.epro.pos.mvp.presenter.ModifyPasswordPresenter
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.ValidateUtils
import kotlinx.android.synthetic.main.modify_password_activity.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor

class ModifyPasswordFragment: BaseTitleBarCustomFragment<ModifyPasswordContract.View,ModifyPasswordPresenter>(),ModifyPasswordContract.View, View.OnClickListener {

    override fun getPresenter(): ModifyPasswordPresenter {
       return ModifyPasswordPresenter()
    }

    override fun modifyPasswordSuccess(result: String) {
            toast(result)
    }

    override fun onClick(v: View?) {
        when(v){
            getRightView()->{
                var prePsw = prePsw.text.toString().trim()
                var newPsw = newPsw.text.toString().trim()
                var newConfirm = newConfirm.text.toString().trim()
                if (!ValidateUtils.validatePassword(prePsw)||!ValidateUtils.validatePassword(newPsw)||!ValidateUtils.validatePassword(newConfirm)){
                    toast(R.string.password_format_error)
                    return
                }
                if (!newPsw.equals(newConfirm)){
                    toast(R.string.two_input_psw_mismatch)
                    return
                }
                mPresenter.modifyPassword(prePsw,newPsw,newConfirm)
            }

            getLeftView()->{
                prePsw.text.clear()
                newPsw.text.clear()
                newConfirm.text.clear()
                var msg  = FragmentChanageEvent()
                msg.postion = 13
                msg.level = 2
                msg.type = 2
                EventBus.getDefault().postSticky(msg)
            }
        }
    }

    override fun lazyLoad() {

    }

    override fun layoutContentId(): Int {
       return R.layout.modify_password_activity
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        getRightView().visibility = View.VISIBLE
        getRightView().text = getString(R.string.edit_person_save)
        getRightView().textColor = resources.getColor(R.color.main_50)
        getTitleView().text = getString(R.string.modify_psw)
        getLeftTitleView().text = getString(R.string.personal_center)
    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
        getRightView().setOnClickListener(this)
        prePsw.addTextChangedListener(mTextWatcher)
        newPsw.addTextChangedListener(mTextWatcher)
        newConfirm.addTextChangedListener(mTextWatcher)
    }
    val mTextWatcher = object : TextWatcher {
        var flag = false
        override fun afterTextChanged(s: Editable?) {
            flag = TextUtils.isEmpty(prePsw.text.toString()) || TextUtils.isEmpty(newPsw.text.toString())|| TextUtils.isEmpty(newConfirm.text.toString())
            getRightView().isEnabled = !flag
            getRightView().textColor = if (!flag) resources.getColor(R.color.mainColor) else resources.getColor(R.color.main_50)
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

}