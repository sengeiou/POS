package com.epro.pos.ui.fragment.settings;

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import com.epro.pos.R
import com.epro.pos.listener.AccountUnbindEvent
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.UnBindPhoneContract
import com.epro.pos.mvp.model.bean.GetUserVfBean
import com.epro.pos.mvp.model.bean.GetVfBean
import com.epro.pos.mvp.presenter.UnBindPhonePresenter
import com.epro.pos.ui.view.CountDownButton
import com.epro.pos.ui.view.VerificationPopup
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.DisplayManager
import kotlinx.android.synthetic.main.activity_bind_phone.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import com.mike.baselib.utils.toast


class UnBindPhoneFragment : BaseTitleBarCustomFragment<UnBindPhoneContract.View, UnBindPhonePresenter>(), UnBindPhoneContract.View, View.OnClickListener {
    override fun onGetUserCodeSuccess(result: GetUserVfBean.Result) {
        getCode!!.start()
    }

    override fun onGetVfCodeSuccess(result: GetVfBean.Result) {

    }

    companion object {
        const val TAG = "UnBindPhone"
        fun newInstance(str: String): UnBindPhoneFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = UnBindPhoneFragment()
            fragment.setArguments(args)
            return fragment
        }

        fun newInstance(): UnBindPhoneFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): UnBindPhonePresenter {
        return UnBindPhonePresenter()
    }

    override fun onUnBindPhoneSuccess() {
        pop!!.dismiss()
        var msg = FragmentChanageEvent()
        msg.postion = 13
        msg.level = 2
        msg.type = 1 //2级界面，1类型
        EventBus.getDefault().postSticky(msg)
        toast(getString(R.string.unbind_success))
    }


    override fun lazyLoad() {
    }

    override fun onClick(v: View?) {
        when (v) {
            unBindPhone -> {
                showUnbindPop()
            }
            getLeftView() -> {
                var msg = FragmentChanageEvent()
                msg.postion = 13
                msg.level = 2
                msg.type = 1 //2级界面，1类型
                EventBus.getDefault().postSticky(msg)
            }
        }
    }

    var pop: VerificationPopup? = null
    var account: String? = null
    var getCode: CountDownButton? = null
    private fun showUnbindPop() {
        pop = VerificationPopup(activity!!)
        pop!!.popupGravity = Gravity.CENTER
        //0.45宽，0.6高
        pop!!.setWidth(DisplayManager.getScreenWidth()!! * 45 / 100)
        pop!!.setHeight(DisplayManager.getScreenHeight()!! * 61 / 100)
        pop!!.showPopupWindow()
        pop!!.setOutSideDismiss(false)
        var textPhone = pop!!.contentView.findViewById<TextView>(R.id.tvPhoneNum)
        val cancel = pop!!.contentView.findViewById<TextView>(R.id.tvCancel)
        val imgRight = pop!!.contentView.findViewById<ProgressBar>(R.id.tvRight)
        doneVfCode()
        textPhone.text = getStringAccount() + textPhone.text.toString().trim()
        getCode = pop!!.contentView.findViewById<CountDownButton>(R.id.btnGetCode)
        getCode!!.enableUI()
        getCode!!.setOnClickListener {
            if ("phone".equals(mType)) {
                if (mAccount!!.startsWith("+86")) {
                    account = "+86_" + mAccount!!.substring(3)
                    mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_MP, account!!, PosConst.VF_TYPE_UNBIND_ACCOUNT, PosConst.UNBIND_ACCOUNT)
                } else if (mAccount!!.startsWith("+852")) {
                    account = "+852_" + mAccount!!.substring(4)
                    mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_MP, account!!, PosConst.VF_TYPE_UNBIND_ACCOUNT, PosConst.UNBIND_ACCOUNT)
                }
            } else {
                mPresenter.getUserVfCode(PosConst.LOGIN_TYPE_EP, mAccount!!, PosConst.VF_TYPE_UNBIND_ACCOUNT, PosConst.UNBIND_ACCOUNT)
            }
        }
        cancel.setOnClickListener {
            pop!!.dismiss()
        }
    }

    var index = 1
    private var mArray: Array<EditText>? = null
    private var needListen = true
    private val codes: ArrayList<String> = ArrayList()
    var vfCode: EditText? = null
    var vfCode2: EditText? = null
    var vfCode3: EditText? = null
    var vfCode4: EditText? = null
    var vfCode5: EditText? = null
    var vfCode6: EditText? = null
    private fun doneVfCode() {
        vfCode = pop!!.contentView.findViewById<EditText>(R.id.vfCode)
        vfCode2 = pop!!.contentView.findViewById<EditText>(R.id.vfCode2)
        vfCode3 = pop!!.contentView.findViewById<EditText>(R.id.vfCode3)
        vfCode4 = pop!!.contentView.findViewById<EditText>(R.id.vfCode4)
        vfCode5 = pop!!.contentView.findViewById<EditText>(R.id.vfCode5)
        vfCode6 = pop!!.contentView.findViewById<EditText>(R.id.vfCode6)
        mArray = arrayOf(vfCode!!, vfCode2!!, vfCode3!!, vfCode4!!, vfCode5!!, vfCode6!!)
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
                        if (codes.size < 6 && s.length > 0) {
                            codes.add(s.toString().substring(0, 1))
                        }
                        showCode()
                        needListen = true
                    }
                    val imgRight = pop!!.contentView.findViewById<ProgressBar>(R.id.tvRight)
                    var flag1 = TextUtils.isEmpty(vfCode!!.text.toString())
                    var flag2 = TextUtils.isEmpty(vfCode2!!.text.toString())
                    var flag3 = TextUtils.isEmpty(vfCode3!!.text.toString())
                    var flag4 = TextUtils.isEmpty(vfCode4!!.text.toString())
                    var flag5 = TextUtils.isEmpty(vfCode5!!.text.toString())
                    var flag6 = TextUtils.isEmpty(vfCode6!!.text.toString())
                    if (!flag1 && !flag2 && !flag3 && !flag4 && !flag5 && !flag6) {
                        var code = vfCode!!.text.toString() + vfCode2!!.text.toString() + vfCode3!!.text.toString() + vfCode4!!.text.toString() + vfCode5!!.text.toString() + vfCode6!!.text.toString()
                        imgRight.visibility = View.VISIBLE
                        if ("phone".equals(mType)) {
                            if (mAccount!!.startsWith("+86")) {
                                account = "+86_" + mAccount!!.substring(3)
                                mPresenter.UnBindPhone(PosConst.UNBIND_ACCOUNT, account!!, code, PosConst.LOGIN_TYPE_MP)
                            } else if (mAccount!!.startsWith("+852")) {
                                account = "+852_" + mAccount!!.substring(4)
                                mPresenter.UnBindPhone(PosConst.UNBIND_ACCOUNT, account!!, code, PosConst.LOGIN_TYPE_MP)
                            }
                        } else if ("email".equals(mType)) {
                            mPresenter.UnBindPhone(PosConst.UNBIND_ACCOUNT, mAccount!!, code, PosConst.LOGIN_TYPE_EP)
                        }
                    } else {
                        imgRight.visibility = View.GONE
                    }
                }
            })
            mArray!![i].setOnKeyListener(object : View.OnKeyListener {
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN && codes.size > 0) {
                        codes.removeAt(codes.size - 1)
                        showCode()
                    }
                    return false
                }
            })
            mArray!![i].setOnFocusChangeListener(object : View.OnFocusChangeListener {
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
            if (code1 != vfCode!!.getText().toString()) {
                vfCode!!.setText(code1)
            }
        } else {
            vfCode!!.setText("")
        }
        if (codes.size >= 2) {
            val code2 = codes[1]
            if (code2 != vfCode2!!.getText().toString()) {
                vfCode2!!.setText(code2)
            }
        } else {
            vfCode2!!.setText("")
        }
        if (codes.size >= 3) {
            val code3 = codes[2]
            if (code3 != vfCode3!!.getText().toString()) {
                vfCode3!!.setText(code3)
            }
        } else {
            vfCode3!!.setText("")
        }
        if (codes.size >= 4) {
            val code4 = codes[3]
            if (code4 != vfCode4!!.getText().toString()) {
                vfCode4!!.setText(code4)
            }
        } else {
            vfCode4!!.setText("")
        }
        if (codes.size >= 5) {
            val code5 = codes[4]
            if (code5 != vfCode5!!.getText().toString()) {
                vfCode5!!.setText(code5)
            }
        } else {
            vfCode5!!.setText("")
        }
        if (codes.size >= 6) {
            val code6 = codes[5]
            if (code6 != vfCode6!!.getText().toString()) {
                vfCode6!!.setText(code6)
            }
        } else {
            vfCode6!!.setText("")
        }
        setFocus()
    }

    //设置焦点
    private fun setFocus() {
        if (codes!!.size == 0) {
            vfCode!!.setFocusable(true)
            vfCode!!.setFocusableInTouchMode(true)
            vfCode!!.requestFocus()
            vfCode!!.setSelection(vfCode!!.getText().length)
            // showSoftInput(vfCode)
        }
        if (codes.size == 1) {
            vfCode2!!.setFocusable(true)
            vfCode2!!.setFocusableInTouchMode(true)
            vfCode2!!.requestFocus()
            vfCode2!!.setSelection(vfCode2!!.getText().length)
            //   showSoftInput(vfCode2)
        }
        if (codes.size == 2) {
            vfCode3!!.setFocusable(true)
            vfCode3!!.setFocusableInTouchMode(true)
            vfCode3!!.requestFocus()
            vfCode3!!.setSelection(vfCode3!!.getText().length)
            // showSoftInput(vfCode3)
        }
        if (codes.size == 3) {
            vfCode4!!.setFocusable(true)
            vfCode4!!.setFocusableInTouchMode(true)
            vfCode4!!.requestFocus()
            vfCode4!!.setSelection(vfCode4!!.getText().length)
            // showSoftInput(vfCode4)
        }
        if (codes.size == 4) {
            vfCode5!!.setFocusable(true)
            vfCode5!!.setFocusableInTouchMode(true)
            vfCode5!!.requestFocus()
            vfCode5!!.setSelection(vfCode5!!.getText().length)
            //showSoftInput(vfCode5)
        }
        if (codes.size >= 5) {
            vfCode6!!.setFocusable(true)
            vfCode6!!.setFocusableInTouchMode(true)
            vfCode6!!.requestFocus()
            vfCode6!!.setSelection(vfCode6!!.getText().length)
        }
    }

    private fun getStringAccount(): String {

        var account: String? = null
        if ("email".equals(mType)) {
            val indexOf = mAccount!!.indexOf("@")
            if (indexOf >= 5) {
                account = mAccount!!.substring(0, 5) + "****" + mAccount!!.substring(indexOf)
            } else if (indexOf >= 4) {
                account = mAccount!!.substring(0, 4) + "****" + mAccount!!.substring(indexOf)
            } else if (indexOf >= 3) {
                account = mAccount!!.substring(0, 3) + "****" + mAccount!!.substring(indexOf)
            } else if (indexOf >= 2) {
                account = mAccount!!.substring(0, 2) + "****" + mAccount!!.substring(indexOf)
            }
            return account!!
        } else {
            if (mAccount!!.startsWith("+86", false)) {
                account = "+86 " + mAccount!!.substring(3, 6) + "****" + mAccount!!.substring(mAccount!!.length - 4, mAccount!!.length)
            } else {
                account = "+852 " + mAccount!!.substring(4, 6) + "****" + mAccount!!.substring(mAccount!!.length - 2, mAccount!!.length)
            }
            return account!!
        }
        return account!!
    }


    override fun layoutContentId(): Int {
        return R.layout.activity_bind_phone
    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        if ("phone".equals(mType)) {
            getTitleView().text = getString(R.string.bind_phone)
            unBindTitle.text = getString(R.string.bind_phone_num_already)
        } else if ("email".equals(mType)) {
            getTitleView().text = getString(R.string.bind_email)
            unBindTitle.text = getString(R.string.bind_email_num_already)
        }
        phoneNum.text = mAccount
        activity!!.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)
        getLeftTitleView().text = getString(R.string.personal_center)
    }

    override fun initData() {
    }

    override fun initListener() {
        unBindPhone.setOnClickListener(this)
        getLeftView().setOnClickListener(this)
    }

    var mAccount: String? = null
    var mType: String? = null
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onUnbindView(event: AccountUnbindEvent) {
        mAccount = event.account
        mType = event.type
    }
}


