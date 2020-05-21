package com.epro.pos.ui.fragment.settings;

import android.os.Bundle
import android.util.Log
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.listener.HistoryDetailSerialNoEvent
import com.epro.pos.mvp.contract.HistoryDetailContract
import com.epro.pos.mvp.model.bean.HistoryDetailBean
import com.epro.pos.mvp.presenter.HistoryDetailPresenter
import com.epro.pos.ui.view.AlignTextView
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarCustomFragment
import kotlinx.android.synthetic.main.activity_history_detial.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.textColor


class HistoryDetailFragment : BaseTitleBarCustomFragment<HistoryDetailContract.View, HistoryDetailPresenter>(), HistoryDetailContract.View, View.OnClickListener {

    override fun onHistoryDetailSuccess(result: HistoryDetailBean.Result) {
        if ("0".equals(result.status.toString())){
            tvStatusNum.text = getString(R.string.status_no_dealWith)
            titleProgress.text = getString(R.string.problem_progress_title)
        }else{
            tvStatusNum.text = getString(R.string.status_dealWith_done)
            titleProgress.text = getString(R.string.problem_have_done)
        }
        tvNumber.text = result.serialNo
        tvTime.text = result.createTime
        logTools.t("YB").d("problem: "+ result.problem)
        etText.text = result.problem
        if (etText.lineCount>1){
            etMessage.text = result.problem
            etMessage.visibility = View.VISIBLE
            etText.visibility = View.GONE
        }else{
            etMessage.visibility = View.GONE
            etText.visibility = View.VISIBLE
        }

      /*  tvNameReal.text = result.owner
        tvPhoneNum.text = result.mobile
        tvEmailNum.text = result.email*/
    }

    override fun getPresenter(): HistoryDetailPresenter {
       return HistoryDetailPresenter()
    }

    override fun onClick(v: View?) {
        when(v){
            getLeftView()->{
                var msg = FragmentChanageEvent()
                msg.postion = 13
                msg.level = 3
                msg.type = 3 //历史记录详情界面，3级界面，第三个
                EventBus.getDefault().postSticky(msg)
            }
        }
    }

    companion object {
        const val TAG = "histroyDetial"
        fun newInstance(str: String): HistoryDetailFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = HistoryDetailFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): HistoryDetailFragment {
            return newInstance("")
        }
    }
    override fun lazyLoad() {

    }

    override fun layoutContentId(): Int {
        return R.layout.activity_history_detial
    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        logTools.t("YB").d(" historyDetailFragment ")
        mPresenter.HistoryDetail(PosConst.HISTORY_RECORD_DETAIL,mSerailNo!!)
        getLeftTitleView().text = getString(R.string.history_record)
        getLeftTitleView().textColor = resources.getColor(R.color.mainTextColor)
        getTitleView().text = getString(R.string.open_detail)
    }

    override fun initData() {

    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
    }

    var mSerailNo:String?=null
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun onUnbindView(event:HistoryDetailSerialNoEvent){
        mSerailNo = event.id
    }
}


