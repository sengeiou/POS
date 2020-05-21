package com.epro.pos.ui.fragment

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.PhoneNumChangeEvent
import com.epro.pos.mvp.contract.EditBindPhoneContract
import com.epro.pos.mvp.presenter.EditBindPhonePopPresenter
import com.epro.pos.ui.view.PhoneOrEmailCheckDialog
import com.mike.baselib.fragment.BaseDialogFragment
import com.mike.baselib.utils.DisplayManager
import com.mike.baselib.utils.ValidateUtils
import kotlinx.android.synthetic.main.activity_edit_bind_phone_pop.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.textColor

class EditBindPhonePopup : BaseDialogFragment<EditBindPhoneContract.View, EditBindPhonePopPresenter>(), View.OnClickListener {

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
                var tvArea = tvSelectCty.text.toString().trim()
                if (checkOk){
                    var msg = PhoneNumChangeEvent()
                    if (tvArea.startsWith("+852")){
                        msg.phone = "+852"+phoneNum
                    }else if(tvArea.startsWith("+86")){
                        msg.phone = "+86"+phoneNum
                    }else{
                        msg.phone = phoneNum
                    }
                    EventBus.getDefault().postSticky(msg)
                    dialog.dismiss()
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

        }
    }

    private fun checkAccount(phoneNum:String) :Boolean{
        if(!ValidateUtils.validatePhoneNo(phoneNum)){
            return false
        }
        return true
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

    companion object {
        const val TAG = "EditBindPhonePopup"
        fun newInstance(str: String): EditBindPhonePopup {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = EditBindPhonePopup()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): EditBindPhonePopup {
            return newInstance("")
        }
    }


    override fun getPresenter(): EditBindPhonePopPresenter {
        return  EditBindPhonePopPresenter()
    }

    override fun getContentLayoutId(): Int {
       return R.layout.activity_edit_bind_phone_pop
    }

    override fun initView() {
        tvSelectCty.text = getString(R.string.country_select_pop_hk)
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

    override fun getWidth(): Int {
        return DisplayManager.getScreenWidth()!!*45/100
    }

    override fun getHeight(): Int {
        return DisplayManager.getScreenHeight()!!*61/100
    }

}