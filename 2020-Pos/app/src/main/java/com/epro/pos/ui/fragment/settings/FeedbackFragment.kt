package com.epro.pos.ui.fragment.settings;

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.InputType
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.FeedbackContract
import com.epro.pos.mvp.model.bean.FeedbackBean
import com.epro.pos.mvp.model.bean.InquireShopBean
import com.epro.pos.mvp.presenter.FeedbackPresenter
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import com.mike.baselib.utils.AppBusManager
import kotlinx.android.synthetic.main.activity_feedback.*
import org.greenrobot.eventbus.EventBus
import com.mike.baselib.utils.toast
import org.jetbrains.anko.textColor


class FeedbackFragment : BaseTitleBarCustomFragment<FeedbackContract.View, FeedbackPresenter>(), FeedbackContract.View, View.OnClickListener {
    override fun lazyLoad() {
    }

    override fun onFeedbackSuccess(result: FeedbackBean.Result) {
        etDetail.text.clear()
        toast(getString(R.string.commit_success))
    }

    var shopBean:InquireShopBean.Result?=null
    var phone:String?=null
    override fun selectUserInfoSuccess(result: InquireShopBean.Result) {
        /*tvNameReal.setText(result.owner)
        tvPhoneNum.setText(result.mobile)
        tvEmailNum.setText( result.email)*/
        shopBean = result
        AppBusManager.setShopName(result.shopName)
        AppBusManager.setShopId(result.shopId)
    }


    override fun onClick(v: View?) {
        when(v){
            getRightView()->{
                var msg = FragmentChanageEvent()
                msg.postion = 10
                EventBus.getDefault().postSticky(msg)
            }

            //返回键
            getLeftView()->{
                etDetail.text.clear()
                var msg = FragmentChanageEvent()
                msg.postion = 13
                EventBus.getDefault().postSticky(msg)
            }
            btnCommit->{
                if(shopBean==null){
                    toast("刷新获取用户信息")
                    return
                }
                phone= shopBean!!.mobile
                if (phone!!.startsWith("+86")){
                    phone ="+86_" +phone!!.substring(3)
                }else if (phone!!.startsWith("+852")){
                    phone ="+852_" +phone!!.substring(4)
                }
                var problem= etDetail.text.toString().trim()
                mPresenter.Feedback(PosConst.COMMIT_FEEDBACK,shopBean!!.owner,phone!!,shopBean!!.email,problem,shopBean!!.shopId,shopBean!!.shopName)
            }
        }
    }

    companion object {
        const val TAG = "Feedback"
        const val EXTRA = "extra"
        fun newInstance(type: String, extra: String = ""): FeedbackFragment {
            val args = Bundle()
            args.putString(TAG, type)
            args.putString(EXTRA, extra)
            val fragment = FeedbackFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): FeedbackFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): FeedbackPresenter {
        return FeedbackPresenter()
    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler() {
        var num = 5
        override fun handleMessage(msg: Message) {
            when (msg.what) {
               COUNT->{
                   var  size = msg.arg1
                   if (size<=500) {
                       tvCount.setText(size.toString()+"/500")
                   }
               }
            }
        }
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_feedback
    }

    override fun initData() {

    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        mPresenter.selectUserInfo(PosConst.SELECT_USER_INFO)
        getLeftTitleView().text = getString(R.string.system_settings)
        getRightView().visibility = View.VISIBLE
        getRightView().text = getString(R.string.history_record)
        etDetail.text.clear()
        getRightView().textColor = resources.getColor(R.color.mainTextColor)
        getTitleView().text = getString(R.string.message_title)
        btnCommit.isEnabled = false
    }

    private fun clearDeleteIcon() {
        tvNameReal.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        tvPhoneNum.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
        tvEmailNum.setCompoundDrawablesWithIntrinsicBounds(null, null, null, null)
    }

    override fun initListener() {
        getRightView().setOnClickListener(this)
        getLeftView().setOnClickListener(this)
        etDetail.addTextChangedListener(mTextWatcher)
        tvNameReal.addTextChangedListener(mTextWatcher)
        tvPhoneNum.addTextChangedListener(mTextWatcher)
        tvEmailNum.addTextChangedListener(mTextWatcher)
        btnCommit.setOnClickListener(this)
    }

    var  COUNT =1
    val mTextWatcher = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
           var flag:Boolean
            flag = TextUtils.isEmpty(etDetail.text.toString().trim())
            btnCommit.isEnabled = !flag
            btnCommit.setBackgroundResource(if(!flag)R.drawable.shape_btn_update_info else R.drawable.shape_bg_push_btn)
            var size = etDetail.text.toString().length
            var message = Message()
            message.what = COUNT
            message.arg1 = size
            handler.sendMessage(message)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

}


