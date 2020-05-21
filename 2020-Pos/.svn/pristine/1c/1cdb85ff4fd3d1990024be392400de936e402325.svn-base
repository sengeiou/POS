package com.epro.pos.ui.fragment.settings

import android.os.Bundle
import android.view.View
import com.epro.pos.R
import com.epro.pos.listener.FragmentChanageEvent
import com.epro.pos.mvp.contract.HistoryRecordContract
import com.epro.pos.mvp.model.bean.HistoryRecordBean
import com.epro.pos.mvp.presenter.HistoryRecordPresenter
import com.epro.pos.ui.adapter.HistoryRecordAdapter
import com.epro.pos.utils.PosConst
import com.mike.baselib.fragment.BaseTitleBarListFragment
import kotlinx.android.synthetic.main.activity_history_record.*
import org.greenrobot.eventbus.EventBus
import org.jetbrains.anko.backgroundColor
import org.jetbrains.anko.textColor

//历史记录界面 2级界面，第4个
class HistoryRecordFragment : BaseTitleBarListFragment<HistoryRecordBean.HistoryRecordOneBean,HistoryRecordContract.View, HistoryRecordPresenter,HistoryRecordAdapter>(), HistoryRecordContract.View, View.OnClickListener {
    override fun getListAdapter(list: ArrayList<HistoryRecordBean.HistoryRecordOneBean>): HistoryRecordAdapter {
        return HistoryRecordAdapter(activity!!,list)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden){
          //  mPresenter.HistoryRecord(PosConst.HISTORY_RECORD_LIST,page)
        }
    }

    override fun onHistoryRecordSuccess(result: HistoryRecordBean.Result) {

    }

    override fun onClick(v: View?) {

        when(v){
            getLeftView()->{
                var msg = FragmentChanageEvent()
                msg.postion = 13
                msg.level = 2
                msg.type = 3
                EventBus.getDefault().postSticky(msg)
            }
        }
    }

    override fun lazyLoad() {
         page=1
         logTools.t("YB").d("lazyLoad" +page)
         getListData()
    }

    companion object {
        const val TAG = "HistoryRecord"
        fun newInstance(str: String): HistoryRecordFragment {
            val args = Bundle()
            args.putString(TAG, str)
            val fragment = HistoryRecordFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): HistoryRecordFragment {
            return newInstance("")
        }
    }

    override fun getPresenter(): HistoryRecordPresenter {
        return HistoryRecordPresenter()
    }

    override fun initData() {
    }

    override fun getListData() {
        logTools.t("YB").d(" historyRecord getListData()")
        mPresenter.HistoryRecord(PosConst.HISTORY_RECORD_LIST,page)
    }

    override fun dismissLoading(errorMsg: String, errorCode: Int, type: String) {
        logTools.t("YB").d("dismissLoading success")
        if (listDataAdapter!!.mData.isEmpty()){
            multipleStatusView.showEmpty()
        }else{
            multipleStatusView.showContent()
        }
        super.dismissLoading(errorMsg, errorCode, type)
    }

    override fun initView() {
        super.initView()
        setHaveBackView(true)
        logTools.t("YB").d("HistoryRecord initView ")
        mPresenter.HistoryRecord(PosConst.HISTORY_RECORD_LIST,page)
        getLeftTitleView().text = getString(R.string.message_title)
        getLeftTitleView().textColor =resources.getColor(R.color.mainTextColor)
        getTitleView().text = getString(R.string.history_record)
        getRvListView().backgroundColor=resources.getColor(R.color.bottomColor)
    }

    override fun initListener() {
        getLeftView().setOnClickListener(this)
    }

    override fun layoutContentId(): Int {
        return R.layout.activity_history_record
    }
}


