package com.epro.pos.ui.login;

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.CompoundButton
import com.mike.baselib.activity.BaseTitleBarCustomActivity
import com.mike.baselib.utils.AppBusManager
import com.mike.baselib.utils.AppUtils
import com.mike.baselib.utils.StatusBarUtil
import com.epro.pos.R
import com.epro.pos.listener.LoginSuccessEvent
import com.epro.pos.mvp.contract.RegisterContract
import com.epro.pos.mvp.model.bean.User
import com.epro.pos.mvp.presenter.RegisterPresenter
import com.epro.pos.utils.PosConst
import kotlinx.android.synthetic.main.activity_register.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast


class RegisterActivity : BaseTitleBarCustomActivity<RegisterContract.View, RegisterPresenter>(), RegisterContract.View {

    companion object {
        private const val REQUEST_CODE_REGISTER = 1
        const val TAG = "RegisterActivity"
        fun launch(context: Context) {
            launchWithStr(context, "")
        }

        fun launchWithStr(context: Context, str: String) {
            context.startActivity(Intent(context, RegisterActivity::class.java).putExtra(TAG, str))
        }
    }
    override fun getVerifyCodeSuccess(msg: String) {
    }

    override fun registerSuccess(user: User) {
        AppBusManager.setToken(user.token)
        AppBusManager.setUsername(etPhoneNo.text.toString().trim())
        EventBus.getDefault().post(LoginSuccessEvent())
        setResult(Activity.RESULT_OK)
        toast(getString(R.string.logout_success_toast))
        finish()
    }

    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenter()
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
        return R.layout.activity_register
    }


    override fun initData() {

    }

    override fun initStatusBar() {
        StatusBarUtil.immersive(this, resources.getColor(R.color.white), 0f)
    }

    override fun initView() {
        super.initView()
        getTitleBar().visibility = View.GONE
        etPhoneNo.setText(AppBusManager.getUsername())
        cbDelete.isChecked = false
        cbBack.isChecked = false
    }

    override fun initListener() {
        btnRegister.setOnClickListener(View.OnClickListener {
            //注册
            val loginname = etPhoneNo.text.toString().trim()
            val name = etName.text.toString().trim()
            val unit = etUnit.text.toString().trim()
            if (!mPresenter.checkParam(loginname, name, unit)) {
                return@OnClickListener
            }
            mPresenter.register(loginname, name, unit, PosConst.REGISTER!!)
            AppUtils.closeKeyboard(this)
        })

        tvForget.setOnClickListener(View.OnClickListener {
        })

        cbDelete.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etPhoneNo.setText("")
        })
        cbBack.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etName.setText("")
        })
        cbBack2.isChecked = false
        cbBack2.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            etUnit.setText("")
        })
    }

}

